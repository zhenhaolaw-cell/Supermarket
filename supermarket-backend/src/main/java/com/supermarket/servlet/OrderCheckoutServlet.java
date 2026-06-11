package com.supermarket.servlet;

import com.supermarket.dao.CartDAO;
import com.supermarket.dao.OrderDAO;
import com.supermarket.entity.Cart;
import com.supermarket.entity.OrderItem;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/order/checkout")
public class OrderCheckoutServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");

        List<Cart> cartList = cartDAO.getAll();
        if (cartList.isEmpty()) {
            resp.getWriter().write(JsonUtil.error("购物车为空"));
            return;
        }

        List<OrderItem> items = new ArrayList<>();
        double total = 0;
        for (Cart c : cartList) {
            OrderItem item = new OrderItem();
            item.setGoodsId(c.getGoodsId());
            item.setGoodsName(c.getGoodsName());
            item.setPrice(c.getPrice());
            item.setQuantity(c.getQuantity());
            items.add(item);
            total += c.getPrice() * c.getQuantity();
        }

        if (orderDAO.checkout(items, total)) {
            resp.getWriter().write(JsonUtil.success("{\"total\":" + total + "}"));
        } else {
            resp.getWriter().write(JsonUtil.error("结算失败，可能存在库存不足"));
        }
    }
}