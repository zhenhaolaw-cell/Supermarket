package com.supermarket.entity;

public class Goods {
    private int id;
    private String name;
    private double price;
    private double originalPrice;
    private String img;
    private String cate;
    private String subCate;
    private String brand;
    private int sales;
    private int stock;
    private String desc;

    public Goods() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

    public String getCate() { return cate; }
    public void setCate(String cate) { this.cate = cate; }

    public String getSubCate() { return subCate; }
    public void setSubCate(String subCate) { this.subCate = subCate; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getSales() { return sales; }
    public void setSales(int sales) { this.sales = sales; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
}