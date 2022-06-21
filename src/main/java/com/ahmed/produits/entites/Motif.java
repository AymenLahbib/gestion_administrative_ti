package com.ahmed.produits.entites;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class Motif {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idMotif;
	private String nomMot;
	
	@OneToMany(mappedBy="motif")
	@JsonIgnore
	private List<Rattrapage> rattrapages;
	
	public Motif() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Motif(Long idMotif, String nomMot, List<Rattrapage> rattrapages) {
		super();
		this.idMotif = idMotif;
		this.nomMot = nomMot;
		this.rattrapages = rattrapages;
	}





	public Long getIdMotif() {
		return idMotif;
	}





	public void setIdMotif(Long idMotif) {
		this.idMotif = idMotif;
	}





	public String getNomMot() {
		return nomMot;
	}





	public void setNomMot(String nomMot) {
		this.nomMot = nomMot;
	}





	public List<Rattrapage> getRattrapages() {
		return rattrapages;
	}





	public void setRattrapages(List<Rattrapage> rattrapages) {
		this.rattrapages = rattrapages;
	}





	@Override
	public String toString() {
		return "Motif [idMotif=" + idMotif + ", nomMot=" + nomMot + ", rattrapages=" + rattrapages + "]";
	}
	
	
	
	
	

}
