package dgcd.financier.infra.repository.mapper;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.factory.OperationFactory;
import dgcd.financier.infra.repository.entity.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {
        AccountMapper.class,
        CategoryMapper.class,
})
public interface OperationMapper {

    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);


    @Mappings({
            @Mapping(target = "id", source = "identity"),
    })
    OperationEntity toEntity(Operation operation);


    default Operation fromEntity(OperationEntity operationEntity) {
        return OperationFactory.makeExisting(
                operationEntity.getId(),
                operationEntity.getDate(),
                AccountMapper.INSTANCE.fromEntity(operationEntity.getAccount()),
                operationEntity.getType(),
                operationEntity.getAmount(),
                operationEntity.getQuantity(),
                CategoryMapper.INSTANCE.fromEntity(operationEntity.getSubcategory()),
                operationEntity.getComment(),
                operationEntity.getCounterparty(),
                operationEntity.getIsCanceled(),
                operationEntity.getCorrelationId()
        );
    }

}
