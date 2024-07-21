package org.view;

import org.entity.Clients;
import org.entity.Product;
import org.interfaces.clients.BillsImpl;
import org.interfaces.clients.ClientsImpl;
import org.interfaces.clients.ProductImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class OptionMenu {
    public void viewMenuOption() throws SQLException {

        ProductImpl prodcutoBdImple = new ProductImpl();
        Scanner input = new Scanner(System.in);
        ClientsImpl clientsImpl = new ClientsImpl();
        BillsImpl billsImpl=new BillsImpl();
        String identification = "";
        Clients clients;
        Product pr = new Product();
        String codeProd;
        int inventary = 0;


        while (true) {
            System.out.println("FCTURAR (1) / PRODUCTOS (2) /  CLIENTES (3)");
            System.out.println("Ingrese una opcion:");
            int option1 = input.nextInt();
            switch (option1) {
                case 0:
                    break;
                case 1:
                    System.out.println("Ingrese el identicador: ");
                    String identificationInput = input.next();
                    billsImpl.proccesBuy(identificationInput);

                    break;
                case 2:
                    System.out.println("Para agregar un producto presion \'A\", para eliminar un producto presione \'E\" para actualizar presione \'U\' ");
                    char option2 = input.next().charAt(0);
                    switch (option2) {
                        case 'A':
                            prodcutoBdImple.insertProduct(pr);
                            break;
                        case 'E':
                            System.out.println("Ingrse el codigo que desea eliminar");
                            String code = input.next();
                            prodcutoBdImple.elimiinateProduct(code);
                            break;
                        case 'U':
                            System.out.println("Ingrse el codigo del producto  que desea actualizarr");
                            String code1 = input.next();
                            System.out.println("Ingrese el precio del producto que dese aactualizar");
                            double price = input.nextDouble();
                            prodcutoBdImple.updatesProduct(code1, price);
                            break;

                    }
                case 3:
                    System.out.println("Para agregar un cliente presione \'A\' , para eliminar presion \"E\"  y para actualizar presione \"U\"");
                    char option3 = input.next().charAt(0);
                    switch (option3) {
                        case 'A':
                            clients = clientsImpl.inputCustomerData(identification);
                            clientsImpl.saveClients(clients);
                            clientsImpl.getClient();
                            break;
                        case 'E':
                            System.out.println("Ingrese el numero de identificacion");
                            identification = input.next();
                            clientsImpl.eliminateClients(identification);
                            break;
                        case 'U':
                            System.out.println("Ingrese la identificacion");
                            identification = input.next();
                            System.out.println("Ingrese el email");
                            String email = input.next();
                            System.out.println("Ingrese el telefono");
                            String phone = input.next();
                            clientsImpl.updateClientsByIdentification(identification, email, phone);
                            clientsImpl.getClient();
                            break;
                        default:
                    }
                    clientsImpl.getClient();
                    break;
                default:
                    System.exit(1);
            }

        }
    }


}
