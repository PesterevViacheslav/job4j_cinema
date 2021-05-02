package ru.job4j.cinema.servlet;
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
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.setAttribute("seats", PsqlStore.instOf().findAllSeats());

        String res = "<thead>" +
                       "<tr>" +
                           "<th style=\"width: 120px;\">Ряд / Место</th>" +
                           "<th>1</th>" +
                           "<th>2</th>" +
                           "<th>3</th>" +
                       "</tr>" +
                       "</thead>" +
                       "<tbody>";
        int prevRow = -1;
        for (Seat seat: PsqlStore.instOf().findAllSeats()) {
            if (seat.getRow() != prevRow) {
                res += "<tr>" +
                        "<th>" + seat.getRow() + "</th>";
            }
            if (seat.getUserId() != 0) {
                res += "<td bgcolor=\"#5f9ea0\"><input type=\"radio\" class=\"place\" name=\"place\" value=\"" + seat.toString() + "\" disabled=\"true\" />";
            } else {
                res += "<td><input type=\"radio\" class=\"place\" name=\"place\" value=\"" + seat.toString() + "\"/>";
            }
            res += "Ряд " + seat.getRow() + ", Место " + seat.getNum() + ", Цена " + seat.getPrice() + "</td>";
            prevRow = seat.getRow();
            if (seat.getRow() != prevRow) {
                res += "</tr>";
            }
        }
        res += "</tbody>";

        resp.setContentType("text/html");
        resp.setCharacterEncoding("windows-1251");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(res);
        writer.flush();
    }
}