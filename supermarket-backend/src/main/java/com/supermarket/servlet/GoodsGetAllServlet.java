package com.supermarket.servlet;

import com.supermarket.dao.GoodsDAO;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/goods/getAll")
public class GoodsGetAllServlet extends HttpServlet {
    private GoodsDAO goodsDAO = new GoodsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        String json = JsonUtil.success(JsonUtil.goodsListToJson(goodsDAO.getAll()));
        resp.getWriter().write(json);
    }
}