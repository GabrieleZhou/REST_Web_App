package it.zhou.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.zhou.entities.Libri;
import it.zhou.entities.Utenti;
import it.zhou.entities.UtentiLibri;

import it.zhou.services.LibriService;
import it.zhou.services.UtentiService;
import it.zhou.services.UtentiLibriService;


@RestController
@RequestMapping("api")
@CrossOrigin
public class ControllerREST {

	@Autowired
	private LibriService libriService;
	
	@Autowired
	private UtentiService utentiService;
	
	@Autowired
	private UtentiLibriService utentiLibriService;
	
	@GetMapping("libri")
	List<Libri> getLibri(){
		return libriService.getAll();
	}
	
	@GetMapping("utenti")
	List<Utenti> getUtenti(){
		return utentiService.getAll();
	}
	
	@GetMapping("utentiLibri")
	List<UtentiLibri> getUtentiLibri(){
		return utentiLibriService.getAll();
	}
	
	@GetMapping("utentiLibri/{id}")
    UtentiLibri getUtentiLibriById(@PathVariable int id) {
        return utentiLibriService.get(id);
    }
	
	@PutMapping("utentiLibri/{id}")
	UtentiLibri updateUtentiLibriByid(@PathVariable int id, @RequestBody UtentiLibri utenteLibroAggiornato) {
		
		UtentiLibri libreriaUtente = utentiLibriService.get(id);
		
		libreriaUtente.setListaLibri(utenteLibroAggiornato.getListaLibri());
		libreriaUtente.setListaLibriEliminati(utenteLibroAggiornato.getListaLibriEliminati());
		
		return utentiLibriService.update(libreriaUtente);
	}

	
	
}