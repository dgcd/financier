module dgcd.financier.app {
    requires spring.core;
    requires spring.web;
    requires spring.beans;
    requires spring.context;
    requires spring.tx;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.boot.actuator;
    requires spring.data.jpa;

    requires jakarta.validation;
    requires jakarta.persistence;

    requires org.aspectj.weaver;
    requires org.hibernate.orm.core;
    requires org.apache.tomcat.embed.core;
    requires org.apache.poi.ooxml;
    requires com.fasterxml.jackson.annotation;

    requires lombok;

    requires financier.domain;
    requires financier.usecase;
}
