module financier.core.usecase {
    requires lombok;

    requires financier.core.domain;

    exports dgcd.financier.core.usecase;
    exports dgcd.financier.core.usecase.impl;
    exports dgcd.financier.core.usecase.port.repository;
    exports dgcd.financier.core.usecase.port.service;
}
