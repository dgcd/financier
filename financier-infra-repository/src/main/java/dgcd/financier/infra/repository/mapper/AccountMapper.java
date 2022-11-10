package dgcd.financier.infra.repository.mapper;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.infra.repository.entity.AccountEntity;

public class AccountMapper {

    public static AccountEntity toEntity(Account account) {
        return new AccountEntity(
                account.getIdentity(),
                account.getTitle(),
                account.getCurrency(),
                account.getBalance(),
                account.getIsClosed()
        );
    }

    public static Account fromEntity(AccountEntity accountEntity) {
        return AccountFactory.makeExisting(
                accountEntity.getId(),
                accountEntity.getTitle(),
                accountEntity.getCurrency(),
                accountEntity.getBalance(),
                accountEntity.getIsClosed()
        );
    }

}
