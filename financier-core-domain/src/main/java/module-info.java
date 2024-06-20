module financier.core.domain {
    requires lombok;

    exports dgcd.financier.core.domain;
    exports dgcd.financier.core.domain.model to
            financier.core.usecase.api,
            financier.core.usecase.impl,
            financier.port.repository;
}
