package study.models;

/*
 *@author Skelorc
 */

import study.DAO.ShiftDAO;
import study.entity.WorkingShift;

import java.time.LocalDate;
import java.util.List;

import static study.messages.StaticMessage.createErrorAlertDialog;

public class ShiftModel {
    /*Инициализируем DAO.*/
    private final ShiftDAO dao = new ShiftDAO();
    /*Создание смены. Внутри проверяем, не пустые ли поля перед созданием, иначе выводим диалоговое окно и бросаем исключение.*/
    public WorkingShift create(LocalDate date, String fullName, String time)
    {
        if(!fullName.isEmpty() && !time.isEmpty()) {
            WorkingShift shift = new WorkingShift();
            shift.setFullName(fullName);
            shift.setShift(date);
            shift.setTime(time);
            dao.createShift(shift);
            return shift;
        }
        else {
            createErrorAlertDialog("Ошибка сохранения!");
            throw new NullPointerException("Пустое поле");
        }
    }

    /*Получаем все смены*/
    public List<WorkingShift> getAllShifts() {
        return dao.findAll();
    }

    /*Удаляем смену по дате*/
    public void delete(LocalDate date) {

        dao.deleteShift(date);
    }

    /*Удаляем смену по id*/
    public void deleteById(int id) {
        dao.deleteShiftById(id);
    }
}
