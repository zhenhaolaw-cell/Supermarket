package com.supermarket.servlet;

import com.supermarket.dao.CartDAO;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart/add")
public class CartAddServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        int goodsId = Integer.parseInt(req.getParameter("goodsId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String spec = req.getParameter("spec");
        if (cartDAO.addOrUpdate(goodsId, quantity, spec)) {
            resp.getWriter().write(JsonUtil.success("{}"));
        } else {
            resp.getWriter().write(JsonUtil.error("加入购物车失败"));
        }
    }
}