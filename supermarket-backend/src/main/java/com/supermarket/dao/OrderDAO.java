package com.supermarket.dao;

import com.supermarket.entity.Order;
import com.supermarket.entity.OrderItem;
import com.supermarket.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    /** 获取全部订单（含商品明细） */
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY create_time DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order o = map(rs);
                o.setItems(getItemsByOrderNo(conn, o.getOrderNo()));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    /** 按订单号查询 */
    public Order getByOrderNo(String orderNo) {
        String sql = "SELECT * FROM orders WHERE order_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, orderNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                Order o = map(rs);
                o.setItems(getItemsByOrderNo(conn, orderNo));
                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    /** 创建订单（同时插入订单明细、扣减库存、清空购物车） */
    public boolean checkout(List<OrderItem> cartItems, double total) {
        String orderNo = generateOrderNo();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // 开启事务

            // 1. 插入 orders
            String orderSql = "INSERT INTO orders (order_no, total, status, create_time) VALUES (?, ?, 'pending', ?)";
            ps = conn.prepareStatement(orderSql);
            ps.setString(1, orderNo);
            ps.setDouble(2, total);
            ps.setLong(3, System.currentTimeMillis());
            ps.executeUpdate();
            ps.close();

            // 2. 插入 order_items + 扣减库存
            String itemSql = "INSERT INTO order_items (order_no, goods_id, goods_name, price, quantity) VALUES (?, ?, ?, ?, ?)";
            String stockSql = "UPDATE goods SET stock = stock - ?, sales = sales + ? WHERE id = ? AND stock >= ?";

            for (OrderItem item : cartItems) {
                ps = conn.prepareStatement(itemSql);
                ps.setString(1, orderNo);
                ps.setInt(2, item.getGoodsId());
                ps.setString(3, item.getGoodsName());
                ps.setDouble(4, item.getPrice());
                ps.setInt(5, item.getQuantity());
                ps.executeUpdate();
                ps.close();

                ps = conn.prepareStatement(stockSql);
                ps.setInt(1, item.getQuantity());
                ps.setInt(2, item.getQuantity());
                ps.setInt(3, item.getGoodsId());
                ps.setInt(4, item.getQuantity());
                int affected = ps.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    return false; // 库存不足
                }
                ps.close();
            }

            // 3. 清空购物车
            String clearCart = "DELETE FROM cart";
            ps = conn.prepareStatement(clearCart);
            ps.executeUpdate();
            ps.close();

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
            DBUtil.close(conn, ps);
        }
    }

    /** 更新订单状态（发货/完成/取消） */
    public boolean updateStatus(String orderNo, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, orderNo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps);
        }
    }

    /** 根据订单号查明细 */
    private List<OrderItem> getItemsByOrderNo(Connection conn, String orderNo) throws SQLException {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_no = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, orderNo);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            OrderItem item = new OrderItem();
            item.setId(rs.getInt("id"));
            item.setOrderNo(rs.getString("order_no"));
            item.setGoodsId(rs.getInt("goods_id"));
            item.setGoodsName(rs.getString("goods_name"));
            item.setPrice(rs.getDouble("price"));
            item.setQuantity(rs.getInt("quantity"));
            list.add(item);
        }
        rs.close();
        ps.close();
        return list;
    }

    /** 生成订单号 */
    private String generateOrderNo() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String ds = sdf.format(new java.util.Date());
        int rand = (int) (Math.random() * 10000);
        return "ORD" + ds + String.format("%04d", rand);
    }

    private Order map(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderNo(rs.getString("order_no"));
        o.setTotal(rs.getDouble("total"));
        o.setStatus(rs.getString("status"));
        o.setCreateTime(rs.getLong("create_time"));
        return o;
    }
}