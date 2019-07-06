package it.polito.tdp.flight.model;

import com.javadocmd.simplelatlng.LatLng;

public class Aereoporto {
	
	private int id;
	private LatLng posizione;
	private String citta;
	private int n;
	public Aereoporto(int id, double latitudine, double longitudine, String citta) {
		this.id = id;
		this.posizione = new LatLng(latitudine, longitudine);
		this.citta = citta;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LatLng getPosizione() {
		return posizione;
	}
	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}
	@Override
	public String toString() {
		return "Aereoporto [id=" + id + ", posizione=" + posizione + "]";
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public int getN() {
		return n;
	}
	public void setN() {
		this.n++;
	}
	
	
	

}
