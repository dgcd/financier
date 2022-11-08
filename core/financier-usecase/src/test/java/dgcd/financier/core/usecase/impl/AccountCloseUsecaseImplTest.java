package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.usecase.AccountCloseUsecase;
import dgcd.financier.core.usecase.exception.AccountAlreadyClosedException;
import dgcd.financier.core.usecase.exception.AccountHasNonZeroBalanceException;
import dgcd.financier.core.usecase.exception.AccountNotExistsException;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static dgcd.financier.core.domain.Currency.USD;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountCloseUsecaseImplTest {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountCloseUsecaseImpl accountCloseUsecase;


    @Test
    void test_execute_OK() {
        // given
        var request = new AccountCloseUsecase.Request(42L);

        // and
        when(accountsRepository.findByIdentity((any())))
                .thenReturn(Optional.of(AccountFactory.makeExisting(42L, "account", USD, ZERO, FALSE)));
        when(accountsRepository.save(any()))
                .thenReturn(AccountFactory.makeExisting(42L, "account", USD, ZERO, TRUE));

        // when
        var response = accountCloseUsecase.execute(request);

        // then
        verify(accountsRepository, times(1)).findByIdentity(any());
        var identityCaptor = ArgumentCaptor.forClass(Long.class);
        verify(accountsRepository).findByIdentity(identityCaptor.capture());
        assertThat(identityCaptor.getValue()).isEqualTo(42L);

        verify(accountsRepository, times(1)).save(any());
        var accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountsRepository).save(accountCaptor.capture());
        var capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getIdentity()).isEqualTo(42L);
        assertThat(capturedAccount.getTitle()).isEqualTo("account");
        assertThat(capturedAccount.getCurrency()).isEqualTo(USD);
        assertThat(capturedAccount.getBalance()).isZero();
        assertThat(capturedAccount.getIsClosed()).isTrue();

        var account = response.getAccount();
        assertThat(account.getIdentity()).isEqualTo(42L);
        assertThat(account.getTitle()).isEqualTo("account");
        assertThat(account.getCurrency()).isEqualTo(USD);
        assertThat(account.getBalance()).isZero();
        assertThat(account.getIsClosed()).isTrue();
    }


    @Test
    void test_execute_accountByIdentityWasNotFound_ERROR() {
        // given
        var request = new AccountCloseUsecase.Request(42L);

        // and
        when(accountsRepository.findByIdentity((any())))
                .thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> accountCloseUsecase.execute(request))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage("Account with identity '42' doesn't exist");

        // then
        verify(accountsRepository, times(1)).findByIdentity(any());
        var identityCaptor = ArgumentCaptor.forClass(Long.class);
        verify(accountsRepository).findByIdentity(identityCaptor.capture());
        assertThat(identityCaptor.getValue()).isEqualTo(42L);

        verify(accountsRepository, never()).save(any());
    }


    @Test
    void test_execute_accountWasAlreadyClosed_ERROR() {
        // given
        var request = new AccountCloseUsecase.Request(42L);

        // and
        when(accountsRepository.findByIdentity((any())))
                .thenReturn(Optional.of(AccountFactory.makeExisting(42L, "account", USD, ZERO, TRUE)));

        // when
        assertThatThrownBy(() -> accountCloseUsecase.execute(request))
                .isInstanceOf(AccountAlreadyClosedException.class)
                .hasMessage("Account with identity '42' is already closed");

        // then
        verify(accountsRepository, times(1)).findByIdentity(any());
        var identityCaptor = ArgumentCaptor.forClass(Long.class);
        verify(accountsRepository).findByIdentity(identityCaptor.capture());
        assertThat(identityCaptor.getValue()).isEqualTo(42L);

        verify(accountsRepository, never()).save(any());
    }


    @Test
    void test_execute_accountHasNonZeroBalanse_ERROR() {
        // given
        var request = new AccountCloseUsecase.Request(42L);

        // and
        when(accountsRepository.findByIdentity((any())))
                .thenReturn(Optional.of(AccountFactory.makeExisting(42L, "account", USD, ONE, FALSE)));

        // when
        assertThatThrownBy(() -> accountCloseUsecase.execute(request))
                .isInstanceOf(AccountHasNonZeroBalanceException.class)
                .hasMessage("Account with identity '42' has non zero balance and can not be closed");

        // then
        verify(accountsRepository, times(1)).findByIdentity(any());
        var identityCaptor = ArgumentCaptor.forClass(Long.class);
        verify(accountsRepository).findByIdentity(identityCaptor.capture());
        assertThat(identityCaptor.getValue()).isEqualTo(42L);

        verify(accountsRepository, never()).save(any());
    }

}
