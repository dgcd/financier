module financier.port.repository {
    requires lombok;
    requires org.slf4j;
    requires org.mapstruct;

    requires spring.tx;
    requires spring.context;
    requires jakarta.validation;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.data.jpa;

    requires financier.core.domain;
    requires financier.core.usecase.api;
    requires spring.jdbc;

    exports dgcd.financier.port.repository;
}
