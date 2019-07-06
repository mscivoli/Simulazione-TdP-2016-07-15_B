package it.polito.tdp.flight.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento>{
	
	public enum TipoEvento{
		PARTENZA;
	}
	
	private TipoEvento tipo;
	private Aereoporto aereoporto;
	private int codicePasseggero;
	private LocalTime orario;
	public Evento(TipoEvento tipo, Aereoporto aereoporto, int codicePasseggero, LocalTime orario) {
		super();
		this.tipo = tipo;
		this.aereoporto = aereoporto;
		this.codicePasseggero = codicePasseggero;
		this.orario = orario;
	}
	public TipoEvento getTipo() {
		return tipo;
	}
	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}
	public Aereoporto getAereoporto() {
		return aereoporto;
	}
	public void setAereoporto(Aereoporto aereoporto) {
		this.aereoporto = aereoporto;
	}
	public int getCodicePasseggero() {
		return codicePasseggero;
	}
	public void setCodicePasseggero(int codicePasseggero) {
		this.codicePasseggero = codicePasseggero;
	}
	public LocalTime getOrario() {
		return orario;
	}
	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.orario.compareTo(o.getOrario());
	}
	@Override
	public String toString() {
		return "Evento [tipo=" + tipo + ", aereoporto=" + aereoporto + ", codicePasseggero=" + codicePasseggero
				+ ", orario=" + orario + "]";
	}
	
	

}
