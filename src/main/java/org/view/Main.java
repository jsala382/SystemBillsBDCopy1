package org.view;


import org.entity.Product;
import org.interfaces.clients.BillsImpl;
import org.interfaces.clients.ClientsImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ClientsImpl clienteImpl= new ClientsImpl();
        OptionMenu optionMenu=new OptionMenu();
        List<Product> productList = new ArrayList<>();
         BillsImpl bills=  new BillsImpl();
        String code="";


       /* ProductImpl productBillboard= new ProductImpl();
        productBillboard.printListProduct(productList);
        bills.inputBuy( getProduct,  code);
        bills.addElininateProducto();*/
        //clienteImpl.inputCustomerData();
        optionMenu.viewMenuOption();



    }
}