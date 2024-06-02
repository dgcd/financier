package dgcd.financier.core.usecase.api;

import dgcd.financier.core.usecase.api.dto.AccountCreateRequestDto;
import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;

@FunctionalInterface
public interface AccountCreateUsecase {

    Either<CommonError, AccountDto> execute(AccountCreateRequestDto request);

}
