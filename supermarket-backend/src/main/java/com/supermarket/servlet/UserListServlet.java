package com.supermarket.servlet;

import com.supermarket.dao.UserDAO;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/getAll")
public class UserListServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        // 手动构建 users JSON（不暴露密码）
        java.util.List<com.supermarket.entity.User> list = userDAO.getAll();
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            com.supermarket.entity.User u = list.get(i);
            sb.append("{\"id\":").append(u.getId());
            sb.append(",\"username\":\"").append(u.getUsername()).append("\"");
            sb.append(",\"role\":\"").append(u.getRole()).append("\"");
            sb.append(",\"nickname\":\"").append(u.getNickname()).append("\"");
            sb.append(",\"phone\":\"").append(u.getPhone()).append("\"}");
        }
        sb.append("]");
        resp.getWriter().write(JsonUtil.success(sb.toString()));
    }
}