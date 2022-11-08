module financier.app {
    requires lombok;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    requires spring.tx;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.boot.actuator;
    requires spring.data.jpa;
    requires org.aspectj.weaver;
//    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
    requires jakarta.validation;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    requires financier.core.domain;
    requires financier.core.usecase;
    requires financier.infra.gateway;
    requires financier.infra.repository;
}
