package ru.job4j.cinema.servlet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.store.PsqlStore;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Class SeatServlet - Сервлет обработки бронирования.
 * Решение задач уровня Middle. Части 012. Servlet JSP.
 * Сервис - Кинотеатр[#283003]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 02.05.2021
 * @version 1
 */
public class SeatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws IOException {
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.setAttribute("seats", PsqlStore.instOf().findAllSeats());
        resp.sendRedirect(req.getContextPath() + "/index.do");
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("SeatSRV");
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.setAttribute("seats", PsqlStore.instOf().findAllSeats());

        Gson gson = new Gson();
        String json = gson.toJson(PsqlStore.instOf().findAllSeats());
        resp.setContentType("text/json");
        resp.setCharacterEncoding("windows-1251"/*"utf-8"*/);
        PrintWriter out = resp.getWriter();
        System.out.println("SEAT=" + json);
        out.println(json);
        out.flush();
    }
}