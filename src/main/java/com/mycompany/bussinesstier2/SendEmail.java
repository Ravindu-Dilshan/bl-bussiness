/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bussinesstier2;

import classes.Item;
import classes.Order;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admins
 */
public class SendEmail {

    static Properties properties = new Properties();

    //set autentication properties
    static {

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    }
//welcome message when a user registeres

    public static void welcome(String email) {
        String content = "<h2>Welcome!</h2>\n"
                + "<h4>Thank you For registering With BlackLotus Cakes</h4>";
        try {
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("blcakes69@gmail.com", getP("I8U4ca8BEiTn3WpIyj4IW3vlIIrNwBrKVXda6/z6arM="));
                }
            };
            Session session = Session.getInstance(properties, auth);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("blcakes69@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSentDate(new Date());
            message.setSubject("BlackLotus Cakes Welcome");
            message.setContent(content, "text/html");
            Transport.send(message);
            System.out.println("The e-mail was sent successfully (welcome)");

        } catch (NoClassDefFoundError e) {
            System.out.println("============================Server No Class Def Found Error=========================");
            System.out.println(e.getMessage());
            throw e;
        } catch (MessagingException e) {
            System.out.println("error in sending mail");
        }
    }
//send invoices when a vendor add a orders

    public static void SendInvoice(String email, Order order, List<Item> itemList) {
        String items = "";
        int netTotal = 0;
        if (itemList.isEmpty() || itemList == null) {
            items += "No Records";
        } else {
            //setting item list
            for (Item i : itemList) {
                int amount = Integer.parseInt(i.getStock().getProduct().getPrice()) * Integer.parseInt(i.getQuantity());
                netTotal += amount;
                items += "\n<tr>"
                        + "<td><span>" + i.getStock().getStockID() + "</span></td>"
                        + "<td><span>" + i.getStock().getProduct().getName() + "</span></td>"
                        + "<td><span>Rs.</span><span>" + i.getStock().getProduct().getPrice() + ".00</span></td>"
                        + "<td><span>" + i.getQuantity() + "</span></td>"
                        + "<td><span>Rs.</span>" + amount + ".00<span></span></td>"
                        + "</tr>\n";
            }
        }
        //web content
        String content = "<html>\n"
                + "    <head>\n"
                + "        <meta charset=\"utf-8\">\n"
                + "\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "\n"
                + "        <style>\n"
                + "            h1 { font: bold 100% sans-serif; letter-spacing: 0.5em; text-align: center; text-transform: uppercase; }\n"
                + "            table { font-size: 75%; table-layout: fixed; width: 100%; }\n"
                + "            table { border-collapse: separate; border-spacing: 2px; }\n"
                + "            th, td { border-width: 1px; padding: 0.5em; position: relative; text-align: left; }\n"
                + "            th, td { border-radius: 0.25em; border-style: solid; }\n"
                + "            th { background: #EEE; border-color: #BBB; }\n"
                + "            td { border-color: #DDD; }\n"
                + "            header { margin: 0 0 3em; }\n"
                + "            header:after { clear: both; content: \"\"; display: table; }\n"
                + "            header h1 { background: #000; border-radius: 0.25em; color: #FFF; margin: 0 0 1em; padding: 0.5em 0; }\n"
                + "            header address { float: left; font-size: 75%; font-style: normal; line-height: 1.25; margin: 0 1em 1em 0; }\n"
                + "            header address p { margin: 0 0 0.25em; }\n"
                + "            header span, header img { display: block; float: right; }\n"
                + "            header span { margin: 0 0 1em 1em; max-height: 25%; max-width: 60%; position: relative; }\n"
                + "            header img { max-height: 100%; max-width: 100%; }\n"
                + "            header input { cursor: pointer; -ms-filter:\"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)\"; height: 100%;"
                + " left: 0; opacity: 0; position: absolute; top: 0; width: 100%; }\n"
                + "            article, article address, table.meta, table.inventory { margin: 0 0 3em; }\n"
                + "            article:after { clear: both; content: \"\"; display: table; }\n"
                + "            article h1 { clip: rect(0 0 0 0); position: absolute; }\n"
                + "            article address { float: left; font-weight: bold; }\n"
                + "            table.meta, table.balance { float: right; width: 36%; }\n"
                + "            table.meta:after, table.balance:after { clear: both; content: \"\"; display: table; }\n"
                + "            table.meta th { width: 40%; }\n"
                + "            table.meta td { width: 60%; }\n"
                + "            table.inventory { clear: both; width: 100%; }\n"
                + "            table.inventory th { font-weight: bold; text-align: center; }\n"
                + "            table.inventory td:nth-child(1) { width: 26%; }\n"
                + "            table.inventory td:nth-child(2) { width: 38%; }\n"
                + "            table.inventory td:nth-child(3) { text-align: right; width: 12%; }\n"
                + "            table.inventory td:nth-child(4) { text-align: right; width: 12%; }\n"
                + "            table.inventory td:nth-child(5) { text-align: right; width: 12%; }\n"
                + "            table.balance th, table.balance td { width: 50%; }\n"
                + "            table.balance td { text-align: right; }\n"
                + "        </style>\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <header>\n"
                + "            <h1>Invoice</h1>\n"
                + "            <address>\n"
                + "                <p>" + order.getVendor().getAddress() + "</p>\n"
                + "                <p>" + order.getVendor().getTelephone() + "</p>\n"
                + "            </address>\n"
                + "        </header>\n"
                + "        <article>\n"
                + "            <address>\n"
                + "                <p>" + order.getVendor().getCompany() + "</p>\n"
                + "            </address>\n"
                + "            <table class=\"meta\">\n"
                + "            </table>\n"
                + "            <table class=\"inventory\">\n"
                + "                <thead>\n"
                + "                    <tr>\n"
                + "                        <th><span>Stock ID</span></th>\n"
                + "                        <th><span>Product Name</span></th>\n"
                + "                        <th><span>Price</span></th>\n"
                + "                        <th><span>Quantity</span></th>\n"
                + "                        <th><span>Total</span></th>\n"
                + "                    </tr>\n"
                + "                </thead>\n"
                + "                <tbody>\n"
                + items
                + "                </tbody>\n"
                + "            </table>\n"
                + "            <table class=\"balance\">\n"
                + "                <tr>\n"
                + "                    <th><span>Net Total</span></th>\n"
                + "                    <td><span>Rs.</span><span>" + netTotal + ".00</span></td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <th><span>Delivary Date</span></th>\n"
                + "                    <td><span>" + order.getDate() + "</span></td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <th><h1><span>Thank you !!</span></h1></th>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "        </article>\n"
                + "    </body>\n"
                + "</html>";
        try {
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("blcakes69@gmail.com", getP("I8U4ca8BEiTn3WpIyj4IW3vlIIrNwBrKVXda6/z6arM="));
                }
            };
            Session session = Session.getInstance(properties, auth);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("blcakes69@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSentDate(new Date());
            message.setSubject("BlackLotus Cakes Invoice");
            message.setContent(content, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("The e-mail was sent successfully (Order)");

        } catch (NoClassDefFoundError e) {
            System.out.println("============================Server No Class Def Found Error=========================");
            System.out.println(e.getMessage());
            throw e;
        } catch (MessagingException e) {
            System.out.println("error in sending mail");
            System.out.println(e.getMessage());
        }
    }
// not to be concerned

    public static String getP(String s) {
        String ing = getS("ugMdenYvb1AZ2Ivm3Kf/DQ==", "fafabin", "jarjarbin");
        String ing2 = getS("D9/gqcclsNXLMxQsRUlIbA==", "fafabin", "jarjarbin");
        String ing3 = getS(s, ing, ing2);
        return ing3;
    }
// not to be concerned

    public static String getS(String strToDecrypt, String secret, String salt) {//getP("I8U4ca8BEiTn3WpIyj4IW3vlIIrNwBrKVXda6/z6arM=")
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
