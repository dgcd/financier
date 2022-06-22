module financier.domain {
    requires jakarta.validation;
    requires jakarta.persistence;
    requires lombok;

    exports dgcd.financier.domain.dictionary;
    exports dgcd.financier.domain.entity;
}
