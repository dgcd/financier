package dgcd.financier.modules.category.dto;

public record CategoryResponseDto(
        Long id,
        String title,
        Long parentId,
        String parentTitle
) {

//    public static CategoryResponseDto of(Category category) {
//        var parent = category.getParent();
//        var hasParent = nonNull(parent);
//        return new CategoryResponseDto(
//                category.getId(),
//                category.getTitle(),
//                hasParent ? parent.getId() : null,
//                hasParent ? parent.getTitle() : null
//        );
//    }

}
