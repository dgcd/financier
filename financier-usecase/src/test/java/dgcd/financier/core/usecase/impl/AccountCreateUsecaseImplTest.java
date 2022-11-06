package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.exception.IllegalAccountTitleException;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.usecase.AccountCreateUsecase;
import dgcd.financier.core.usecase.exception.AccountWithTitleAlreadyExistsException;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dgcd.financier.core.domain.Currency.USD;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountCreateUsecaseImplTest {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountCreateUsecaseImpl accountCreateUsecase;


    @Test
    void test_execute_OK() {
        // given
        var request = new AccountCreateUsecase.Request("account", USD);

        // and
        when(accountsRepository.existByTitle("account"))
                .thenReturn(false);
        when(accountsRepository.save(any()))
                .thenReturn(AccountFactory.makeExisting(42L, "account", USD, ZERO, FALSE));

        // when
        var response = accountCreateUsecase.execute(request);

        // then
        verify(accountsRepository, times(1)).existByTitle(any());
        var titleCaptor = ArgumentCaptor.forClass(String.class);
        verify(accountsRepository).existByTitle(titleCaptor.capture());
        assertThat(titleCaptor.getValue()).isEqualTo("account");

        verify(accountsRepository, times(1)).save(any());
        var accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountsRepository).save(accountCaptor.capture());
        var capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getIdentity()).isNull();
        assertThat(capturedAccount.getTitle()).isEqualTo("account");
        assertThat(capturedAccount.getCurrency()).isEqualTo(USD);
        assertThat(capturedAccount.getBalance()).isZero();
        assertThat(capturedAccount.getIsClosed()).isFalse();

        var account = response.getAccount();
        assertThat(account.getIdentity()).isEqualTo(42L);
        assertThat(account.getTitle()).isEqualTo("account");
        assertThat(account.getCurrency()).isEqualTo(USD);
        assertThat(account.getBalance()).isZero();
        assertThat(account.getIsClosed()).isFalse();
    }


    @Test
    void test_execute_duplicatedTitle_ERROR() {
        // given
        var request = new AccountCreateUsecase.Request("account", USD);

        // and
        when(accountsRepository.existByTitle("account"))
                .thenReturn(true);

        // when
        assertThatThrownBy(() -> accountCreateUsecase.execute(request))
                .isInstanceOf(AccountWithTitleAlreadyExistsException.class)
                .hasMessage("Account with title 'account' already exists");

        // then
        verify(accountsRepository, times(1)).existByTitle(any());
        var titleCaptor = ArgumentCaptor.forClass(String.class);
        verify(accountsRepository).existByTitle(titleCaptor.capture());
        assertThat(titleCaptor.getValue()).isEqualTo("account");

        verify(accountsRepository, never()).save(any());
    }


    @Test
    void test_execute_nullRequest_ERROR() {
        // when
        assertThatThrownBy(() -> accountCreateUsecase.execute(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Request can not be null");
    }


    @Test
    void test_execute_nullTitle_ERROR() {
        // given
        var request = new AccountCreateUsecase.Request(null, USD);

        // when
        assertThatThrownBy(() -> accountCreateUsecase.execute(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Title can not be null");
    }


    @Test
    void test_execute_tooShortTitle_ERROR() {
        // given
        var request = new AccountCreateUsecase.Request("1", USD);

        // when
        assertThatThrownBy(() -> accountCreateUsecase.execute(request))
                .isInstanceOf(IllegalAccountTitleException.class)
                .hasMessage("Account title length must be from 5 to 40 but was: 1");
    }


    @Test
    void test_execute_nullCurrency_ERROR() {
        // given
        var request = new AccountCreateUsecase.Request("title", null);

        // when
        assertThatThrownBy(() -> accountCreateUsecase.execute(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Currency can not be null");
    }

}
