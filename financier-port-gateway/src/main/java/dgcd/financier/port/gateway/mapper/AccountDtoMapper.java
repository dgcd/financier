package dgcd.financier.port.gateway.mapper;

import dgcd.financier.core.api.dto.common.AccountDto;
import dgcd.financier.port.gateway.dto.AccountResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountDtoMapper {

    AccountResponseDto fromDomain(AccountDto account);

//    AccountCreateUsecase.Request toCreateUsecase(AccountCreateRequestDto dto);
//
//     AccountCloseUsecase.Request toCloseUsecase(CommonIdDto dto);

}
