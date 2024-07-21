package org.interfaces.clients;

import org.conexion.ConnexionBD;
import org.entity.Product;
import org.excepetion.ExceptionCompras;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductImpl implements org.interfaces.Product {

    @Override
    public void insertProduct(Product pr) {
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese el codigo del producto;");
        String code = input.next();
        Product productFounde = getProductoByCode(code);
        if (productFounde.getCode() == null) {
            System.out.println("Ingrese el nombre del producto");
            String name = input.next();
            System.out.println("Ingrese la cantidad:");
            int cant = input.nextInt();
            System.out.println("Ingrese el precio");
            double price = input.nextDouble();
            pr.setCode(code);
            pr.setNameProduct(name);
            pr.setAmount(cant);
            pr.setPrice(price);

        }

        String sql = "INSERT INTO `db_factura`.`producto` ( codigo_producto,nombre,cantidad,precio) VALUES (?,?,?,?)";

        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pr.getCode());
            ps.setString(2, pr.getNameProduct());
            ps.setInt(3, pr.getAmount());
            ps.setDouble(4, pr.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL INGRESAR EL DETALLE DE LA FACUTRA");
        }
        System.out.println("Producto ingresado de forrma correcta");

    }

    @Override
    public Product getProductoByCode(String code) {
        String sql = "SELECT * FROM db_factura.producto WHERE codigo_producto=? ";
        Product productList = new Product();
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.setId(resultSet.getInt("id"));
                productList.setCode(resultSet.getString("codigo_producto"));
                productList.setNameProduct(resultSet.getString("nombre"));
                productList.setAmount(resultSet.getInt("cantidad"));
                productList.setPrice(resultSet.getDouble("precio"));
            }
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL OBTENER UN PRODCUTO");

        }
        return productList;
    }

    @Override
    public void elimiinateProduct(String code) {
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from db_factura.producto where codigo_producto=?");
            preparedStatement.setString(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL BORRAR UN PRODUCTO");
        }
        getProduct();

    }

    @Override
    public void updatesProduct(String code, double price) {
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update  db_factura.producto set  precio=? where codigo_producto=?");
            preparedStatement.setDouble(1, price);
            preparedStatement.setString(2, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL BORRAR UN CLIENTE");
        }

    }

    public void getProduct() {
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from db_factura.producto ");

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                System.out.print(result.getInt("id"));
                System.out.print(" |");
                System.out.print(result.getString("codigo_producto"));
                System.out.print(" |");
                System.out.print(result.getString("nombre"));
                System.out.print(" |");
                System.out.print(result.getString("cantidad"));
                System.out.print(" |");
                System.out.print(result.getString("precio"));

            }
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL OBTENER UN PRODUCTO " + e);
        }

    }


}

