package com.supermarket.servlet;

import com.supermarket.dao.CartDAO;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart/clear")
public class CartClearServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        if (cartDAO.clear()) {
            resp.getWriter().write(JsonUtil.success("{}"));
        } else {
            resp.getWriter().write(JsonUtil.error("清空失败"));
        }
    }
}