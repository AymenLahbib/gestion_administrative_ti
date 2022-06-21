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
public class Rattrapage {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long idrat;
 	@NotNull
 	@Size (min = 4,max = 40)
    private String module ;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEmbauche;
    
    @ManyToOne//plusieur produit que avoir une meme produit
    private Salle salle;
    
    @ManyToOne//plusieur produit que avoir une meme produit
    private Motif motif;
    
    private String etat ;

	public Rattrapage(@NotNull @Size(min = 4, max = 40) String module, Date dateEmbauche, Salle salle, Motif motif,
			String etat) {
		super();
		this.module = module;
		this.dateEmbauche = dateEmbauche;
		this.salle = salle;
		this.motif = motif;
		this.etat ="En cours";
	}

	public Rattrapage() {
		super();
		// TODO Auto-generated constructor stub
	} 
    
    

}
