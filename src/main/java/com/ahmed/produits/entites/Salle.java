package com.ahmed.produits.entites;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
public class Salle {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long ids;
 	@NotNull
 	@Size (min = 4,max = 40)
    private String type ;
 	
 	@OneToMany(mappedBy="salle")
	@JsonIgnore
	private List<Reclamation> reclamations;
 	
 	@OneToMany(mappedBy="salle")
	@JsonIgnore
	private List<Rattrapage> rattrapages;
 	
 	@OneToMany(mappedBy="salle")
	@JsonIgnore
	private List<Reservation> reservations;
 	
 	

	public Salle(Long ids, @NotNull @Size(min = 4, max = 40) String type, List<Reclamation> reclamations) {
		super();
		this.ids = ids;
		this.type = type;
		this.reclamations = reclamations;
	}
	
	

	public Salle() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getIds() {
		return ids;
	}

	public void setIds(Long ids) {
		this.ids = ids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Reclamation> getReclamations() {
		return reclamations;
	}

	public void setReclamations(List<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}



	@Override
	public String toString() {
		return "Salle [ids=" + ids + ", type=" + type + ", reclamations=" + reclamations + "]";
	} 
	
	
 	
 	
 	
 	

}
