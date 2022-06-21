package com.ahmed.produits.entites;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;

@Data
@Entity
public class Reservation {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long idres;
 	@NotNull
 	@Size (min = 4,max = 40)
    private String module ;

 	private String etat ;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateP;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateR;
    
	@NotNull
 	@Size (min = 4,max = 40)
    private String nom_club ;
    
    
    @ManyToOne//plusieur produit que avoir une meme produit
    private Salle salle;
    
    @ManyToOne//plusieur produit que avoir une meme produit
    private Datashow datashow;

	public Reservation(Long idres, @NotNull @Size(min = 4, max = 40) String module, Date dateP, Date dateR,
			@NotNull @Size(min = 4, max = 40) String nom_club, Salle salle, Datashow datashow) {
		super();
		this.idres = idres;
		this.module = module;
		this.dateP = dateP;
		this.dateR = dateR;
		this.nom_club = nom_club;
		this.salle = salle;
		this.datashow = datashow;
		this.etat="En cours";
	}

	public Reservation() {
		super();
	}
    
    

}
