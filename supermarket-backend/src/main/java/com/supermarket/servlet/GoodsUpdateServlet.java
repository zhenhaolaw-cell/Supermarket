package com.supermarket.servlet;

import com.supermarket.dao.GoodsDAO;
import com.supermarket.entity.Goods;
import com.supermarket.util.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/goods/update")
public class GoodsUpdateServlet extends HttpServlet {
    private GoodsDAO goodsDAO = new GoodsDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        req.setCharacterEncoding("utf-8");

        Goods g = new Goods();
        g.setId(parseInt(req.getParameter("id")));
        g.setName(req.getParameter("name"));
        g.setPrice(parseDouble(req.getParameter("price")));
        g.setOriginalPrice(parseDouble(req.getParameter("originalPrice")));
        g.setImg(req.getParameter("img"));
        g.setCate(req.getParameter("cate"));
        g.setBrand(req.getParameter("brand"));
        g.setStock(parseInt(req.getParameter("stock")));
        g.setDesc(req.getParameter("desc"));

        if (goodsDAO.update(g)) {
            resp.getWriter().write(JsonUtil.success("{}"));
        } else {
            resp.getWriter().write(JsonUtil.error("更新失败"));
        }
    }

    private double parseDouble(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0; }
    }
    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}