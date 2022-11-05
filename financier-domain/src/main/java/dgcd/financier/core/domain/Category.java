package dgcd.financier.core.domain;

public interface Category {

    Long getId();

    String getTitle();

    Category getParent();

    boolean isParent();

}
