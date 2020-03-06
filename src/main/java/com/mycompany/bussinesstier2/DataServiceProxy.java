/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bussinesstier2;

import classes.DataService;
import classes.DataService_Service;

/**
 *
 * @author Admins
 */
public class DataServiceProxy {
    //getting a single data service port
    private static DataService_Service instance = new DataService_Service();

    private DataServiceProxy() {}

    public static DataService getProxy() {
        DataService proxy = instance.getDataServicePort();
        return proxy;
    }

}
