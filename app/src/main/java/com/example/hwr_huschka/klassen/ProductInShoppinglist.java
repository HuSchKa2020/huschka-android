package com.example.hwr_huschka.klassen;

public class ProductInShoppinglist extends Product{

    int anzahl;
    boolean ausgewaehlt;

    public ProductInShoppinglist(int produktID, String hersteller, String name, String kategorie, double preis, int kcal, Position position, int anzahl) {
        super(produktID, hersteller, name, kategorie, preis, kcal, position);
        ausgewaehlt = false;
        this.anzahl = anzahl;
    }


    public boolean isAusgewaehlt() {
        return ausgewaehlt;
    }

    public void setAusgewaehlt(boolean ausgewaehlt) {
        this.ausgewaehlt = ausgewaehlt;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }


}
