package it.zhou.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UtentiLibri {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String listaLibri;
	private String listaLibriEliminati;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getListaLibri() {
		return listaLibri;
	}
	public void setListaLibri(String listaLibri) {
		this.listaLibri = listaLibri;
	}
	public String getListaLibriEliminati() {
		return listaLibriEliminati;
	}
	public void setListaLibriEliminati(String listaLibriEliminati) {
		this.listaLibriEliminati = listaLibriEliminati;
	}
	
	
}