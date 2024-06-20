package dgcd.financier.core.domain.validation

import dgcd.financier.core.domain.model.Account
import spock.lang.Specification

import java.util.function.Consumer

import static dgcd.financier.core.domain.CurrencyType.RUB

class AccountValidatorTest extends Specification {

    def "Validate with valid id - OK"() {
        given:
        def account = account() { it.id = id }

        when:
        account.validate()

        then:
        noExceptionThrown()

        where:
        id << [null, 1, 100]
    }


    def "Validate with wrong id - ERROR"() {
        given:
        def account = account() { it.id = id }

        when:
        account.validate()

        then:
        thrown(IllegalArgumentException)

        where:
        id << [-1, 0]
    }


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
