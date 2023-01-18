package study.constants;

import java.util.Arrays;

/*Константы для дней недели*/
public enum Days {

    MONDAY("Понедельник",1),
    TUESDAY("Вторник",2),
    WEDNESDAY("Среда",3),
    THURSDAY("Четверг",4),
    FRIDAY("Пятница",5),
    SATURDAY("Суббота",6),
    SUNDAY("Воскресенье",7);


    private String value;
    private int numberOfDay;

    Days(String value, int numberOfDay) {
        this.value = value;
        this.numberOfDay = numberOfDay;
    }

    public String getValue()
    {
        return value;
    }

    public int getNumberOfDay()
    {
        return numberOfDay;
    }

    public static Days getByNum(int numberOfDay)
    {
        return Arrays.stream(values()).filter(x -> x.getNumberOfDay()==numberOfDay).findFirst().orElseThrow();
    }


}
