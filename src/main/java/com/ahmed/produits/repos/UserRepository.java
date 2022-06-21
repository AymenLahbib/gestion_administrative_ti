package com.ahmed.produits.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmed.produits.entites.User;


public interface UserRepository extends JpaRepository<User, Long> {
      User findByUsername (String username);
  	List<User> findByRolesIdRole(Long id);


}
