module financier.repository {
    requires lombok;

    requires spring.context;
    requires jakarta.validation;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.data.jpa;

    requires financier.domain;
    requires financier.usecase;

    exports dgcd.financier.repository.impl;
    exports dgcd.financier.repository.jpa;
}
