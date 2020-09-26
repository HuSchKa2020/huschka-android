package com.example.hwr_huschka.klassen;

import org.threeten.bp.LocalDate;
import java.util.ArrayList;

public class ShoppingList {

	//Eigenschaften
	private int listenID;
	private LocalDate datum;
	private String supermarkt;
	private String status;
	private ArrayList inhalt;
	
	//Konstruktoren
	public ShoppingList(LocalDate datum, String supermarkt, String status) {
		this.datum = datum;
		this.supermarkt = supermarkt;
		this.status = status;
	}
	
	//Methoden
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
	
	public void setInhalt(ArrayList inhalt) {
		this.inhalt = inhalt; 
	}

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

	public ArrayList getInhalt() {
		return inhalt;
	}
	
}
