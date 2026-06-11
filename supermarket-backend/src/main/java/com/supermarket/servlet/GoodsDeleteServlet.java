package com.supermarket.servlet;

import com.supermarket.dao.GoodsDAO;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/goods/delete")
public class GoodsDeleteServlet extends HttpServlet {
    private GoodsDAO goodsDAO = new GoodsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.getWriter().write(JsonUtil.error("缺少参数id"));
            return;
        }
        if (goodsDAO.delete(Integer.parseInt(idStr))) {
            resp.getWriter().write(JsonUtil.success("{}"));
        } else {
            resp.getWriter().write(JsonUtil.error("删除失败"));
        }
    }
}