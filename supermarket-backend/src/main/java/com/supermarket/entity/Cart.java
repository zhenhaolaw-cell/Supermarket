package com.supermarket.entity;

public class Cart {
    private int id;
    private int goodsId;
    private String goodsName;
    private double price;
    private int quantity;
    private String spec;
    private String img;

    public Cart() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGoodsId() { return goodsId; }
    public void setGoodsId(int goodsId) { this.goodsId = goodsId; }

    public String getGoodsName() { return goodsName; }
    public void setGoodsName(String goodsName) { this.goodsName = goodsName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
}