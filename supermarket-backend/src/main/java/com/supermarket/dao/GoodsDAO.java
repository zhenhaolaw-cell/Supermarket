package com.supermarket.dao;

import com.supermarket.entity.Goods;
import com.supermarket.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO {

    /** 获取全部商品 */
    public List<Goods> getAll() {
        List<Goods> list = new ArrayList<>();
        String sql = "SELECT * FROM goods ORDER BY id";
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

    /** 按ID查询 */
    public Goods getById(int id) {
        String sql = "SELECT * FROM goods WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    /** 搜索商品（按名称或品牌） */
    public List<Goods> search(String keyword) {
        List<Goods> list = new ArrayList<>();
        String sql = "SELECT * FROM goods WHERE name LIKE ? OR brand LIKE ? ORDER BY id";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
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

    /** 新增商品 */
    public boolean add(Goods g) {
        String sql = "INSERT INTO goods (name, price, original_price, img, cate, brand, stock, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, g.getName());
            ps.setDouble(2, g.getPrice());
            ps.setDouble(3, g.getOriginalPrice());
            ps.setString(4, g.getImg());
            ps.setString(5, g.getCate());
            ps.setString(6, g.getBrand());
            ps.setInt(7, g.getStock());
            ps.setString(8, g.getDesc());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps);
        }
    }

    /** 删除商品 */
    public boolean delete(int id) {
        String sql = "DELETE FROM goods WHERE id = ?";
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

    /** 更新商品 */
    public boolean update(Goods g) {
        String sql = "UPDATE goods SET name=?, price=?, original_price=?, img=?, cate=?, brand=?, stock=?, description=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, g.getName());
            ps.setDouble(2, g.getPrice());
            ps.setDouble(3, g.getOriginalPrice());
            ps.setString(4, g.getImg());
            ps.setString(5, g.getCate());
            ps.setString(6, g.getBrand());
            ps.setInt(7, g.getStock());
            ps.setString(8, g.getDesc());
            ps.setInt(9, g.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps);
        }
    }

    /** 更新库存和销量 */
    public boolean updateStockAndSales(int id, int stockChange, int salesChange) {
        String sql = "UPDATE goods SET stock = stock + ?, sales = sales + ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, stockChange);
            ps.setInt(2, salesChange);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps);
        }
    }

    private Goods map(ResultSet rs) throws SQLException {
        Goods g = new Goods();
        g.setId(rs.getInt("id"));
        g.setName(rs.getString("name"));
        g.setPrice(rs.getDouble("price"));
        g.setOriginalPrice(rs.getDouble("original_price"));
        g.setImg(rs.getString("img"));
        g.setCate(rs.getString("cate"));
        g.setSubCate(rs.getString("sub_cate"));
        g.setBrand(rs.getString("brand"));
        g.setSales(rs.getInt("sales"));
        g.setStock(rs.getInt("stock"));
        g.setDesc(rs.getString("description"));
        return g;
    }
}