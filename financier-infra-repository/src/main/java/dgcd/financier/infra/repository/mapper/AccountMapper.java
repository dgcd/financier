package dgcd.financier.infra.repository.mapper;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.infra.repository.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);


    @Mappings({
            @Mapping(target = "id", source = "identity"),
    })
    AccountEntity toEntity(Account account);


    default Account fromEntity(AccountEntity accountEntity) {
        return AccountFactory.makeExisting(
                accountEntity.getId(),
                accountEntity.getTitle(),
                accountEntity.getCurrency(),
                accountEntity.getBalance(),
                accountEntity.getIsClosed()
        );
    }

}
