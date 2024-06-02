package dgcd.financier.core.usecase.api.port.service;

import java.time.LocalDate;

@FunctionalInterface
public interface TimeService {

    LocalDate getNowDate();

}
