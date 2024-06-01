package dgcd.financier.core.domain.validation

import spock.lang.Specification

import static dgcd.financier.core.domain.validation.ValidationUtils.checkId

class ValidationUtilsTest extends Specification {

    def "CheckId - OK"() {
        when:
        checkId(id, "id")

        then:
        noExceptionThrown()

        where:
        id << [null, 1, 42, 1000000]
    }


    def "CheckId - ERROR"() {
        when:
        checkId(id, "id")

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == message

        where:
        id || message
        -1 || "id must be greater then 0 but was: -1"
        0  || "id must be greater then 0 but was: 0"
    }


    def "CheckId null field title - ERROR"() {
        when:
        checkId(-42, null)

        then:
        def ex = thrown(NullPointerException)
        ex.message == "field title can not be null"
    }

}
