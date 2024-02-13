package it.zhou.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.zhou.entities.Libri;
import it.zhou.repos.LibriDAO;

@Service
public class LibriServiceImpl implements LibriService {

	@Autowired
	private LibriDAO dao;
	
	@Override
	public Libri add(Libri l) {
		return dao.save(l);
	}

	@Override
	public Libri get(int id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Libri> getAll() {
		return dao.findAll();
	}

	@Override
	public Libri update(Libri l) {
		return dao.save(l);
	}

	@Override
	public void delete(int id) {
		dao.deleteById(id);
	}

}