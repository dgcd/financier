package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.infra.repository.jpa.OperationsJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static dgcd.financier.infra.repository.mapper.OperationMapper.INSTANCE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OperationRepositoryImpl implements OperationsRepository {

    private final OperationsJpaRepository operationsJpaRepository;

    @Override
    public List<Operation> findAll() {
        var operations = operationsJpaRepository.findAll()
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        log.debug("[findAll] operations size: {}", operations.size());

        return operations;
    }


    @Override
    public List<Operation> findAllNotCanceled() {
        var operations = operationsJpaRepository.findByIsCanceledFalse()
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        log.debug("[findAllNotCanceled] operations size: {}", operations.size());

        return operations;
    }


    @Override
    public Optional<Operation> findByIdentity(Long identity) {
        log.debug("[findByIdentity] identity: {}", identity);

        var operationEntityOpt = operationsJpaRepository.findById(identity);
        var operationOpt = operationEntityOpt.map(INSTANCE::fromEntity);

        log.debug("[findByIdentity] category: {}", operationOpt.orElse(null));

        return operationOpt;
    }


    @Override
    public List<Operation> findByCorrelationIdStartingWith(String prefix) {
        log.debug("[findByCorrelationIdStartingWith] prefix: {}", prefix);

        var operations = operationsJpaRepository.findByCorrelationIdStartingWith(prefix)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        if (log.isDebugEnabled()) {
            operations.forEach(operation -> log.debug("[findByCorrelationIdStartingWith] operation: {}", operation));
        }

        return operations;
    }


    @Override
    public Operation save(Operation operation) {
        var operationEntity = INSTANCE.toEntity(operation);
        var savedEntity = operationsJpaRepository.save(operationEntity);
        var savedOperation = INSTANCE.fromEntity(savedEntity);

        log.debug("[save] operation: {}", savedOperation);

        return savedOperation;
    }


    @Override
    public List<Operation> saveAll(List<Operation> operations) {
        var operationEntities = operations.stream()
                .map(INSTANCE::toEntity)
                .toList();
        var savedOperations = operationsJpaRepository.saveAll(operationEntities)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        if (log.isDebugEnabled()) {
            savedOperations.forEach(operation -> log.debug("[saveAll] operation: {}", operation));
        }

        return savedOperations;
    }

}
