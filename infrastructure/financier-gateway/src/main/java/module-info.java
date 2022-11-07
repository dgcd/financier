module financier.infrastructure.gateway {
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

    exports dgcd.financier.infrastructure.gateway.aspects;
    exports dgcd.financier.infrastructure.gateway.controller;
    exports dgcd.financier.infrastructure.gateway.dto;
    exports dgcd.financier.infrastructure.gateway.exception;
    exports dgcd.financier.infrastructure.gateway.mapper;
    exports dgcd.financier.infrastructure.gateway.service;
}
