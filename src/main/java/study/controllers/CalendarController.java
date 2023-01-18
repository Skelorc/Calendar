package study.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import study.entity.WorkingShift;
import study.models.ShiftModel;
import study.views.CalendarView;

import java.util.List;
import java.util.stream.Collectors;

import static study.messages.StaticMessage.createInfodialog;

public class CalendarController extends CalendarView {

    /*Инициализация модели */
    private final ShiftModel shiftModel = new ShiftModel();

    /*Метод инициализации контроллера, внутри которого, получаем список всех текущих рабочих смен. Будем использовать этот список
    * как кэш. Также сразу инициализируем внутри элементы.*/
    @FXML
    void initialize() {
        shiftList = FXCollections.observableArrayList(shiftModel.getAllShifts());
        initCalendar();
        initTableShifts();
    }
    /*Показать прошлый месяц*/
    @FXML
    public void showPreviousMonth()
    {
        previousMonth();
    }

    /*Показать следующий месяц*/
    @FXML
    public void showNextMonth()
    {
        nextMonth();
    }

    /*Создание и сохранение смены в бд с добавлением в кэш.
     с.48 и 49 очищает поля для ввода данных. Последняя строка выводит информационное сообщение.*/
    @FXML
    public void createShift()
    {
        WorkingShift workingShift = shiftModel.create(date, tf_fullName.getText(),tf_time.getText());
        shiftList.add(workingShift);
        initCalendar();
        tf_fullName.clear();
        tf_time.clear();
        createInfodialog("Пользователь " + tf_fullName.getText() + " добавлен в базу!");
    }

    /*Удаление смены с базы и из кэша.*/

    @FXML
    public void deleteShift ()
    {
        WorkingShift selectedItem = tv_shifts.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
        {
            shiftModel.deleteById(selectedItem.getId());
            List<WorkingShift> forRemoved = shiftList.stream().filter(x -> x.getFullName().equals(selectedItem.getFullName())).collect(Collectors.toList());
            forRemoved.forEach(x -> shiftList.remove(x));
            initCalendar();
            tf_fullName.clear();
            tf_time.clear();
            createInfodialog("Пользователь " + selectedItem.getFullName() + " удален из базы!");
        }
    }
}
