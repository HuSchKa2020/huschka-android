package com.example.hwr_huschka;

public class Constants {

    public static final String ROOT_URL = "http://huschka.ddnss.de/huschka/v1/"; // muss noch angepasst werden

    public static final String URL_LOGIN_USER = ROOT_URL + "UserLogin.php";
    public static final String URL_REGISTER_USER = ROOT_URL + "registerUser.php";
    public static final String URL_LOAD_USER_DATA = ROOT_URL + "getUserData.php";
    public static final String URL_CHANGE_PASSWORD = ROOT_URL + "UpdatePasswort.php";
    public static final String URL_CHANGE_NAME = ROOT_URL + "UpdateName.php";
    public static final String URL_CHANGE_ADRESS = ROOT_URL + "UpdateAdresse.php";

    public static final String URL_GET_PRODUCTS = ROOT_URL + "getProduct.php";
    public static final String URL_PRODUCT_INFO = ROOT_URL + "getProductInfo.php";

    public static final String URL_GET_PRODUCT_OF_SHOPPINGLIST = ROOT_URL + "getProductsbyListenID.php";
    public static final String URL_GET_PRICE_OF_SHOPPINGLIST = ROOT_URL + "TotalPrice.php";

    public static final String URL_GET_USERS_SHOPPINGLIST = ROOT_URL + "getShoppingListbyUser.php";
    public static final String URL_ADD_LIST = ROOT_URL + "createShoppinglist.php";
    public static final String URL_DELETE_ALL_PRODUCTS_FROM_LIST = ROOT_URL + "DeleteAllProductInShoppinglist.php";
    public static final String URL_DELETE_SHOPPINGLIST = ROOT_URL + "DeleteShoppinglist.php";
    public static final String URL_SHOPPINGLIST_POSITIONS = ROOT_URL + "ShoppinglistLocation.php";

    public static final String URL_ADD_PRODUCT_SHOPPINGLIST = ROOT_URL + "InsertProductJsonArray.php";



    // parameter for StringRequests
    // Shoppinglist params
    public static final String REQ_PARAM_SHOPPINGLISTID = "ListenID";
    public static final String REQ_PARAM_NUMBEROF_PRODUCTS = "Anzahl";
    public static final String REQ_PARAM_SHOPPINGLIST_DATE = "Erstelldatum";
    public static final String REQ_PARAM_SHOPPINGLIST_SUPERMARKET= "Supermarkt";

    // User params
    public static final String REQ_PARAM_USERID = "KundenID";
    public static final String REQ_PARAM_USER_MAIL = "Email";
    public static final String REQ_PARAM_USER_PASSWORD = "Password";
    public static final String REQ_PARAM_USER_FIRSTNAME = "Vorname";
    public static final String REQ_PARAM_USER_FAMILYNAME = "Nachname";
    public static final String REQ_PARAM_USER_ADDRESS = "Adresse";

    public static final String REQ_PARAM_NEWPASSWORD = "neuesPasswort";
    public static final String REQ_PARAM_OLDPASSWORD = "altesPasswort";

    // Product params
    public static final String REQ_PARAM_PRODUCTID = "ProduktID";
    public static final String REQ_PARAM_PRODUCT_ARRAY = "ProductArray";
    public static final String REQ_PARAM_PRODUCT_TEILNAME = "Produktname";



    // return names from String Request
    public static final String REQ_RETURN_SHOPPINGLISTID = "ListenID";
    public static final String REQ_RETURN_SHOPPINGLIST_DATUM = "Erstelldatum";
    public static final String REQ_RETURN_SHOPPINGLIST_SUPERMARKT = "Supermarkt";
    public static final String REQ_RETURN_SHOPPINGLIST_STATUS = "Status";
    public static final String REQ_RETURN_SHOPPINGLIST_NUMBEROF_PRODUCTS = "Anzahl";
    public static final String REQ_RETURN_SHOPPINGLIST_PRICE = "Gesamtpreis";

    public static final String REQ_RETURN_USERID = "UserID";
    public static final String REQ_RETURN_USER_MAIL = "Email";
    public static final String REQ_RETURN_USER_FIRSTNAME = "Vorname";
    public static final String REQ_RETURN_USER_FAMILYNAME = "Nachname";
    public static final String REQ_RETURN_USER_ADRESSE = "Adresse";
    public static final String REQ_RETURN_USER_BANKVERBINDUNG = "Bankverbindung";

    public static final String REQ_RETURN_PRODUKTID = "ProduktID";
    public static final String REQ_RETURN_PRODUKT_NAME = "Name";
    public static final String REQ_RETURN_PRODUKT_PRODUCER = "Hersteller";
    public static final String REQ_RETURN_PRODUKT_PRICE = "Preis";
    public static final String REQ_RETURN_PRODUKT_KCAL = "Kcal";
    public static final String REQ_RETURN_PRODUKT_KATEGORIE = "Kategorie";
    public static final String REQ_RETURN_PRODUKT_LISTE = "Produkte";

    public static final String REQ_RETURN_PRODUKT_REIHE = "Reihe";
    public static final String REQ_RETURN_PRODUKT_REGALHOEHE = "Regalhoehe";



    public static final String REQ_RETURN_ALL_SCORES = "Scores";
    public static final String REQ_RETURN_GESUNDHEITSSCORE = "GesundheitsScore";
    public static final String REQ_RETURN_UMWELTSCORE = "UmweltScore";
    public static final String REQ_RETURN_GESAMTSCORE = "GesamtScore";
    public static final String REQ_RETURN_ERNAEHRUNGSFORM = "Ernaehrungsform";






}
