package it.zhou.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Libri {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String titolo;
	private String autore;
	private String codice_ISBN;
	private String data_aggiunta;
	private String data_eliminazione;
	private String trama;
	private int numero_letture;
	private int rating;
	private String pagine;
	private boolean evidenza;
	private String data_pubblicazione;
	private int numero_pagine;
	private boolean mark;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public String getCodice_ISBN() {
		return codice_ISBN;
	}
	public void setCodice_ISBN(String codice_ISBN) {
		this.codice_ISBN = codice_ISBN;
	}
	public String getData_aggiunta() {
		return data_aggiunta;
	}
	public void setData_aggiunta(String data_aggiunta) {
		this.data_aggiunta = data_aggiunta;
	}
	public String getData_eliminazione() {
		return data_eliminazione;
	}
	public void setData_eliminazione(String data_eliminazione) {
		this.data_eliminazione = data_eliminazione;
	}
	public String getTrama() {
		return trama;
	}
	public void setTrama(String trama) {
		this.trama = trama;
	}
	public int getNumero_letture() {
		return numero_letture;
	}
	public void setNumero_letture(int numero_letture) {
		this.numero_letture = numero_letture;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getPagine() {
		return pagine;
	}
	public void setPagine(String pagine) {
		this.pagine = pagine;
	}
	public boolean isEvidenza() {
		return evidenza;
	}
	public void setEvidenza(boolean evidenza) {
		this.evidenza = evidenza;
	}
	public String getData_pubblicazione() {
		return data_pubblicazione;
	}
	public void setData_pubblicazione(String data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}
	public int getNumero_pagine() {
		return numero_pagine;
	}
	public void setNumero_pagine(int numero_pagine) {
		this.numero_pagine = numero_pagine;
	}
	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	
}