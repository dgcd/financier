module financier.infra.exchange {
    requires lombok;
    requires org.slf4j;

    requires spring.context;
    requires spring.beans;
    requires spring.web;

    requires financier.core.usecase;

    exports dgcd.financier.infra.exchange;
}
