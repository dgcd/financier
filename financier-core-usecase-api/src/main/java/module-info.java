module financier.core.usecase.api {
    requires lombok;
    requires io.vavr;

    requires financier.core.domain;

    exports dgcd.financier.core.api;
    exports dgcd.financier.core.api.dto;
    exports dgcd.financier.core.api.dto.common;
    exports dgcd.financier.core.api.error;
    exports dgcd.financier.core.api.port.repository;
    exports dgcd.financier.core.api.port.service;
}
