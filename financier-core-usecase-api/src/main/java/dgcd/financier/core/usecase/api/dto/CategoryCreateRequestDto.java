package dgcd.financier.core.usecase.api.dto;

public record CategoryCreateRequestDto(
        String title,
        Long parentId
) {

    public boolean createParent() {
        return parentId == null;
    }

}
