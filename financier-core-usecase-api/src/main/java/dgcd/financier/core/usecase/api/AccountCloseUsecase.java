package dgcd.financier.core.usecase.api;

import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;

@FunctionalInterface
public interface AccountCloseUsecase {

    Either<CommonError, AccountDto> execute(Long id);

}
