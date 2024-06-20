package dgcd.financier.port.gateway.service;

import dgcd.financier.core.usecase.api.AccountCloseUsecase;
import dgcd.financier.core.usecase.api.AccountCreateUsecase;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.port.gateway.dto.AccountCreateRequestDto;
import dgcd.financier.port.gateway.dto.AccountResponseDto;
import dgcd.financier.port.gateway.dto.CommonIdDto;
import dgcd.financier.port.gateway.mapper.AccountDtoMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountCreateUsecase accountCreateUsecase;
    private final AccountCloseUsecase accountCloseUsecase;
    private final AccountDtoMapper accountDtoMapper;


    @Transactional
    public Either<CommonError, AccountResponseDto> createAccount(AccountCreateRequestDto dto) {
        return toRight(dto)
                .map(accountDtoMapper::toCreateUsecase)
                .flatMap(accountCreateUsecase::execute)
                .map(accountDtoMapper::fromUsecase);
    }

    @Transactional
    public Either<CommonError, AccountResponseDto> closeAccount(CommonIdDto dto) {
        return toRight(dto)
                .map(CommonIdDto::id)
                .flatMap(accountCloseUsecase::execute)
                .map(accountDtoMapper::fromUsecase);
    }

}
