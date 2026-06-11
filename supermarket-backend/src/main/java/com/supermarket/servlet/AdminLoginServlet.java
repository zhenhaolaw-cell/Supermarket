package com.supermarket.servlet;

import com.supermarket.dao.UserDAO;
import com.supermarket.entity.User;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        req.setCharacterEncoding("utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userDAO.login(username, password);
        if (user != null) {
            resp.getWriter().write(JsonUtil.success("{\"username\":\"" + user.getUsername() + "\",\"role\":\"" + user.getRole() + "\"}"));
        } else {
            resp.getWriter().write(JsonUtil.error("用户名或密码错误"));
        }
    }
}