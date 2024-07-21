package org.interfaces;


import org.entity.Clients;

import java.util.Date;

public interface CustomerData {

    Clients getClientsByIdentification(String identification);

    void saveClients(Clients clients);

    void getClient();
    void eliminateClients(String identification);
    void updateClientsByIdentification(String  identification,String email,String phone);
    void insertClientinHeadBills(String code, int total, Date fecha);


}
