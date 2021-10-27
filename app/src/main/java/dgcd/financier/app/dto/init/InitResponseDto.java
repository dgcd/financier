package dgcd.financier.app.dto.init;

import dgcd.financier.app.domain.model.Account;

import java.util.List;

public record InitResponseDto(
        List<Account> accounts
) {}
