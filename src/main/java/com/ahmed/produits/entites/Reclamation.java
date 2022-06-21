package com.ahmed.produits.entites;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
public class Reclamation {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long idrec;
 	@NotNull
 	@Size (min = 4,max = 40)
    private String type_rec ;
 	@Size (min = 1,max = 40)
    private String priorite ;
 	@Size (min = 4,max = 40)
    private String description ;
 	private String etat ;
 	
 	 public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	@ManyToOne//plusieur produit que avoir une meme produit
	    private Salle salle;

	public Reclamation(Long idrec, @NotNull @Size(min = 4, max = 40) String type_rec,
			@Size(min = 4, max = 40) String priorite, @Size(min = 4, max = 40) String description, Salle salle,String etat) {
		super();
		this.idrec = idrec;
		this.type_rec = type_rec;
		this.priorite = priorite;
		this.description = description;
		this.salle = salle;
		this.etat="En Cours";
	}

	public Reclamation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdrec() {
		return idrec;
	}

	public void setIdrec(Long idrec) {
		this.idrec = idrec;
	}

	public String getType_rec() {
		return type_rec;
	}

	public void setType_rec(String type_rec) {
		this.type_rec = type_rec;
	}

	public String getPriorite() {
		return priorite;
	}

	public void setPriorite(String priorite) {
		this.priorite = priorite;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	@Override
	public String toString() {
		return "Reclamation [idrec=" + idrec + ", type_rec=" + type_rec + ", priorite=" + priorite + ", description="
				+ description + ", salle=" + salle + "]";
	}
 	 
 	 
	
 	
 	
 	
 	


}
