module financier.core.usecase.api {
    requires lombok;
    requires io.vavr;

    requires financier.core.domain;

    exports dgcd.financier.core.usecase.api;
    exports dgcd.financier.core.usecase.api.dto;
    exports dgcd.financier.core.usecase.api.dto.common;
    exports dgcd.financier.core.usecase.api.error;
    exports dgcd.financier.core.usecase.api.port.repository;
    exports dgcd.financier.core.usecase.api.port.service;
    exports dgcd.financier.core.usecase.api.utils;
}
