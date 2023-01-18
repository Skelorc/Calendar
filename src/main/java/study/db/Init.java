package study.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Init {

    private static final String connectionUrl = "jdbc:sqlite:db\\calendar.db";
    /*Создании бд, если она не создана.*/
    public static void createDb() {
        //Создаём подключение к базе данных через конструкцию try catch, для автоматического закрытия после выполнения работы.
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             //получаем состояние подключения
             Statement statement = connection.createStatement()) {
            //выполняем создание базы данных Рабочих смен
            statement.execute("""
                    create table if not exists working_shifts (
                        id       integer not null primary key AUTOINCREMENT,
                        full_name varchar(255),
                        shift    date,
                        time    varchar(255)
                    )""");
            System.out.println("Таблица успешно создана!");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения!");
            e.printStackTrace();
        }
    }
}
