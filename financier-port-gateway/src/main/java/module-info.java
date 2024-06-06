module financier.port.gateway {
    requires lombok;
    requires io.vavr;
    requires org.slf4j;
    requires org.mapstruct;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.webmvc;
    requires spring.tx;
    requires org.aspectj.weaver;
    requires jakarta.validation;
    requires org.apache.tomcat.embed.core;
    requires org.apache.poi.ooxml;
    requires io.swagger.v3.oas.annotations;

    requires financier.core.domain;
    requires financier.core.usecase.api;

    exports dgcd.financier.port.gateway.aspects;
    exports dgcd.financier.port.gateway.controller;
    exports dgcd.financier.port.gateway.dto;
    exports dgcd.financier.port.gateway.exception;
    exports dgcd.financier.port.gateway.service;
}
