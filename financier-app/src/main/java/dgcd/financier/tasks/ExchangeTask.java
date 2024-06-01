//package dgcd.financier.tasks;
//
//import dgcd.financier.infra.exchange.ExchangeService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.context.event.ApplicationStartedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ExchangeTask {
//
//    private final ExchangeService exchangeService;
//
//    @EventListener(ApplicationStartedEvent.class)
//    public void executorServiceStarter() {
//        exchangeService.updateRates();
//    }
//
//}
