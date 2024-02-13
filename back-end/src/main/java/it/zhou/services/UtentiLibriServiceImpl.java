package it.zhou.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.zhou.entities.UtentiLibri;
import it.zhou.repos.UtentiLibriDAO;

@Service
public class UtentiLibriServiceImpl implements UtentiLibriService {

	@Autowired
	private UtentiLibriDAO dao;
	
	@Override
	public UtentiLibri add(UtentiLibri ul) {
		return dao.save(ul);
	}

	@Override
	public UtentiLibri get(int id) {
		return dao.findById(id).get();
	}

	@Override
	public List<UtentiLibri> getAll() {
		return dao.findAll();
	}

	@Override
	public UtentiLibri update(UtentiLibri ul) {
		return dao.save(ul);
	}

	@Override
	public void delete(int id) {
		dao.deleteById(id);
	}

}