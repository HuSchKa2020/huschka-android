package com.example.hwr_huschka.klassen;

public class Position {

	//Eigenschaften
	private String abteilung;
	private int reihe;
	private int regal;
		
	
	//Konstruktor
	
	public Position(String abteilung, int regal, int reihe){
		this.abteilung = abteilung;
		this.regal = regal;
		this.reihe = reihe;
	}
	
	
	//Methoden
	
		
	public void setAbteilung(String abteilung) {
		this.abteilung = abteilung;
	}
	
	public void setReihe(int reihe) {
		this.reihe = reihe;
	}
	
	public void setRegal(int regal) {
		this.regal = regal;			
	}


	public String getAbteilung() {
		return abteilung;
	}
	
	public int getReihe() {
		return reihe;
	}
	
	public int getRegal() {
		return regal;
	}
	
}
