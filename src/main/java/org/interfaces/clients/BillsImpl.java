package org.interfaces.clients;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.conexion.ConnexionBD;
import org.entity.Buy;
import org.entity.Clients;
import org.entity.Product;
import org.excepetion.ExceptionCompras;
import org.interfaces.Bills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.conexion.ConnexionBD.getConnection;

public class BillsImpl extends ClientsImpl implements Bills {
    Scanner input = new Scanner(System.in);
    ClientsImpl clientsImpl = new ClientsImpl();
    ProductImpl product = new ProductImpl();
    String identification;

    ////INGRESAR CLIENTES
    public void proccesBuy(String identification) {
        Clients cli = clientsImpl.inputCustomerData(identification);
        System.out.println(cli.getId());
        inputBuys(cli.getId());
    }


    //////REGISTRAR LAS COMPRAS
    public void inputBuys( int idClient) {
        boolean state = true;
        List<Buy> listCar = new ArrayList<>();
        while (state) {
            System.out.println("Ingrese el coidgo del producto");
            String code = input.next();
            Product productObj = product.getProductoByCode(code);
            if (productObj.getCode() == null) {
                System.out.println("Codigo no encotrado");
            } else {

                System.out.println("Ingrese la cantidad");
                int amount = input.nextInt();
                Buy buy = new Buy();
                buy.setIdProduct(productObj.getId());
                buy.setTotal(amount * productObj.getPrice());
                listCar.add(buy);
                System.out.println("Si desea ingresar mas productos digit SI, caso contrario digito NO");
                String option = input.next();
                if (option.equalsIgnoreCase("NO")) {
                    break;
                }

            }
        }
        System.out.println("Desea realizar esta compra");
        String option = input.next();
        if (option.equalsIgnoreCase("SI")) {
            saveHeaderBilssd(listCar, idClient);
            printHeader();
            int valueMax = maxIndexHeader();
            for (int i = 0; i < listCar.size(); i++) {
                double priceTotal = listCar.get(i).getTotal();
                int idProd = listCar.get(i).getIdProduct();
                saveDetailBills(priceTotal, idProd, valueMax);
            }

        }
    }


    //////GUARDAMMOS EN LA BASE DE DATOS
    void saveHeaderBilssd(List<Buy> car, int idClient) {
        try {
            Date todayDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fechaActual = sdf.format(todayDate);
            Connection connection = getConnection();
            String sql = "insert into db_factura.cabecera_factura (FECHA, TOTAL_FACTURA,ID_CLIENTE) VALUES (?,?,?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setString(1, fechaActual);
            preparedStatement1.setDouble(2, sumTotalBuys(car));
            preparedStatement1.setInt(3, idClient);
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL REGISTRAR LA COMPRA" +e) ;
        }
    }

    public void printHeader() {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from db_factura.cabecera_factura ");

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                System.out.print(result.getString("FECHA"));
                System.out.print(" |");
                System.out.print(result.getString("TOTAL_FACTURA"));
                System.out.print(" |");
                System.out.println(result.getString("ID_ClIENTE"));

            }
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL OBTENER UN CLIENTE " + e);
        }

    }

    /**
     * metodo para guardar facturas etalle
     *
     * @param car
     */
    void saveDetailBills(double priceTotal, int idProduct, int idCabecera) {

        try {;
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into db_factura.detalle_factura (PRECIO_TOTAL, ID_PRODUCTO ,id_cabecera) VALUES(?,?,?) " );
            preparedStatement.setDouble(1, priceTotal);
            preparedStatement.setInt(2, idProduct);
            preparedStatement.setInt(3, idCabecera);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL REGISTRAR LOS DETALLES" +e);
        }
    }

    double sumTotalBuys(List<Buy> listCar) {
        double total = 0.0;
        for (int i = 0; i < listCar.size(); i++) {
            total = total + listCar.get(i).getTotal();
        }
        return total;
    }

    int maxIndexHeader() {
        int indice = 0;
        try {
            Connection connection = getConnection();
            String sql = "SELECT max(ID) as MAX FROM db_factura.cabecera_factura;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                indice = result.getInt("MAX");
            }

        } catch (SQLException e) {
            throw new ExceptionCompras("ERRO AL OBTENER EL INDCE");
        }
        return indice;
    }



}
