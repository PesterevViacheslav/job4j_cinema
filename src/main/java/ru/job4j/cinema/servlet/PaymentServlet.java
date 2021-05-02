package ru.job4j.cinema.servlet;
import ru.job4j.cinema.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Class PaymentServlet - Сервлет обработки платежей.
 * Решение задач уровня Middle. Части 012. Servlet JSP.
 * Сервис - Кинотеатр[#283003]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 02.05.2021
 * @version 1
 */
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("payRow", req.getParameter("row"));
        req.setAttribute("payNum", req.getParameter("num"));
        req.setAttribute("payPrice", req.getParameter("price"));
        req.setAttribute("user", req.getParameter("user"));
        req.getRequestDispatcher("payment.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (PsqlStore.instOf().reserve(Integer.valueOf(req.getParameter("user")),
                                       Integer.valueOf(req.getParameter("row")),
                                       Integer.valueOf(req.getParameter("num")))) {
            resp.sendRedirect(req.getContextPath() + "/index.do");
        } else {
            req.getRequestDispatcher("err.jsp").forward(req, resp);
        }
    }
}