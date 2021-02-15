package com.example.hwr_huschka.klassen;

public class ProductInShoppinglist extends Product implements Comparable<ProductInShoppinglist>{

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


    @Override
    public int compareTo(ProductInShoppinglist product) {
        if(this.isAusgewaehlt() && !product.isAusgewaehlt()) {
            return 1; // dieses Objekt ist "größer" als das übergebene Objekt, also weiter hinten in der Liste
        } else if (this.isAusgewaehlt() && !product.isAusgewaehlt()) {
            return -1; // dieses Objekt ist "kleiner" als das übergebene Objekt, also weiter vorne in der Liste
        } else {
            // nach dem Regal sortieren
            if (this.getPosition().getReihe() < product.getPosition().getReihe()) {
                return -1;
            } else if (this.getPosition().getReihe() > product.getPosition().getReihe()){
                return 1;
            } else {
                // nach Regal sortieren
                if(this.getPosition().getRegal() <= product.getPosition().getRegal()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }
}
