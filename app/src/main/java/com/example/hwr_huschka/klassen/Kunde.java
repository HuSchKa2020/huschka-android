package com.example.hwr_huschka.klassen;

public class Kunde {

	//Eigenschaften
	
	private int kundenID;
	private String email;


	private String vorname;
	private String nachname;
	private String adresse;
	private String bank;
	
	//Konstruktor
	
	public Kunde(){
	}

	public Kunde(int kundenID, String email, String vorname, String nachname, String adresse) {
		this.kundenID = kundenID;
		this.email = email;
		this.vorname = vorname;
		this.nachname = nachname;
		this.adresse = adresse;
	}

	//Methoden
	
	//setter
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public void setKundenID(int kundenID) {
		this.kundenID = kundenID;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	//getter
	public String getEmail() {
		return email;
	}

	public String getVorname() {
		return vorname;
	}
	
	public String getNachname() {
		return nachname;
	}

	public int getKundenID() {
		return kundenID;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getBank() {
		return bank;
	}
}
