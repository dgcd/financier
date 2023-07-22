package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.infra.repository.jpa.AccountsJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static dgcd.financier.infra.repository.mapper.AccountMapper.INSTANCE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountsRepository {

    private final AccountsJpaRepository accountsJpaRepository;


    @Override
    public List<Account> findAll() {
        var accounts = accountsJpaRepository.findAll()
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        log.debug("[findAll] accounts size: {}", accounts.size());

        return accounts;
    }


    @Override
    public Optional<Account> findByIdentity(Long identity) {
        log.debug("[findByIdentity] identity: {}", identity);

        var accountEntityOpt = accountsJpaRepository.findById(identity);
        var accountOpt = accountEntityOpt.map(INSTANCE::fromEntity);

        log.debug("[findByIdentity] account: {}", accountOpt.orElse(null));

        return accountOpt;
    }


    @Override
    public boolean existByTitle(String title) {
        log.debug("[existByTitle] title: {}", title);

        var isPresent = accountsJpaRepository.findByTitle(title).isPresent();

        log.debug("[existByTitle] isPresent: {}", isPresent);

        return isPresent;
    }


    @Override
    public Account save(Account account) {
        var accountEntity = INSTANCE.toEntity(account);
        var savedEntity = accountsJpaRepository.save(accountEntity);
        var savedAccount = INSTANCE.fromEntity(savedEntity);

        log.debug("[save] account: {}", savedAccount);

        return savedAccount;
    }


    @Override
    public List<Account> saveAll(List<Account> accounts) {
        var accountEntities = accounts.stream()
                .map(INSTANCE::toEntity)
                .toList();
        var savedAccounts = accountsJpaRepository.saveAll(accountEntities)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        if (log.isDebugEnabled()) {
            savedAccounts.forEach(account -> log.debug("[saveAll] account: {}", account));
        }

        return savedAccounts;
    }

}
