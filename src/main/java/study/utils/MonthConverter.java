package study.utils;


import javafx.util.StringConverter;
import study.constants.MonthConstant;

public class MonthConverter {

    /*Конвертер нашего enum с месяцами для работы с combobox. Конвертируем в String и из String.*/
    public static final StringConverter<MonthConstant> MONTH_CONVERTER = new StringConverter<>() {
        @Override
        public String toString(MonthConstant monthConstant) {
            return monthConstant.getValue();
        }

        @Override
        public MonthConstant fromString(String string) {
            return MonthConstant.valueOf(string);
        }
    };
}
