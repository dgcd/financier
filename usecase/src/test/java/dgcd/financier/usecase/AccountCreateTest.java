package dgcd.financier.usecase;

import dgcd.financier.domain.entity.Account;
import dgcd.financier.usecase.dto.AccountCreateRequestDto;
import dgcd.financier.usecase.exception.ValidationException;
import dgcd.financier.usecase.exception.account.AccountWithTitleAlreadyExistsException;
import dgcd.financier.usecase.port.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static dgcd.financier.domain.dictionary.Currency.EUR;
import static dgcd.financier.domain.dictionary.Currency.RUB;
import static dgcd.financier.domain.dictionary.Currency.USD;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountCreateTest {

    @Mock
    private AccountsRepository accountsRepository;


    @Test
    void createAccount_whenNullTitle_thenException() {
        var accountCreate = new AccountCreate(accountsRepository);
        var dto = new AccountCreateRequestDto(null, RUB);

        assertThrows(ValidationException.class, () -> accountCreate.createAccount(dto));
    }


    @Test
    void createAccount_whenTooLongTitle_thenException() {
        var accountCreate = new AccountCreate(accountsRepository);
        var dto = new AccountCreateRequestDto("1234567890123456789012345678901", RUB);

        assertThrows(ValidationException.class, () -> accountCreate.createAccount(dto));
    }


    @Test
    void createAccount_whenEmptyTitle_thenException() {
        var accountCreate = new AccountCreate(accountsRepository);
        var dto = new AccountCreateRequestDto("      ", USD);

        assertThrows(ValidationException.class, () -> accountCreate.createAccount(dto));
    }


    @Test
    void createAccount_whenNullCurrency_thenException() {
        var accountCreate = new AccountCreate(accountsRepository);
        var dto = new AccountCreateRequestDto("  123456789012345678901234567890  ", null);

        assertThrows(ValidationException.class, () -> accountCreate.createAccount(dto));
    }


    @Test
    void createAccount_whenTitleAlreadyExists_thenException() {
        final var DUPLICATED_TITLE = "someTitle";

        var accountCreate = new AccountCreate(accountsRepository);
        var dto = new AccountCreateRequestDto(DUPLICATED_TITLE, EUR);

        when(accountsRepository.findByTitle(DUPLICATED_TITLE))
                .thenReturn(Optional.of(new Account(123L, DUPLICATED_TITLE, EUR, ZERO, false)));

        assertThrows(AccountWithTitleAlreadyExistsException.class, () -> accountCreate.createAccount(dto));
    }

    @Test
    void createAccount_whenTitleAlreadyExists_thenException_2() {
        final var DUPLICATED_TITLE = "someTitle";

        var accountCreate = new AccountCreate(accountsRepository);
        var dto = new AccountCreateRequestDto(DUPLICATED_TITLE, EUR);

        doReturn(Optional.of(new Account(123L, DUPLICATED_TITLE, EUR, ZERO, false)))
                .when(accountsRepository)
                .findByTitle(DUPLICATED_TITLE);

//        when(accountsRepository.findByTitle(DUPLICATED_TITLE))
//                .thenReturn(Optional.of(new Account(123L, DUPLICATED_TITLE, EUR, ZERO, false)));

        assertThrows(AccountWithTitleAlreadyExistsException.class, () -> accountCreate.createAccount(dto));
    }

//    @Test
//    void createAccount_whenOK_thenCorrect() {
//        final var TITLE = "someTitle";
//
//        var accountCreate = new AccountCreate(accountsRepository);
//        var dto = new AccountCreateRequestDto(TITLE, EUR);
//
//        when(accountsRepository.findByTitle(TITLE))
//                .thenReturn(Optional.empty());
//
//        ArgumentCaptor<Account> argument = ArgumentCaptor.forClass(Account.class);
//        when(accountsRepository).save(argument.capture()).thenAnswer(
//                new Answer() {
//                    Object answer(InvocationOnMock invocation) {
//                        argument.getValue();
//
//                        Account account = new Account(23L, TITLE, EUR, ZERO, false);
//
//                        return save(account);
//                    }
//                });
//
//        assertThrows(AccountWithTitleAlreadyExistsException.class, () -> accountCreate.createAccount(dto));
//    }

}
