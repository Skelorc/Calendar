package study.constants;

/*Константы для месяцев*/
public enum MonthConstant {

    JANUARY("Январь"),
    FEBRUARY("Февраль"),
    MARCH("Март"),
    APRIL("Апрель"),
    MAY("Май"),
    JUNE("Июнь"),
    JULY("Июль"),
    AUGUST("Август"),
    SEPTEMBER("Сентябрь"),
    OCTOBER("Октябрь"),
    NOVEMBER("Ноябрь"),
    DECEMBER("Декабрь");

    private final String value;

    MonthConstant(String value) {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
