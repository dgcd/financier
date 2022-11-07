package dgcd.financier.repository.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.repository.entity.AccountEntity;
import dgcd.financier.repository.jpa.AccountsJpaRepository;
import dgcd.financier.repository.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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


    @Override
    public Optional<Account> findByIdentity(Long identity) {
        var accountEntityOpt = accountsJpaRepository.findById(identity);
        return accountEntityOpt.map(AccountMapper::fromEntity);
    }


    @Override
    public boolean existByTitle(String title) {
        return accountsJpaRepository.findByTitle(title).isPresent();
    }


    @Override
    public Account save(Account account) {
        var accountEntity = AccountMapper.toEntity(account);
        var savedEntity = accountsJpaRepository.save(accountEntity);
        return AccountMapper.fromEntity(savedEntity);
    }

}
