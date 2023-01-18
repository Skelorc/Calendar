package study.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import study.constants.Days;
import study.constants.MonthConstant;
import study.entity.WorkingShift;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static study.utils.MonthConverter.MONTH_CONVERTER;

public class CalendarView {

    /*Объект даты, где мы будем сохранять дату, выбранную пользователем.*/
    protected LocalDate date;
    @FXML
    private GridPane pane_calendar;
    @FXML
    private ComboBox<MonthConstant> cb_months;
    @FXML
    private ComboBox<Integer> cb_years;
    @FXML
    protected TextField tf_fullName,tf_time;
    @FXML
    private Label lb_date;
    @FXML
    protected TableView<WorkingShift> tv_shifts;
    protected ObservableList<WorkingShift> shiftList;

    /*Инициализация календаря. Добавляем конвертер в combobox с месяцами. Заполняем combobox с годами. Добавляем слушатели для
    * combobox и инициализируем данные в календаре.*/
    public void initCalendar() {
        date = LocalDate.now();
        cb_months.setConverter(MONTH_CONVERTER);
        cb_months.setItems(FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(MonthConstant.values()))));
        List<Integer> years = new ArrayList<>();
        int year = date.getYear();
        for (int i = 0; i < 10; i++) {
            years.add(year++);
        }
        cb_years.setItems(FXCollections.observableArrayList(years));
        cb_months.getSelectionModel().select(MonthConstant.valueOf(date.getMonth().toString()));
        cb_years.getSelectionModel().select(Integer.valueOf(date.getYear()));
        addListenerToCb();
        addDataToCalendar(date);
    }

    /*Отображение прошлого месяца*/
    protected void previousMonth() {
        date = date.minusMonths(1);
        cb_months.getSelectionModel().select(MonthConstant.valueOf(date.getMonth().toString()));
        addDataToCalendar(date);
    }
    /*Отображение следующего месяца*/
    protected void nextMonth() {
        date = date.plusMonths(1);
        cb_months.getSelectionModel().select(MonthConstant.valueOf(date.getMonth().toString()));
        addDataToCalendar(date);
    }

    /*Инициализация столбцов для таблицы смен*/
    public void initTableShifts() {
        ObservableList<TableColumn<WorkingShift, String>> list_columns = FXCollections.observableArrayList(new ArrayList<>());
        list_columns.add(new TableColumn<>("ФИО"));
        list_columns.add(new TableColumn<>("Смена"));
        list_columns.add(new TableColumn<>("Время"));
        createColumns(list_columns);
    }

    /*Создание столбцов прошлого месяца*/
    private void createColumns(ObservableList<TableColumn<WorkingShift, String>> list_columns) {
        for (TableColumn<WorkingShift, String> column : list_columns) {
            configColumns(column, column.getText());
        }
        tv_shifts.getColumns().setAll(list_columns);
    }

    /*Конфигурация отображения ячеек таблицы*/
    private void configColumns(TableColumn<WorkingShift, String> column, String value) {
        switch (value) {
            case "ФИО" -> {
                column.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                column.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            case "Смена" -> column.setCellValueFactory(dataFeatures -> {
                WorkingShift workingShift = dataFeatures.getValue();
                LocalDate dateShift = workingShift.getShift();
                return new SimpleStringProperty(dateShift.toString());
            });
            case "Время" -> {
                column.setCellValueFactory(new PropertyValueFactory<>("time"));
                column.setCellFactory(TextFieldTableCell.forTableColumn());
            }
        }
    }

    /*добавление данных в календарь. Если в дате есть смена, то отобразится ...*/
    private void addDataToCalendar(LocalDate date) {
        clearData();
        for (int day = 1; day <= 7; day++) {
            Label lDayName = new Label(Days.getByNum(day).getValue());
            pane_calendar.add(lDayName, day - 1, 0);
        }
        int daysInMonth = date.lengthOfMonth();
        int dayOfWeek = date.withDayOfMonth(1).getDayOfWeek().getValue();
        int row = 1;
        for (int i = 1; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i);
            LocalDate dateNow = LocalDate.of(date.getYear(), date.getMonthValue(), i);
            shiftList.stream().filter(x -> x.getShift().equals(dateNow)).forEach(x -> stringBuilder.append("\n").append("..."));
            Label lDate = new Label(stringBuilder.toString());
            pane_calendar.add(lDate, dayOfWeek - 1, row);
            dayOfWeek++;
        }
        addListenerToPane();
    }



    /*Добавление слушателя к combobox. При изменении месяца или года, меняется наш объект date.*/
    private void addListenerToCb() {
        cb_months.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                Month month = Month.valueOf(String.valueOf(newValue));
                String year = cb_years.getSelectionModel().getSelectedItem().toString();
                date = LocalDate.of(Integer.parseInt(year), month.getValue(), LocalDate.now().getDayOfMonth());
                addDataToCalendar(date);
            }
        });

        cb_years.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                Month month = Month.valueOf(String.valueOf(cb_months.getSelectionModel().getSelectedItem()));
                int year = newValue;
                date = LocalDate.of(year, month, LocalDate.now().getDayOfMonth());
                addDataToCalendar(date);
            }
        });
    }

    /*Слушатель к календарю. При клике на какое либо число,
    если в это число есть рабочие смены, то отображаем их в таблице TableView.*/
    private void addListenerToPane() {
        pane_calendar.getChildren().forEach(item -> {
            item.setOnMouseClicked(event -> {
                Label s = (Label) item;
                String[] split = s.getText().split("\n");
                int day = Integer.parseInt(split[0]);
                date = LocalDate.of(date.getYear(), date.getMonth(), day);
                List<WorkingShift> shiftsByDate = shiftList.stream().filter(x -> x.getShift().equals(date)).collect(Collectors.toList());
                tv_shifts.getItems().clear();
                tv_shifts.getItems().addAll(shiftsByDate);
                lb_date.setText(day + "-" + MonthConstant.valueOf(date.getMonth().toString()).getValue() + "-" + date.getYear());
            });
        });
    }

    /*Очистка данных в полях*/
    private void clearData() {
        pane_calendar.setGridLinesVisible(false);
        pane_calendar.getChildren().clear();
        pane_calendar.setGridLinesVisible(true);
    }
}
