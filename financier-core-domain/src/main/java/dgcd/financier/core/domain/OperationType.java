package dgcd.financier.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperationType {

    BASE(false),
    TRANS(true),
    INCOME(false),
    EXPENSE(false),
    EXCHANGE(true),
    ;

    private final boolean paired;

}
