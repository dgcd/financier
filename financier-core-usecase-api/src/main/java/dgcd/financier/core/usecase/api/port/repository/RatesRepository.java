package dgcd.financier.core.usecase.api.port.repository;

import dgcd.financier.core.domain.model.Rate;

import java.util.Optional;

public interface RatesRepository {

//    List<Rate> findAll();

    Optional<Rate> getLastRate();

//    void save(Rate rate);
//
//    List<Rate> saveAll(List<Rate> rates);

}
