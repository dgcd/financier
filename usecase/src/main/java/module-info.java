module financier.usecase {
    requires spring.tx;
    requires lombok;
    requires org.mapstruct;

    requires financier.domain;

    exports dgcd.financier.usecase;
    exports dgcd.financier.usecase.dto;
    exports dgcd.financier.usecase.port;
    exports dgcd.financier.usecase.exception;
}
