package dgcd.financier.core.domain;

public interface Category {

    Long getIdentity();

    String getTitle();

    Category getParent();

    boolean isParent();

}
