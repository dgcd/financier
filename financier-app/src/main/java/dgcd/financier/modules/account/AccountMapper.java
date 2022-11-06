package dgcd.financier.modules.account;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.usecase.AccountCreateUsecase;
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

    AccountCreateUsecase.Request toUsecase(AccountCreateRequestDto dto);

}
