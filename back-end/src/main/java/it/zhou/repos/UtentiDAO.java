package it.zhou.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import it.zhou.entities.Utenti;

public interface UtentiDAO extends JpaRepository<Utenti, Integer> {

}