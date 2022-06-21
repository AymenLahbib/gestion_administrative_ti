package com.ahmed.produits.repos;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmed.produits.entites.Rattrapage;
import com.ahmed.produits.entites.User;

public interface RattrapageRepository extends JpaRepository<Rattrapage, Long> {
	Rattrapage findBymodule (String module);

}
