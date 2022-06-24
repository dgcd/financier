package dgcd.financier;

import dgcd.financier.modules.account.AccountsJpaRepository;
import dgcd.financier.usecase.AccountCreate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FinancierApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancierApplication.class, args);
    }

    @Bean
    public AccountCreate accountCreate(AccountsJpaRepository accountsJpaRepository) {
        return new AccountCreate(accountsJpaRepository);
    }

}
