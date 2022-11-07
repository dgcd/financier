module financier.gateway {
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

    requires financier.domain;
    requires financier.usecase;

    exports dgcd.financier.gateway.aspects;
    exports dgcd.financier.gateway.controller;
    exports dgcd.financier.gateway.dto;
    exports dgcd.financier.gateway.exception;
    exports dgcd.financier.gateway.mapper;
    exports dgcd.financier.gateway.service;
}
