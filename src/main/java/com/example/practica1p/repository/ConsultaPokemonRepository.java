package com.example.practica1p.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practica1p.entity.ConsultaPokemon;

@Repository("consultaPokemonRepository")
public interface ConsultaPokemonRepository extends JpaRepository<ConsultaPokemon, Long> {

}
