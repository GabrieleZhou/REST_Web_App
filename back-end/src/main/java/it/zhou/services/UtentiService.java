package it.zhou.services;

import java.util.List;

import it.zhou.entities.Utenti;

public interface UtentiService {

	//CRUD
	
	Utenti add(Utenti u);
	Utenti get(int id);
	List<Utenti> getAll();
	Utenti update(Utenti u);
	void delete(int id);
	
}