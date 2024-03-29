module financier.infra.repository {
    requires lombok;
    requires org.slf4j;
    requires org.mapstruct;

    requires spring.context;
    requires jakarta.validation;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.data.jpa;

    requires financier.core.domain;
    requires financier.core.usecase;

    exports dgcd.financier.infra.repository.entity;
    exports dgcd.financier.infra.repository.impl;
    exports dgcd.financier.infra.repository.jpa;
}
