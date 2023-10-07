package by.teachmeskills.springbootproject.enums;

public enum PagesPathEnum {
    HOME_PAGE("home"),
    LOGIN_PAGE("login"),
    CATEGORY_PAGE("category"),
    CART_PAGE("cart"),
    PRODUCT_PAGE("product"),
    REGISTRATION_PAGE("registration"),
    ACCOUNT_PAGE("account"),
    SEARCH_PAGE("search"),
    ERROR_PAGE("error"),
    STATISTIC_PAGE("statistic");

    private final String path;

    PagesPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
