package dgcd.financier.core.api;

import dgcd.financier.core.api.dto.AccountCreateRequestDto;
import dgcd.financier.core.api.dto.common.AccountDto;
import dgcd.financier.core.api.error.CommonError;
import io.vavr.control.Either;

@FunctionalInterface
public interface AccountCreateUsecase {

    Either<CommonError, AccountDto> execute(AccountCreateRequestDto request);

}
