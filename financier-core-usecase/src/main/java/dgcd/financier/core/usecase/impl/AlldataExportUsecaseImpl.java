package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.usecase.AlldataExportUsecase;
import dgcd.financier.core.usecase.AlldataUsecase;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class AlldataExportUsecaseImpl implements AlldataExportUsecase {

    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;


    @Override
    public Response execute(Request request) {
        return new Response(new AlldataUsecase.AlldataRows(
                getAndSortAccounts(),
                getAndSortCategories(),
                getAndSortOperations()
        ));
    }


    private List<AccountRow> getAndSortAccounts() {
        return accountsRepository.findAll()
                .stream()
                .sorted((a1, a2) -> a1.getIsClosed().equals(a2.getIsClosed()) ?
                        a1.getCurrency().equals(a2.getCurrency()) ?
                                a1.getTitle().compareTo(a2.getTitle()) :
                                a1.getCurrency().compareTo(a2.getCurrency()) :
                        a1.getIsClosed() ? 1 : -1
                )
                .map(AccountRow::of)
                .toList();
    }


    private List<CategoryRow> getAndSortCategories() {
        return categoriesRepository.findAll()
                .stream()
                .filter(c -> nonNull(c.getParent()))
                .sorted((c1, c2) ->
                        c1.getParent().getTitle().equals(c2.getParent().getTitle()) ?
                                c1.getTitle().compareTo(c2.getTitle()) :
                                c1.getParent().getTitle().compareTo(c2.getParent().getTitle()))
                .map(CategoryRow::of)
                .toList();
    }


    private List<OperationRow> getAndSortOperations() {
        return operationsRepository.findAllNotCanceled()
                .stream()
                .sorted((o1, o2) -> o1.getDate().equals(o2.getDate()) ?
                        o1.getType().equals(o2.getType()) ?
                                o1.getIdentity().compareTo(o2.getIdentity()) :
                                o1.getType().compareTo(o2.getType()) :
                        o1.getDate().compareTo(o2.getDate())
                )
                .map(OperationRow::of)
                .toList();
    }

}
