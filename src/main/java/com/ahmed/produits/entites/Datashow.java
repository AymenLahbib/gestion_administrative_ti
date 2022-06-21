package com.ahmed.produits.entites;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Datashow {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long idv;
	
	@Min(value = 1)
 	@Max(value = 20)
    private double nb_v ;
	
	boolean valable=false;
	
	@OneToMany(mappedBy="datashow")
	@JsonIgnore
	private List<Reservation> reservations;

	public Datashow(Long idv, @Min(1) @Max(20) double nb_v, boolean valable, List<Reservation> reservations) {
		super();
		this.idv = idv;
		this.nb_v = nb_v;
		this.valable = valable;
		this.reservations = reservations;
	}

	public Datashow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdv() {
		return idv;
	}

	public void setIdv(Long idv) {
		this.idv = idv;
	}

	public double getNb_v() {
		return nb_v;
	}

	public void setNb_v(double nb_v) {
		this.nb_v = nb_v;
	}

	public boolean isValable() {
		return valable;
	}

	public void setValable(boolean valable) {
		this.valable = valable;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public String toString() {
		return "Datashow [idv=" + idv + ", nb_v=" + nb_v + ", valable=" + valable + ", reservations=" + reservations
				+ "]";
	}
	
	public boolean isDispo() { 
		boolean p=false;
		if(valable==true)
			 p=true;
		return p;
	}
	
	

}
