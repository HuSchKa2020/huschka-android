package com.example.hwr_huschka.klassen;

import com.example.hwr_huschka.klassen.Position;

import androidx.annotation.NonNull;

public class Product {

	//Eigenschaften
	private int produktID = 0;
	private String name;
	private double preis;
	private String kategorie;
	private String hersteller;
	private int kcal;
	private Position position;

	//Konstruktoren
	public Product(int produktID, String name, double preis){
		this.produktID = produktID;
		this.name = name;
		this.preis = preis;
	}

	@NonNull
	@Override
	public String toString() {
		return "ID: " + produktID;
	}

	//Methoden
	// setter
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public void setName(String name) {
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

	// getter
	public Position getPosition() {
		return position;
	}
	
	public String getName() {
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
