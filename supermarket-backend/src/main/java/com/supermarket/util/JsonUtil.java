package com.supermarket.util;

import com.supermarket.entity.*;
import java.util.List;

public class JsonUtil {

    public static String success(String dataJson) {
        return "{\"code\":200,\"msg\":\"success\",\"data\":" + dataJson + "}";
    }

    public static String error(String msg) {
        return "{\"code\":500,\"msg\":\"" + escape(msg) + "\"}";
    }

    // ---------- Goods ----------
    public static String goodsToJson(Goods g) {
        if (g == null) return "null";
        return "{" +
            "\"id\":" + g.getId() + "," +
            "\"name\":\"" + escape(g.getName()) + "\"," +
            "\"price\":" + g.getPrice() + "," +
            "\"originalPrice\":" + g.getOriginalPrice() + "," +
            "\"img\":\"" + escape(g.getImg()) + "\"," +
            "\"cate\":\"" + escape(g.getCate()) + "\"," +
            "\"brand\":\"" + escape(g.getBrand()) + "\"," +
            "\"sales\":" + g.getSales() + "," +
            "\"stock\":" + g.getStock() + "," +
            "\"desc\":\"" + escape(g.getDesc()) + "\"" +
        "}";
    }

    public static String goodsListToJson(List<Goods> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(goodsToJson(list.get(i)));
        }
        return sb.append("]").toString();
    }

    // ---------- Cart ----------
    public static String cartToJson(Cart c) {
        if (c == null) return "null";
        return "{" +
            "\"id\":" + c.getId() + "," +
            "\"goodsId\":" + c.getGoodsId() + "," +
            "\"name\":\"" + escape(c.getGoodsName()) + "\"," +
            "\"price\":" + c.getPrice() + "," +
            "\"quantity\":" + c.getQuantity() + "," +
            "\"spec\":\"" + escape(c.getSpec()) + "\"," +
            "\"img\":\"" + escape(c.getImg()) + "\"" +
        "}";
    }

    public static String cartListToJson(List<Cart> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(cartToJson(list.get(i)));
        }
        return sb.append("]").toString();
    }

    // ---------- Order ----------
    public static String orderToJson(Order o) {
        if (o == null) return "null";
        StringBuilder sb = new StringBuilder();
        sb.append("{\"orderNo\":\"").append(escape(o.getOrderNo())).append("\",");
        sb.append("\"total\":").append(o.getTotal()).append(",");
        sb.append("\"status\":\"").append(escape(o.getStatus())).append("\",");
        sb.append("\"createTime\":").append(o.getCreateTime()).append(",");
        sb.append("\"items\":[");
        List<OrderItem> items = o.getItems();
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append("{\"id\":").append(items.get(i).getId());
                sb.append(",\"goodsId\":").append(items.get(i).getGoodsId());
                sb.append(",\"name\":\"").append(escape(items.get(i).getGoodsName())).append("\"");
                sb.append(",\"price\":").append(items.get(i).getPrice());
                sb.append(",\"quantity\":").append(items.get(i).getQuantity()).append("}");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    public static String orderListToJson(List<Order> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(orderToJson(list.get(i)));
        }
        return sb.append("]").toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}