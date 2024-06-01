package dgcd.financier.core.usecase.impl.mapper

import dgcd.financier.core.domain.CurrencyType
import dgcd.financier.core.usecase.impl.testData.AccountTestData
import spock.lang.Specification

class AccountMapperTest extends Specification {

    def "Should map single account - OK"() {
        given:
        def account = AccountTestData.account()

        when:
        def result = AccountMapper.INSTANCE.fromDomain(account)

        then:
        result.id() == 42
        result.title() == "Account RUB"
        result.currency() == CurrencyType.RUB
        result.balance() == BigDecimal.valueOf(99.95)
    }


    def "Should map list of accounts - OK"() {
        given:
        def accounts = [
                AccountTestData.account(),
                AccountTestData.account { it.id = 100 }
        ]

        when:
        def result = AccountMapper.INSTANCE.fromDomain(accounts)

        then:
        result.size() == 2
        verifyAll(result[0]) {
            id() == 42
            title() == "Account RUB"
            currency() == CurrencyType.RUB
            balance() == BigDecimal.valueOf(99.95)
        }
        verifyAll(result[1]) {
            id() == 100
            title() == "Account RUB"
            currency() == CurrencyType.RUB
            balance() == BigDecimal.valueOf(99.95)
        }
    }

}
