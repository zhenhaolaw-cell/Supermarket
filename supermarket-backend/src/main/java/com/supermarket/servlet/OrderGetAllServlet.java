package com.supermarket.servlet;

import com.supermarket.dao.OrderDAO;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/getAll")
public class OrderGetAllServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        String json = JsonUtil.success(JsonUtil.orderListToJson(orderDAO.getAll()));
        resp.getWriter().write(json);
    }
}