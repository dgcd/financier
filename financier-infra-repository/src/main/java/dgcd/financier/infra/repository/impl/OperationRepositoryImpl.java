package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.infra.repository.jpa.OperationsJpaRepository;
import dgcd.financier.infra.repository.mapper.OperationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OperationRepositoryImpl implements OperationsRepository {

    private final OperationsJpaRepository operationsJpaRepository;

    @Override
    public List<Operation> findAll() {
        return operationsJpaRepository.findAll()
                .stream()
                .map(OperationMapper::fromEntity)
                .toList();
    }


    @Override
    public List<Operation> findAllNotCanceled() {
        return operationsJpaRepository.findByIsCanceledFalse()
                .stream()
                .map(OperationMapper::fromEntity)
                .toList();
    }


    @Override
    public Optional<Operation> findByIdentity(Long identity) {
        var operationEntityOpt = operationsJpaRepository.findById(identity);
        return operationEntityOpt.map(OperationMapper::fromEntity);
    }


    @Override
    public List<Operation> findByCorrelationIdStartingWith(String prefix) {
        return operationsJpaRepository.findByCorrelationIdStartingWith(prefix)
                .stream()
                .map(OperationMapper::fromEntity)
                .toList();
    }


    @Override
    public Operation save(Operation operation) {
        var operationEntity = OperationMapper.toEntity(operation);
        var savedEntity = operationsJpaRepository.save(operationEntity);
        return OperationMapper.fromEntity(savedEntity);
    }


    @Override
    public List<Operation> saveAll(List<Operation> operations) {
        var operationEntities = operations.stream()
                .map(OperationMapper::toEntity)
                .toList();
        return operationsJpaRepository.saveAll(operationEntities)
                .stream()
                .map(OperationMapper::fromEntity)
                .toList();
    }

}
