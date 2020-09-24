package com.example.hwr_huschka.klassen;

import com.example.hwr_huschka.klassen.Position;

public class Produkt {

	//Eigenschaften
	
	private int produktID;
	private String name;
	private double preis;
	private String kategorie;
	private String hersteller;
	private int kcal;
	private Position position;
	
	
	//Konstruktor
	
	public Produkt(){
	}
	
	
	//Methoden
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public void setProduktName(String name) {
		this.name = name;
	}
	
	public void setProduktID(int produktID) {
		this.produktID = produktID;	
	}
	
	public void setPreis(double preis) {
		this.preis = preis;	
	}
	
	public void setHersteller(String hersteller) {
		this.hersteller = hersteller;
	}
	
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;	
	}
	
	public void setKcal(int kcal) {
		this.kcal = kcal;	
	}
	
	
	
	
	public Position getPosition() {
		return position;
	}
	
	public String getProduktName() {
		return name;
	}
	
	public int getProduktID() {
		return produktID;
	}
	
	public double getPreis() {
		return preis;
	}
	
	public String getHersteller() {
		return hersteller;
	}
	
	public String getKategorie() {
		return kategorie;
	}
	
	public int getKcal() {
		return kcal;
	}
	
}
