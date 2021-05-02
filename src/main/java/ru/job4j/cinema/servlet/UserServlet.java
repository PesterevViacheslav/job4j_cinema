package ru.job4j.cinema.servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.PsqlStore;
/**
 * Class UserServlet - Сервлет создания пользователя.
 * Решение задач уровня Middle. Части 012. Servlet JSP.
 * Сервис - Кинотеатр[#283003]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 02.05.2021
 * @version 1
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        String usrName = req.getParameter("name");
        String usrPhone = req.getParameter("phone");
        String usrPassword = req.getParameter("password");
        System.out.println("usrName=" + usrName + " usrPhone=" + usrPhone + " usrPassword" + usrPassword);
        if (usrName != null && usrPhone != null && usrPassword != null) {
            User user = PsqlStore.instOf().findUserByPhonePassword(usrPhone, usrPassword);
            HttpSession sc = req.getSession();
            System.out.println("user=" + user);
            if (user != null) {
                sc.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/seats.do");
            } else {
                sc.setAttribute("user",
                        PsqlStore.instOf().create(new User(0, usrName, usrPhone, usrPassword)));
                resp.sendRedirect(req.getContextPath() + "/seats.do");
            }
        } else {
            req.setAttribute("error", "Не указаны имя, телефон или пароль");
            req.getRequestDispatcher("login.jsp?error=").forward(req, resp);
        }
    }
}