package it.zhou.services;

import java.util.List;

import it.zhou.entities.Libri;

public interface LibriService {

	//CRUD
	
	Libri add(Libri l);
	Libri get(int id);
	List<Libri> getAll();
	Libri update(Libri l);
	void delete(int id);
	
}