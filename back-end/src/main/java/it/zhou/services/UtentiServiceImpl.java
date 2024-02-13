package it.zhou.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.zhou.entities.Utenti;
import it.zhou.repos.UtentiDAO;

@Service
public class UtentiServiceImpl implements UtentiService {

	@Autowired
	private UtentiDAO dao;
	
	@Override
	public Utenti add(Utenti u) {
		return dao.save(u);
	}

	@Override
	public Utenti get(int id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Utenti> getAll() {
		return dao.findAll();
	}

	@Override
	public Utenti update(Utenti u) {
		return dao.save(u);
	}

	@Override
	public void delete(int id) {
		dao.deleteById(id);
	}

}