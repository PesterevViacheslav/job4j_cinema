<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored ="false" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <title>Cinema</title>

    <script>
        function buy(user) {
            alert("BUY");
            console.log("BUY log");
            if (user == null) {
                window.location.href = 'login.jsp';
            } else {
                //alert("ELSE  " + JSON.stringify($("input:radio[name=place]:selected").val()));
                let t = JSON.parse($("input:radio[name=place]:selected").val());
                alert("t=" + t);
                window.location.href = 'payment.do?row=' + t['row'] + '&num=' + t['num'] + '&price=' + t['price'];
            }
            return true;
        }
    </script>
    <script>
        function getSeats() {
            console.log("getSeats() Entered");
            let res = null;
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/cinema/seats.do',
                encoding: 'windows-1251',
                dataType: 'json'
            }).done(function(data) {
                console.log("getSeats() JSON.stringify(data)= " + JSON.stringify(data));
                //alert("getSeats " + JSON.stringify(data));
                // let res;
                // if (data) {
                //     res = "<thead>" +
                //         "<tr>" +
                //         "<th style=\"width: 120px;\">Ряд / Место</th>" +
                //         "<th>1</th>" +
                //         "<th>2</th>" +
                //         "<th>3</th>" +
                //         "</tr>" +
                //         "</thead>" +
                //         "<tbody>";
                //     let prevRow = -1;
                //     for (let i = 0; i < data.length; i++) {
                //         if (data[i].row != prevRow) {
                //             res += "<tr>" +
                //                 "<th>" + data[i].row + "</th>";
                //         }
                //         if (data[i].userId != 0) {
                //             res += "<td bgcolor=\"#5f9ea0\"><input type=\"radio\" class=\"place\" name=\"place\" value=\"" + data[i].toString() + "\" disabled=\"true\" />";
                //         } else {
                //             res += "<td><input type=\"radio\" class=\"place\" name=\"place\" value=\"" + data[i].toString() + "\"/>";
                //         }
                //         res += "Ряд " + data[i].row + ", Место " + data[i].num + ", Цена " + data[i].price + "</td>";
                //         prevRow = data[i].row;
                //         if (data[i].row != prevRow) {
                //             res += "</tr>";
                //         }
                //     }
                //      res += "</tbody>";
                // }
                // alert(res);
                //$("#bookTbl").html(res);
                res = data;
            }).fail(function(err) {
                alert(err);
            });
            console.log("getSeats() Finished res=" + res);
            return res;
        }
    </script>
</head>


<body>

<div class="container">
    <script>
        $(document).ready(function(){
            console.log("$(document).ready(function() Entered");
            let data  = getSeats();
            console.log("$(document).ready(function() data=" + data);
            let res;
            res = "<thead>" +
                "<tr>" +
                "<th style=\"width: 120px;\">Ряд / Место</th>" +
                "<th>1</th>" +
                "<th>2</th>" +
                "<th>3</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>";
            let prevRow = -1;
            if (data != null) {
                for (let i = 0; i < data.length; i++) {
                    if (data[i].row != prevRow) {
                        res += "<tr>" +
                            "<th>" + data[i].row + "</th>";
                    }
                    if (data[i].userId != 0) {
                        res += "<td bgcolor=\"#5f9ea0\"><input type=\"radio\" class=\"place\" name=\"place\" value=\"" + data[i].toString() + "\" disabled=\"true\" />";
                    } else {
                        res += "<td><input type=\"radio\" class=\"place\" name=\"place\" value=\"" + data[i].toString() + "\"/>";
                    }
                    res += "Ряд " + data[i].row + ", Место " + data[i].num + ", Цена " + data[i].price + "</td>";
                    prevRow = data[i].row;
                    if (data[i].row != prevRow) {
                        res += "</tr>";
                    }
                }
            }
            res += "</tbody>";
            $("#bookTbl").html(res);
            // setInterval('getSeats()',5000);
        });
    </script>

    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/user.jsp">Регистрация</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">| Войти</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </ul>
    </div>
    <div class="row pt-3">
        <h4>
            Бронирование мест на сеанс
        </h4>
        <table id="bookTbl" class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 120px;">Ряд / Место</th>
                <th>1</th>
                <th>2</th>
                <th>3</th>
            </tr>
            </thead>
            <tbody action="<%=request.getContextPath()%>/seats.do" method="post">
            <c:set var="prew_row" scope="session" value="-1" />

            <c:forEach items="${seats}" var="seat">
                <c:choose>
                    <c:when test="${seat.row!=prew_row}">
                        <tr>
                        <th><c:out value="${seat.row}"/></th>
                    </c:when>
                </c:choose>

                <c:choose>
                    <c:when test="${seat.userId != 0}">
                        <td bgcolor="#5f9ea0"><input type="radio" class="place" name="place" value="<c:out value="${seat}"/>" disabled="true" />
                    </c:when>
                    <c:when test="${seat.userId == 0}">
                        <td><input type="radio" class="place" name="place" value="<c:out value="${seat}"/>"/>
                    </c:when>
                </c:choose>

                    Ряд <c:out value="${seat.row}"/>, Место <c:out value="${seat.num}"/>, Цена <c:out value="${seat.price}"/></td>
                <c:set var="prew_row" scope="session" value="${seat.row}" />

                <c:choose>
                <c:when test="${seat.row!=prew_row}">
                </tr>
                </c:when>
                </c:choose>

            </c:forEach>
            </tbody>
        </table>
        <div class="row float-right">
            <button type="button" class="btn btn-success"
                    onclick="return buy(${user.id});">
                Оплатить
            </button>
        </div>
    </div>
</div>
</body>
</html>