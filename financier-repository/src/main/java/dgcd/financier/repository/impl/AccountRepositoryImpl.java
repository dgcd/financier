package dgcd.financier.repository.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.repository.jpa.AccountsJpaRepository;
import dgcd.financier.repository.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountsRepository {

    private final AccountsJpaRepository accountsJpaRepository;


    @Override
    public List<Account> findAll() {
        return accountsJpaRepository.findAll()
                .stream()
                .map(AccountMapper::fromEntity)
                .toList();
    }

}
