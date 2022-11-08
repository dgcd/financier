module financier.infra.gateway {
    requires lombok;
    requires org.mapstruct;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    requires spring.tx;
    requires org.aspectj.weaver;
    requires org.slf4j;
    requires jakarta.validation;

    requires financier.core.domain;
    requires financier.core.usecase;

    exports dgcd.financier.infra.gateway.aspects;
    exports dgcd.financier.infra.gateway.controller;
    exports dgcd.financier.infra.gateway.dto;
    exports dgcd.financier.infra.gateway.exception;
    exports dgcd.financier.infra.gateway.mapper;
    exports dgcd.financier.infra.gateway.service;
}
