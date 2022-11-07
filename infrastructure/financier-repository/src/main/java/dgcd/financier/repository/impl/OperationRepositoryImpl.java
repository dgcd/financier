package dgcd.financier.repository.impl;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.repository.jpa.OperationsJpaRepository;
import dgcd.financier.repository.mapper.OperationMapper;
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
