package dgcd.financier.infrastructure.repository.mapper;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.factory.OperationFactory;
import dgcd.financier.infrastructure.repository.entity.OperationEntity;

public class OperationMapper {

    public static OperationEntity toEntity(Operation operation) {
        return new OperationEntity(
                operation.getIdentity(),
                operation.getDate(),
                AccountMapper.toEntity(operation.getAccount()),
                operation.getType(),
                operation.getAmount(),
                operation.getQuantity(),
                CategoryMapper.toEntity(operation.getSubcategory()),
                operation.getComment(),
                operation.getCounterparty(),
                operation.getIsCanceled(),
                operation.getCorrelationId()
        );
    }


    public static Operation fromEntity(OperationEntity operationEntity) {
        return OperationFactory.makeExisting(
                operationEntity.getId(),
                operationEntity.getDate(),
                AccountMapper.fromEntity(operationEntity.getAccount()),
                operationEntity.getType(),
                operationEntity.getAmount(),
                operationEntity.getQuantity(),
                CategoryMapper.fromEntity(operationEntity.getSubcategory()),
                operationEntity.getComment(),
                operationEntity.getCounterparty(),
                operationEntity.getIsCanceled(),
                operationEntity.getCorrelationId()
        );
    }

}
