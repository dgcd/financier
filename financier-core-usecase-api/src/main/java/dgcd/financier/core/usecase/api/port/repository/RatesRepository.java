package dgcd.financier.core.usecase.api.port.repository;

import dgcd.financier.core.domain.model.Rate;

import java.util.List;
import java.util.Optional;

public interface RatesRepository {

    List<Rate> findAll();

    Optional<Rate> getLastRate();

    void createOrUpdate(Rate rate);

    void createAll(List<Rate> rates);

}
