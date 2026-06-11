package com.supermarket.servlet;

import com.supermarket.dao.OrderDAO;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/updateStatus")
public class OrderUpdateStatusServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        req.setCharacterEncoding("utf-8");

        String orderNo = req.getParameter("orderNo");
        String status = req.getParameter("status");
        if (orderNo == null || status == null) {
            resp.getWriter().write(JsonUtil.error("缺少参数"));
            return;
        }
        if (orderDAO.updateStatus(orderNo, status)) {
            resp.getWriter().write(JsonUtil.success("{}"));
        } else {
            resp.getWriter().write(JsonUtil.error("更新失败"));
        }
    }
}