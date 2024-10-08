package dgcd.financier.core.usecase.impl.mapper;

import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);


    AccountDto fromDomain(Account account);

    List<AccountDto> fromDomain(List<Account> accounts);

}
