package dgcd.financier.core.usecase.impl.testData

import dgcd.financier.core.domain.model.Account

import java.util.function.Consumer

import static dgcd.financier.core.domain.CurrencyType.RUB

class AccountTestData {

    static Account account(Consumer<Account> modify = {}) {
        def account = new Account()
                .setId(42L)
                .setTitle("Account RUB")
                .setCurrency(RUB)
                .setBalance(BigDecimal.valueOf(99.95))
                .tap { modify(it) }
        account
    }

}
