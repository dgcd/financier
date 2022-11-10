module financier.infra.gateway {
    requires lombok;
    requires org.slf4j;
    requires org.mapstruct;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.tx;
    requires org.aspectj.weaver;
    requires jakarta.validation;
    requires org.apache.tomcat.embed.core;
    requires org.apache.poi.ooxml;

    requires financier.core.domain;
    requires financier.core.usecase;

    exports dgcd.financier.infra.gateway.aspects;
    exports dgcd.financier.infra.gateway.controller;
    exports dgcd.financier.infra.gateway.dto;
    exports dgcd.financier.infra.gateway.exception;
    exports dgcd.financier.infra.gateway.mapper;
    exports dgcd.financier.infra.gateway.service;
}
