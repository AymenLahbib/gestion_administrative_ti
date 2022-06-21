package com.ahmed.produits.service;

import java.util.List;

import com.ahmed.produits.entites.Role;

public interface RoleService {

	List <Role> findAll();
	Role findByRole(String role);



}
