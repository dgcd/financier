package dgcd.financier.modules.dto.mapper;

import dgcd.financier.core.domain.Account;
import dgcd.financier.modules.dto.AccountResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    @Mappings({
            @Mapping(target = "id", source = "account.identity"),
    })
    AccountResponseDto fromDomain(Account account);

}
