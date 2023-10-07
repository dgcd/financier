module financier.app {
    requires lombok;
    requires org.slf4j;

    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.boot.actuator;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.tomcat.embed.core;

    requires financier.core.usecase;
    requires financier.infra.gateway;
    requires financier.infra.repository;
    requires financier.infra.exchange;
}
