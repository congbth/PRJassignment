package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Date;
import dao.RequestDAO;
import model.Request;
import model.User;

@WebServlet("/createRequest")
public class CreateRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        Date fromDate = Date.valueOf(request.getParameter("from_date"));
        Date toDate = Date.valueOf(request.getParameter("to_date"));
        String reason = request.getParameter("reason");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Request req = new Request(title, fromDate, toDate, reason, "In Progress", user.getId());

        boolean success = RequestDAO.insertRequest(req);

        if (success) {
            response.sendRedirect("home.jsp");
        } else {
            request.setAttribute("errorMessage", "Lưu đơn thất bại.");
            request.getRequestDispatcher("requestForm.jsp").forward(request, response);
        }
    }
}
