package org.interfaces;


public interface Product {
    void insertProduct(org.entity.Product pr);
    org.entity.Product getProductoByCode(String code);
    void elimiinateProduct(String code);
    void updatesProduct(String code,double price);

}
