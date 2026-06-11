package com.supermarket.dao;

import com.supermarket.entity.Cart;
import com.supermarket.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    /** 鑾峰彇璐墿杞﹀叏閮ㄥ晢鍝?*/
    public List<Cart> getAll() {
        List<Cart> list = new ArrayList<>();
        String sql = "SELECT * FROM cart ORDER BY added_at";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    /** 鍔犲叆璐墿杞︼紙濡傛灉宸插瓨鍦ㄥ悓鍟嗗搧鍚岃鏍煎垯澧炲姞鏁伴噺锛?*/
    public boolean addOrUpdate(int goodsId, int quantity, String spec) {
        String checkSql = "SELECT * FROM cart WHERE goods_id = ? AND spec = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(checkSql);
            ps.setInt(1, goodsId);
            ps.setString(2, spec != null ? spec : "");
            rs = ps.executeQuery();
            if (rs.next()) {
                int newQty = rs.getInt("quantity") + quantity;
                int cartId = rs.getInt("id");
                ps.close();
                String updateSql = "UPDATE cart SET quantity = ? WHERE id = ?";
                ps = conn.prepareStatement(updateSql);
                ps.setInt(1, newQty);
                ps.setInt(2, cartId);
                ps.executeUpdate();
            } else {
                ps.close();
                rs.close();
                // 从goods表获取商品信息
                String goodsSql = "SELECT name, price, img FROM goods WHERE id = ?";
                ps = conn.prepareStatement(goodsSql);
                ps.setInt(1, goodsId);
                rs = ps.executeQuery();
                if (!rs.next()) return false;
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String img = rs.getString("img");
                ps.close();
                rs.close();

                String insertSql = "INSERT INTO cart (goods_id, goods_name, price, quantity, spec, img) VALUES (?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(insertSql);
                ps.setInt(1, goodsId);
                ps.setString(2, name);
                ps.setDouble(3, price);
                ps.setInt(4, quantity);
                ps.setString(5, spec != null ? spec : "");
                ps.setString(6, img);
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    /** 鏇存柊璐墿杞﹀晢鍝佹暟閲?*/
    public boolean updateQuantity(int id, int quantity) {
        if (quantity <= 0) return delete(id);
        String sql = "UPDATE cart SET quantity = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps);
        }
    }

    /** 鍒犻櫎璐墿杞︿腑鐨勪竴椤?*/
    public boolean delete(int id) {
        String sql = "DELETE FROM cart WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps);
        }
    }

    /** 娓呯┖璐墿杞?*/
    public boolean clear() {
        String sql = "DELETE FROM cart";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps);
        }
    }

    private Cart map(ResultSet rs) throws SQLException {
        Cart c = new Cart();
        c.setId(rs.getInt("id"));
        c.setGoodsId(rs.getInt("goods_id"));
        c.setGoodsName(rs.getString("goods_name"));
        c.setPrice(rs.getDouble("price"));
        c.setQuantity(rs.getInt("quantity"));
        c.setSpec(rs.getString("spec"));
        c.setImg(rs.getString("img"));
        return c;
    }
}