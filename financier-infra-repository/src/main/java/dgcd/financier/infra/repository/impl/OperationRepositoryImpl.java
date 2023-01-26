package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.infra.repository.jpa.OperationsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static dgcd.financier.infra.repository.mapper.OperationMapper.INSTANCE;

@Repository
@RequiredArgsConstructor
public class OperationRepositoryImpl implements OperationsRepository {

    private final OperationsJpaRepository operationsJpaRepository;

    @Override
    public List<Operation> findAll() {
        return operationsJpaRepository.findAll()
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }


    @Override
    public List<Operation> findAllNotCanceled() {
        return operationsJpaRepository.findByIsCanceledFalse()
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }


    @Override
    public Optional<Operation> findByIdentity(Long identity) {
        var operationEntityOpt = operationsJpaRepository.findById(identity);
        return operationEntityOpt.map(INSTANCE::fromEntity);
    }


    @Override
    public List<Operation> findByCorrelationIdStartingWith(String prefix) {
        return operationsJpaRepository.findByCorrelationIdStartingWith(prefix)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }


    @Override
    public Operation save(Operation operation) {
        var operationEntity = INSTANCE.toEntity(operation);
        var savedEntity = operationsJpaRepository.save(operationEntity);
        return INSTANCE.fromEntity(savedEntity);
    }


    @Override
    public List<Operation> saveAll(List<Operation> operations) {
        var operationEntities = operations.stream()
                .map(INSTANCE::toEntity)
                .toList();
        return operationsJpaRepository.saveAll(operationEntities)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }

}
