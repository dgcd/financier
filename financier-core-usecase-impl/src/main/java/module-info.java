module financier.core.usecase.impl {
    requires lombok;
    requires io.vavr;
    requires org.slf4j;
    requires org.mapstruct;

    requires financier.core.domain;
    requires financier.core.usecase.api;

    exports dgcd.financier.core.usecase.impl;
}
