package com.example.hwr_huschka.klassen;

import java.lang.reflect.Array;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShoppingList {

	//Eigenschaften
	
	private int listenID;
	private LocalDateTime datum;
	private String supermarkt;
	private String status;
	private ArrayList inhalt;
	
	//Konstruktoren
	
	public ShoppingList(){
		status = "In Bearbeitung";
	}
	
	
	//Methoden
	
	
	public void setListenID(int listenID) {
		this.listenID = listenID;
	}
	
	public void setDatum(LocalDateTime datum) {
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
	
	
	
	public int getListenID() {
		return listenID;
	}
	
	public LocalDateTime getDatum() {
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
