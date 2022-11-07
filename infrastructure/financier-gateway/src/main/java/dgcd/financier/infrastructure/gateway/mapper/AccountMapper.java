package dgcd.financier.infrastructure.gateway.mapper;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.usecase.AccountCloseUsecase;
import dgcd.financier.core.usecase.AccountCreateUsecase;
import dgcd.financier.infrastructure.gateway.dto.AccountCreateRequestDto;
import dgcd.financier.infrastructure.gateway.dto.AccountResponseDto;
import dgcd.financier.infrastructure.gateway.dto.CommonIdDto;
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

    AccountCreateUsecase.Request toCreateUsecase(AccountCreateRequestDto dto);

    @Mappings({
            @Mapping(target = "identity", source = "dto.id"),
    })
    AccountCloseUsecase.Request toCloseUsecase(CommonIdDto dto);

}
