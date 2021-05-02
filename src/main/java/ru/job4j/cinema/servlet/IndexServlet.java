package ru.job4j.cinema.servlet;
import ru.job4j.cinema.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Class IndexServlet - Сервлет обработки корневой страницы.
 * Решение задач уровня Middle. Части 012. Servlet JSP.
 * Сервис - Кинотеатр[#283003]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 02.05.2021
 * @version 1
 */
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.setAttribute("seats", PsqlStore.instOf().findAllSeats());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
