package dgcd.financier.infrastructure.repository.impl;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.infrastructure.repository.mapper.OperationMapper;
import dgcd.financier.infrastructure.repository.jpa.OperationsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OperationRepositoryImpl implements OperationsRepository {

    private final OperationsJpaRepository operationsJpaRepository;

    @Override
    public List<Operation> findAllNotCanceled() {
        return operationsJpaRepository.findByIsCanceledFalse()
                .stream()
                .map(OperationMapper::fromEntity)
                .toList();
    }

}
