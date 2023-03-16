package pro.sky.budgetapp.model;

public enum Category {
    FOOD("Еда"),
    DRINKS("Напитки"),
    CLOTHES("Одежда"),
    TRANSPORT("Транспорт"),
    HOBBY("Хобби"),
    FUN("Развлечения"),
    ERROR("Ошибочное перечисление");

    private final String text;

    Category(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
