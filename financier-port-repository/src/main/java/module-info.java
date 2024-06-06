module financier.port.repository {
    requires java.sql;
    requires lombok;
    requires org.slf4j;
    requires org.mapstruct;

    requires spring.tx;
    requires spring.context;
    requires jakarta.validation;

    requires financier.core.domain;
    requires financier.core.usecase.api;
    requires spring.jdbc;
    requires spring.beans;

    exports dgcd.financier.port.repository;
}
