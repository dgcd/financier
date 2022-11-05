package dgcd.financier.domain;

public interface Category {

    Long getId();

    String getTitle();

    Category getParent();

    boolean isParent();

}
