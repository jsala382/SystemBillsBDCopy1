package org.interfaces.clients;


import org.conexion.ConnexionBD;
import org.entity.Clients;
import org.excepetion.ExceptionCompras;
import org.interfaces.CustomerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class ClientsImpl implements CustomerData {
    Scanner inputDataClient = new Scanner(System.in);


    public Clients inputCustomerData(String identificationInput) {
        Clients clients = new Clients();

        Clients clientFounds = getClientsByIdentification(identificationInput);
        if (clientFounds.getIdentification() != null) {
            System.out.print(clientFounds.getIdentification());
            System.out.print("|");
            System.out.print(clientFounds.getName());
            System.out.print("|");
            System.out.print(clientFounds.getAddress());
            System.out.print("|");
            System.out.print(clientFounds.getPhoneNumber());
            System.out.print("|");
            System.out.println(clientFounds.getEmail());
        } else {
            System.out.println("Cliente no encotrado");
            clients.setTypeIdentification("CED");
            clients.setIdentification(identificationInput);
            System.err.print("Ingrese el nombre del cliente: ");
            String nameClients = inputDataClient.nextLine();
            System.out.print("Ingrese la direccion del cliente: ");
            String addressClients = inputDataClient.nextLine();
            System.out.print("Ingrese el numero de telfomo: ");
            String phoneNumberClient = inputDataClient.next();
            System.out.println("Ingrese el email:");
            String eMail = inputDataClient.next();

            clients.setName(nameClients);
            clients.setAddress(addressClients);
            clients.setPhoneNumber(phoneNumberClient);
            clients.setEmail(eMail);
            saveClients(clients);
        }

        return clientFounds;
    }


    @Override
    public Clients getClientsByIdentification(String identification) {
        Clients clients = new Clients();
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from db_factura.clientes where IDENTIFICACION= ?");
            preparedStatement.setString(1, identification);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                clients.setId(result.getInt("id"));
                clients.setTypeIdentification(result.getString("TIPO_IDENTIFICACION"));
                clients.setIdentification(result.getString("IDENTIFICACION"));
                clients.setName(result.getString("NOMBRES"));
                clients.setAddress(result.getString("DIRECCION"));
                clients.setPhoneNumber(result.getString("TELEFONO"));
                clients.setEmail(result.getString("EMAIl"));
            }
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL OBTENER UN CLIENTE " + e);
        }


        return clients;
    }

    @Override
    public void saveClients(Clients clients) {
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into db_factura.clientes (TIPO_IDENTIFICACION,IDENTIFICACION,NOMBRES,DIRECCION,TELEFONO,EMAIL) VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1, clients.getTypeIdentification());
            preparedStatement.setString(2, clients.getIdentification());
            preparedStatement.setString(3, clients.getName());
            preparedStatement.setString(4, clients.getAddress());
            preparedStatement.setString(5, clients.getPhoneNumber());
            preparedStatement.setString(6, clients.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL INSERTAR LOD DATOS DEL CLIENTE " + e);
        }
        System.out.println("Datos insertados correctamente");
    }

    @Override
    public void getClient() {
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from db_factura.clientes ");

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                System.out.print(result.getInt("id"));
                System.out.print(" |");
                System.out.print(result.getString("TIPO_IDENTIFICACION"));
                System.out.print(" |");
                System.out.print(result.getString("IDENTIFICACION"));
                System.out.print(" |");
                System.out.print(result.getString("NOMBRES"));
                System.out.print(" |");
                System.out.print(result.getString("DIRECCION"));
                System.out.print(" |");
                System.out.print(result.getString("TELEFONO"));
                System.out.print(" |");
                System.out.println(result.getString("EMAIl"));
            }
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL OBTENER UN CLIENTE " + e);
        }

    }

    public void printCustomeData(Clients clients) {
        System.out.println("----------------DETALLE FACTURA-------------------------------");
        System.out.println("Nombre del cliente:\t" + clients.getName() + " " +
                "Direccion: " + clients.getAddress() +
                "Numerto de telefono: " + clients.getPhoneNumber());
    }

    @Override
    public void eliminateClients(String identification) {
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from db_factura.clientes where IDENTIFICACION=?");
            preparedStatement.setString(1, identification);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL BORRAR UN CLIENTE");
        }
        getClient();

    }

    @Override
    public void updateClientsByIdentification(String identification, String email, String phone) {
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update  db_factura.clientes set  EMAIL=?, TELEFONO=? where IDENTIFICACION=?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, identification);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL BORRAR UN CLIENTE");
        }
    }

    @Override
    public void insertClientinHeadBills(String code, int total, Date fecha) {
        try {
            Connection connection = ConnexionBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `db_factura`.`cabecera_factura` (`FECHA`, `TOTAL_FACTURA`, `ID_CLIENTE`) VALUES ('20/01/1981', '1', '1'))");
            preparedStatement.setDate(1, (java.sql.Date) fecha);
            preparedStatement.setString(2, code);
            preparedStatement.setInt(3, total);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionCompras("ERROR AL REGISTRAR LA CABECERA" + e);
        }
    }

}
