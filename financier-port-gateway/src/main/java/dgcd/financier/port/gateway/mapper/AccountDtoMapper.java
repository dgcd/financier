package dgcd.financier.port.gateway.mapper;

import dgcd.financier.core.usecase.api.dto.AccountCreateRequestDto;
import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import dgcd.financier.port.gateway.dto.AccountResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountDtoMapper {

    AccountResponseDto fromUsecase(AccountDto account);

    AccountCreateRequestDto toCreateUsecase(dgcd.financier.port.gateway.dto.AccountCreateRequestDto dto);

}
