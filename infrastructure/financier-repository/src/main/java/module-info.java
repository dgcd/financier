module financier.infrastructure.repository {
    requires lombok;

    requires spring.context;
    requires jakarta.validation;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.data.jpa;

    requires financier.core.domain;
    requires financier.core.usecase;

    exports dgcd.financier.infrastructure.repository.entity;
    exports dgcd.financier.infrastructure.repository.impl;
    exports dgcd.financier.infrastructure.repository.jpa;
}
