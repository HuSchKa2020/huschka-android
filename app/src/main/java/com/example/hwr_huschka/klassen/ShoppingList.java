package com.example.hwr_huschka.klassen;

import org.threeten.bp.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class ShoppingList implements Serializable {

	//Eigenschaften
	private int listenID;
	private LocalDate datum;
	private String supermarkt;
	private String status;
	private double gesundheitsscore;
	private double umweltscore;
	private String ernaehrungsform;
	private HashMap<Product, Integer> inhalt = new HashMap<Product, Integer>();
	
	//Konstruktoren
	public ShoppingList(LocalDate datum, String supermarkt, String status) {
		this.datum = datum;
		this.supermarkt = supermarkt;
		this.status = status;
	}
	public ShoppingList(String supermarkt, String status){
		this.supermarkt = supermarkt;
		this.status = status;
		this.datum = LocalDate.of(2020,04,19);
	}

	public ShoppingList(int listenID, LocalDate datum, String supermarkt, String status) {
		this.listenID = listenID;
		this.datum = datum;
		this.supermarkt = supermarkt;
		this.status = status;

	}
	public ShoppingList(int listenID, LocalDate datum, String supermarkt) {
		this.listenID = listenID;
		this.datum = datum;
		this.supermarkt = supermarkt;
		this.status = status;
	}
	
	//Methoden

	@NonNull
	@Override
	public String toString() {
		return "ID: " + this.listenID + ", Supermarkt: " + this.supermarkt;
	}

	//setter
	public void setListenID(int listenID) {
		this.listenID = listenID;
	}
	
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	
	public void setSupermarkt(String supermarkt) {
		this.supermarkt = supermarkt;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setInhalt(HashMap<Product, Integer> inhalt) {
		this.inhalt = inhalt; 
	}

	public void  setGesundheitsscore(double gesundheitsscore) { this.gesundheitsscore = gesundheitsscore; }

	public void  setUmweltscore(double umweltscore) { this.umweltscore = umweltscore; }

	public void  setErnaehrungsform(String ernaehrungsform) { this.ernaehrungsform = ernaehrungsform; }

	//getter
	public int getListenID() {
		return listenID;
	}
	
	public LocalDate getDatum() {
		return datum;
	}
	
	public String getSupermarkt() {
		return supermarkt;
	}
	
	public String getStatus() {
		return status;
	}

	public HashMap<Product, Integer> getInhalt() {
		return inhalt;
	}

	public double getGesundheitsscore() {
		return gesundheitsscore;
	}

	public double getUmweltscore() {
		return umweltscore;
	}

	public String getErnaehrungsform() {
		return ernaehrungsform;
	}
	
}
