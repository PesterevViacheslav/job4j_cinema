package ru.job4j.cinema.model;
import java.util.Objects;
/**
 * Class Seat - Место.
 * Решение задач уровня Middle. Части 012. Servlet JSP.
 * Сервис - Кинотеатр[#283003]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 02.05.2021
 * @version 1
 */
public class Seat {
    private int id;
    private int hallId;
    private int row;
    private int num;
    private int price;
    private int userId;
    private String userName;
    /**
     * Method Seat. Конструктор
     * @param id
     * @param hallId Зал
     * @param row Ряд
     * @param num Место
     * @param price Цена
     * @param userId ID зрителя
     * @param userName Имя зрителя
     */
    public Seat(int id, int hallId, int row, int num, int price, int userId, String userName) {
        this.id = id;
        this.hallId = hallId;
        this.row = row;
        this.num = num;
        this.price = price;
        this.userId = userId;
        this.userName = userName;
    }
    /**
     * Method getId. Получение ID
     * @return ID
     */
    public int getId() {
        return id;
    }
    /**
     * Method setId. Установка ID
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getHallId() {
        return hallId;
    }

    public int getRow() {
        return row;
    }

    public int getNum() {
        return num;
    }

    public int getPrice() {
        return price;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return hallId == seat.hallId &&
                row == seat.row &&
                num == seat.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallId, row, num);
    }

    @Override
    public String toString() {
        return "{\"id\" : " + id +
                  ", \" hallId \" : " + hallId +
                  ", \"row\" : " + row +
                  ", \"num\" : " + num +
                  ", \"price\" : " + price +
                  ", \"userId\" : " + userId +
                  ", \"userName\" : " + "\"" + userName +"\"}";
    }
}