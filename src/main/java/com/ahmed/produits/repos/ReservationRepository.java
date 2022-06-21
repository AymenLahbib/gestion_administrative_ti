package com.ahmed.produits.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmed.produits.entites.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
