package dgcd.financier.usecase.mappers;

import dgcd.financier.domain.entity.Account;
import dgcd.financier.usecase.dto.AccountCreateRequestDto;
import dgcd.financier.usecase.dto.AccountResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "title", expression = "java(dto.title().trim())"),
            @Mapping(target = "currency", source = "dto.currency"),
            @Mapping(target = "balance", ignore = true),
            @Mapping(target = "isClosed", ignore = true),
    })
    Account accountCreateRequestDtoToAccount(AccountCreateRequestDto dto);


    AccountResponseDto accountToResponseDto(Account account);

}
