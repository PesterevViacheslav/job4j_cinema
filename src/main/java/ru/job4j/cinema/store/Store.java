package ru.job4j.cinema.store;
import ru.job4j.cinema.model.*;
import java.util.Collection;
/**
 * Interface Store - Хранилище.
 * Решение задач уровня Middle. Части 012. Servlet JSP.
 * Сервис - Кинотеатр[#283003]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 02.05.2021
 * @version 1
 */
public interface Store {
    Collection<Seat> findAllSeats();
    boolean reserve(int user, int row, int num);
    Seat findSeatById(int id);
    User create(User user);
    User findUserByPhonePassword(String phone, String password);
}