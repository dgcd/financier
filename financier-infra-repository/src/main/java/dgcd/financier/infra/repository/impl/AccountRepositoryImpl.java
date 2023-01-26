package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.infra.repository.jpa.AccountsJpaRepository;
import dgcd.financier.infra.repository.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static dgcd.financier.infra.repository.mapper.AccountMapper.INSTANCE;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountsRepository {

    private final AccountsJpaRepository accountsJpaRepository;


    @Override
    public List<Account> findAll() {
        return accountsJpaRepository.findAll()
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }


    @Override
    public Optional<Account> findByIdentity(Long identity) {
        var accountEntityOpt = accountsJpaRepository.findById(identity);
        return accountEntityOpt.map(INSTANCE::fromEntity);
    }


    @Override
    public boolean existByTitle(String title) {
        return accountsJpaRepository.findByTitle(title).isPresent();
    }


    @Override
    public Account save(Account account) {
        var accountEntity = INSTANCE.toEntity(account);
        var savedEntity = accountsJpaRepository.save(accountEntity);
        return INSTANCE.fromEntity(savedEntity);
    }


    @Override
    public List<Account> saveAll(List<Account> accounts) {
        var accountEntities = accounts.stream()
                .map(INSTANCE::toEntity)
                .toList();
        return accountsJpaRepository.saveAll(accountEntities)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }

}
