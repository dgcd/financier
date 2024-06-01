module financier.port.exchange {
    requires lombok;
    requires org.slf4j;

    requires spring.context;
    requires spring.beans;
    requires spring.web;

    requires financier.core.usecase.api;

    exports dgcd.financier.port.exchange;
}
