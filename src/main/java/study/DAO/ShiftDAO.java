package study.DAO;


import study.entity.WorkingShift;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO {

    /*Путь до бд*/
    private final String PATH_TO_DB = "jdbc:sqlite:db\\calendar.db";

    /*Получение всех смен*/
    public List<WorkingShift> findAll()
    {
        List<WorkingShift> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(PATH_TO_DB)) {
            String query = "SELECT * FROM working_shifts ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                WorkingShift workingShift = new WorkingShift();
                workingShift.setId(resultSet.getInt("id"));
                workingShift.setFullName(resultSet.getString("full_name"));
                workingShift.setShift(LocalDate.parse(resultSet.getString("shift")));
                workingShift.setTime(resultSet.getString("time"));
                list.add(workingShift);
            }
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println("Ошибка!");
            e.printStackTrace();
        }
        return list;
    }

    /*Создание смены*/
    public void createShift(WorkingShift workingShift)
    {
        try (Connection connection = DriverManager.getConnection(PATH_TO_DB)) {
            String query = "INSERT OR REPLACE INTO working_shifts (full_name,shift,time) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, workingShift.getFullName());
            preparedStatement.setString(2, workingShift.getShift().toString());
            preparedStatement.setString(3, workingShift.getTime());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*Удаление смены по дате*/
    public void deleteShift(LocalDate date)
    {
        try (Connection connection = DriverManager.getConnection(PATH_TO_DB)) {
            String query = "DELETE FROM working_shifts  WHERE shift = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*Удаление смены по id*/
    public void deleteShiftById(int id) {
        try (Connection connection = DriverManager.getConnection(PATH_TO_DB)) {
            String query = "DELETE FROM working_shifts  WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
