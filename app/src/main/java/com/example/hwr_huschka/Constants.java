package com.example.hwr_huschka;

public class Constants {

    public static final String ROOT_URL = "http://91.65.187.116/jan/v1/"; // muss noch angepasst werden

    public static final String URL_LOGIN_USER = ROOT_URL + "UserLogin.php";
    public static final String URL_REGISTER_USER = ROOT_URL + "registerUser.php";

    public static final String URL_GET_PRODUCTS = ROOT_URL + "getProduct.php";
    public static final String URL_PRODUCT_INFO = ROOT_URL + "getProductInfo.php";

    public static final String URL_GET_PRODUCT_OF_SHOPPINGLIST = ROOT_URL + "getProductsbyListenID.php";

    public static final String URL_GET_USERS_SHOPPINGLIST = ROOT_URL + "getShoppingListbyUser.php";
    public static final String URL_ADD_LIST = ROOT_URL + "createShoppinglist.php";
    public static final String URL_DELETE_ALL_PRODUCTS_FROM_LIST = ROOT_URL + "DeleteProduct.php";

    public static final String URL_ADD_PRODUCT_SHOPPINGLIST = ROOT_URL + "InsertProductJsonArray.php";



    // parameter for StringRequests
    // Shoppinglist params
    public static final String REQ_PARAM_SHOPPINGLISTID = "ListenID";
    public static final String REQ_PARAM_NUMBEROF_PRODUCTS = "numberOf";

    // User params
    public static final String REQ_PARAM_USERID = "KundeID";
    public static final String REQ_PARAM_USER_MAIL = "mail";
    public static final String REQ_PARAM_USER_PASSWORD = "password";
    public static final String REQ_PARAM_USER_ADDRESS = "adresse";

    // Product params
    public static final String REQ_PARAM_PRODUCTID = "ProductID";


    // return names from String Request
    public static String REQ_RETURN_SHOPPINGLISTID = "ListenID";
    public static String REQ_RETURN_SHOPPINGLIST_DATUM = "date";
    public static String REQ_RETURN_SHOPPINGLIST_SUPERMARKT = "supermarkt";
    public static String REQ_RETURN_SHOPPINGLIST_STATUS = "status";

    public static String REQ_RETURN_USERID = "KundenID";
    public static String REQ_RETURN_USER_FIRSTNAME = "firstname";
    public static String REQ_RETURN_USER_FAMILYNAME = "familyname";

    public static String REQ_RETURN_PRODUKTID = "ProductID";
    public static String REQ_RETURN_PRODUKT_NAME = "produktname";
    public static String REQ_RETURN_PRODUKT_PRODUCER = "hersteller";
    public static String REQ_RETURN_PRODUKT_PRICE = "preis";
    public static String REQ_RETURN_PRODUKT_KCAL = "kcal";
    public static String REQ_RETURN_PRODUKT_KATEGORIE = "kategorie";



}
