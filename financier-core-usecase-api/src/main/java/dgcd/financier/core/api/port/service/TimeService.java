package dgcd.financier.core.api.port.service;

import java.time.LocalDate;

@FunctionalInterface
public interface TimeService {

    LocalDate getNowDate();

}
