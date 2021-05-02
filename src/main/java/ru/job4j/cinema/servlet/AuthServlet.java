package ru.job4j.cinema.servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.PsqlStore;
import javax.servlet.http.HttpSession;
/**
 * Class AuthServlet - Сервлет аутентификации.
 * Решение задач уровня Middle. Части 012. Servlet JSP.
 * Сервис - Кинотеатр[#283003]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 02.05.2021
 * @version 1
 */
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        if (password != null && phone != null) {
            User user = PsqlStore.instOf().findUserByPhonePassword(phone, password);
            HttpSession sc = req.getSession();
            if (user == null) {
                req.setAttribute("error", "Пользователь не найден");
                req.getRequestDispatcher("login.jsp?error=").forward(req, resp);
            } else {
                sc.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/seats.do");
            }
        } else {
            req.setAttribute("error", "Не верный телефон или пароль");
            req.getRequestDispatcher("login.jsp?error=").forward(req, resp);
        }
    }
}