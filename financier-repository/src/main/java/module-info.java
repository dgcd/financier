module financier.repository {
    requires lombok;

//    requires spring.context;
//    requires spring.core;
//    requires spring.beans;
//    requires spring.web;
//    requires spring.tx;
//    requires spring.boot;
//    requires spring.boot.autoconfigure;
//    requires spring.boot.actuator;
//    requires org.aspectj.weaver;
//    requires org.apache.tomcat.embed.core;
//    requires org.slf4j;

    requires jakarta.validation;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.data.jpa;

    requires financier.domain;
    requires financier.usecase;
}
