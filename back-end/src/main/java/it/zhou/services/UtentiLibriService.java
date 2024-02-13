package it.zhou.services;

import java.util.List;

import it.zhou.entities.UtentiLibri;

public interface UtentiLibriService {

	//CRUD
	
	UtentiLibri add(UtentiLibri ul);
	UtentiLibri get(int id);
	List<UtentiLibri> getAll();
	UtentiLibri update(UtentiLibri ul);
	void delete(int id);
	
}