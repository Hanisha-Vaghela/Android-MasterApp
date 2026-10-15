package com.example.lecture_works;

public class ProductList {
    int productid,productimage,originalprice,disscountprice,subcatid;
    String productname,description,wishlist,productdiscount;


    public String getProductdiscount() {
        return productdiscount;
    }

    public void setProductdiscount(String productdiscount) {
        this.productdiscount = productdiscount;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductimage() {
        return productimage;
    }

    public void setProductimage(int productimage) {
        this.productimage = productimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getProductid() {
        return productid;
    }

    public int getSubcatid() {
        return subcatid;
    }

    public void setSubcatid(int subcatid) {
        this.subcatid = subcatid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(int originalprice) {
        this.originalprice = originalprice;
    }

    public int getDisscountprice() {
        return disscountprice;
    }

    public void setDisscountprice(int disscountprice) {
        this.disscountprice = disscountprice;
    }


    String name;

//
//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
