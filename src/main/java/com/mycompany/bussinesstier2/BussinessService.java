/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bussinesstier2;

import classes.Category;
import classes.DataService;
import classes.Item;
import classes.Order;
import classes.Product;
import classes.Sale;
import classes.Stock;
import classes.User;
import classes.Vendor;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author IAMON
 */
@WebService(serviceName = "BussinessService")
public class BussinessService {

    DataService proxy = DataServiceProxy.getProxy();

    /**
     * This is a sample web service operation
     *
     * @param object
     * @return
     */
    //add
    @WebMethod(operationName = "addUser")
    public String addUser(@WebParam(name = "object") User object) {
        String pwHash = Encrypt.getMd5(object.getPassword());
        object.setPassword(pwHash);
        int result = proxy.addUser(object);
        if (result > 0) {
            try {
                SendEmail.welcome(object.getEmail());
            } catch (NoClassDefFoundError e) {
                System.out.println("============================Server No Class Def Found Error=========================");
            }
            return "Successful";
        } else if (result == -1) {
            return "EmailInUse";
        } else {
            return "InternalError";
        }
    }

    @WebMethod(operationName = "addVendor")
    public String addVendor(@WebParam(name = "object") Vendor object) {
        String pwHash = Encrypt.getMd5(object.getPassword());
        object.setPassword(pwHash);
        int result = proxy.addVendor(object);
        if (result > 0) {
            try {
                SendEmail.welcome(object.getEmail());
            } catch (NoClassDefFoundError e) {
                System.out.println("============================Server No Class Def Found Error=========================");
            }
            return "Successful";
        } else if (result == -1) {
            return "EmailInUse";
        } else {
            return "InternalError";
        }
    }

    @WebMethod(operationName = "addCategory")
    public String addCategory(@WebParam(name = "object") Category object) {
        int result = proxy.addCategory(object);
        if (result > 0) {
            return "Successful";
        } else {
            return "InternalError";
        }
    }

    @WebMethod(operationName = "addProduct")
    public String addProduct(@WebParam(name = "object") Product object) {
        int result = proxy.addProduct(object);
        if (result > 0) {
            return "Successful";
        } else if (result == -2) {
            return "IncorrectID";
        } else {
            return "InternalError";
        }
    }

    @WebMethod(operationName = "addStock")
    public String addStock(@WebParam(name = "object") Stock object) {
        int result = proxy.addStock(object);
        if (result > 0) {
            return "Successful";
        } else if (result == -2) {
            return "IncorrectID";
        } else {
            return "InternalError";
        }
    }

    @WebMethod(operationName = "addSale")
    public String addSale(@WebParam(name = "object") Sale object, @WebParam(name = "items") List<Item> items) {
        int result = proxy.addSale(object, items);
        if (result > 0) {
            return "Successful";
        } else if (result == -2) {
            return "IncorrectID";
        } else {
            return "InternalError";
        }
    }

    @WebMethod(operationName = "addOrder")
    public String addOrder(@WebParam(name = "object") Order object, @WebParam(name = "items") List<Item> items) {
        int result = proxy.addOrder(object, items);
        try {
            SendEmail.SendInvoice(object.getVendor().getEmail(), object, items);
        } catch (NoClassDefFoundError e) {
            System.out.println("============================Server No Class Def Found Error=========================");
        }
        if (result > 0) {
            return "Successful";
        } else if (result == -2) {
            return "IncorrectID";
        } else {
            return "InternalError";
        }
    }

    //update
    @WebMethod(operationName = "update")
    public String update(@WebParam(name = "object") Object object, @WebParam(name = "name") String name) {
        int result = proxy.update(object, name);
        if (result > 0) {
            return "Successful";
        } else if (result == -1) {
            return "EmailInUse";
        } else {
            return "InternalError";
        }
    }

    @WebMethod(operationName = "updatePassword")
    public String updatePassword(@WebParam(name = "id") int id, @WebParam(name = "password") String password, @WebParam(name = "Npassword") String Npassword) {
        String pwHash = Encrypt.getMd5(password);
        User u = (User) proxy.getOne(id, "user");
        if (u != null && u.getPassword().equals(pwHash)) {
            int result = proxy.updatePassword(id, Encrypt.getMd5(Npassword));
            if (result > 0) {
                return "Successful";
            } else {
                return "InternalError";
            }
        } else {
            return "IncorrectPassword";
        }
    }

    @WebMethod(operationName = "updateProductImage")
    public String updateProductImage(@WebParam(name = "id") int id, @WebParam(name = "image") String image) {
        int result = proxy.updateProductImage(id, image);
        if (result > 0) {
            return "Successful";
        } else if (result == -2) {
            return "IncorrectID";
        } else {
            return "InternalError";
        }
    }

    //delete
    @WebMethod(operationName = "delete")
    public String delete(@WebParam(name = "id") int id, @WebParam(name = "name") String name) {
        int result = proxy.delete(id, name);
        if (result > 0) {
            return "Successful";
        } else if (result == -3) {
            return "CannotInuse";
        } else {
            return "InternalError";
        }
    }

    //getOne
    @WebMethod(operationName = "getOne")
    public Object getOne(@WebParam(name = "id") int id, @WebParam(name = "name") String name) {
        return proxy.getOne(id, name);
    }

    //getAll
    @WebMethod(operationName = "getAllUsers")
    public List<User> getAllUsers() {
        return proxy.getAllUsers();
    }

    @WebMethod(operationName = "getAllVendors")
    public List<Vendor> getAllVendors() {
        return proxy.getAllVendors();
    }

    @WebMethod(operationName = "getAllCategory")
    public List<Category> getAllCategory() {
        return proxy.getAllCategory();
    }

    @WebMethod(operationName = "getAllProducts")
    public List<Product> getAllProducts() {
        return proxy.getAllProducts();
    }

    @WebMethod(operationName = "getAllStocks")
    public List<Stock> getAllStocks() {
        return proxy.getAllStocks();
    }

    @WebMethod(operationName = "getAllSales")
    public List<Sale> getAllSales() {
        return proxy.getAllSales();
    }

    @WebMethod(operationName = "getAllOrders")
    public List<Order> getAllOrders() {
        return proxy.getAllOrders();
    }

    @WebMethod(operationName = "getAllItems")
    public List<Item> getAllItems(@WebParam(name = "id") int id, @WebParam(name = "name") String name) {
        return proxy.getAllItems(id, name);
    }

    //get last inserted id
    @WebMethod(operationName = "getLastInsertedId")
    public int getLastInsertedId(@WebParam(name = "type") String type) {
        return proxy.getLastID(type);
    }

    //login
    @WebMethod(operationName = "login")
    public User login(@WebParam(name = "email") String email, @WebParam(name = "password") String password) {
        User u = proxy.login(email);
        String pwHash = Encrypt.getMd5(password);
        if (u != null && u.getPassword().equals(pwHash)) {
            return u;
        }
        return null;
    }
}
