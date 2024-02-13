package it.zhou.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import it.zhou.entities.Libri;

public interface LibriDAO extends JpaRepository<Libri, Integer> {

}