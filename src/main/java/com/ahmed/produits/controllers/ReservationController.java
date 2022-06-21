package com.ahmed.produits.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahmed.produits.entites.Datashow;
import com.ahmed.produits.entites.Motif;
import com.ahmed.produits.entites.Rattrapage;
import com.ahmed.produits.entites.Reclamation;
import com.ahmed.produits.entites.Reservation;
import com.ahmed.produits.entites.Salle;
import com.ahmed.produits.repos.RattrapageRepository;
import com.ahmed.produits.repos.ReservationRepository;
import com.ahmed.produits.repos.SalleRepository;

@Controller
public class ReservationController {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	SalleRepository salleRepository;
	
	
	// Une Fonction qui retourne  la formulaire d'Ajout une Reservation d'un DataShow

	@RequestMapping("/showCreateReservationDataShow")
	public String showCreate(ModelMap modelMap) {
		Reservation res=new Reservation();
		Datashow dt= new Datashow();
		if (dt.isDispo())
			res.setDatashow(dt);
		modelMap.addAttribute("reservation", res);
		modelMap.addAttribute("mode", "new");


		return "formReservationDataShow";
	}
	
	// Une Fonction qui sauvegarde la reservation en donnant un Etat "En cours" Automatiquement et Redirect vers La Page Lister

	@RequestMapping("/saveReservationDataShow")
	public String saveEnseignant(@Valid Reservation Reservation,
			 BindingResult bindingResult) 
	{
		if (bindingResult.hasErrors()) return "formReservationDataShow";
		Reservation.setEtat("En cours");
		reservationRepository.save(Reservation);
	 return "redirect:/ListeReservations";
	}
	
	// Une Fonction qui approuve la Reservation en prenant leur ID 

	@RequestMapping("/modifierRes")
	public String editerRec(@RequestParam("id") Long id,ModelMap modelMap,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size)
	{
		//Retouner l"etat du reservation qui a l'id passer comme parametre
		String p= reservationRepository.getById(id).getEtat();
		reservationRepository.getById(id).setEtat("Approuvé");

		Reservation p1=reservationRepository.save(reservationRepository.getById(id));
		
		Page<Reservation> rec = reservationRepository.findAll(PageRequest.of(page, size));
		modelMap.addAttribute("reservation", rec);
		modelMap.addAttribute("Reservations", rec);
		modelMap.addAttribute("pages", new int[rec.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("reservation", p);
	modelMap.addAttribute("mode", "edit");
	
	return "listeReservations";
	}
	
	// Une Fonction qui refus la Reservation en prenant leur ID 

	@RequestMapping("/refuserRes")
	public String refuserRec(@RequestParam("id") Long id,ModelMap modelMap,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size)
	{
		//Retouner l"etat du reservation qui a l'id passer comme parametre

		String p= reservationRepository.getById(id).getEtat();
		
		reservationRepository.getById(id).setEtat("Refusé");

		Reservation p1=reservationRepository.save(reservationRepository.getById(id));
		
		Page<Reservation> rec = reservationRepository.findAll(PageRequest.of(page, size));
		
		modelMap.addAttribute("reservation", rec);
		modelMap.addAttribute("Reservations", rec);
		modelMap.addAttribute("pages", new int[rec.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("reservation", p);
	modelMap.addAttribute("mode", "edit");
	
	return "listeReservations";
	}
	
	
	// Une Fonction qui retourne  la formulaire d'Ajout une Reservation d'une Salle

	@RequestMapping("/showCreateReservationSalle")
	public String showCreateSalle(ModelMap modelMap) {
		List<Salle> sals = salleRepository.findAll();

		Reservation res=new Reservation();
		  Salle sal=new Salle();
			sal=sals.get(0);

		modelMap.addAttribute("reservation", res);
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("salles", sals);



		return "formReservationSalle";
	}
	
	// Une Fonction qui sauvegarde la reservation en donnant un Etat "En cours" Automatiquement et Redirect vers La Page Lister

	@RequestMapping("/saveReservationSalle")
	public String saveEnseignantSalle(@Valid Reservation Reservation,
			 BindingResult bindingResult) 
	{
		if (bindingResult.hasErrors()) return "formReservationSalle";
		Reservation.setEtat("En cours");
		reservationRepository.save(Reservation);
	 return "redirect:/ListeReservations";
	}
	
	// Une Fonction qui Liste les Reservations

	@RequestMapping("/ListeReservations")
	public String listeRattrapages(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "2") int size) {
		Page<Reservation> resv = reservationRepository.findAll(PageRequest.of(page, size));
	
		modelMap.addAttribute("Reservations", resv);
		modelMap.addAttribute("pages", new int[resv.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeReservations";
	}
	// Une Fonction qui supprime la Reservation en prenant leur ID 

	@RequestMapping("/supprimerReservation")
	public String supprimerRattrapage(@RequestParam("id") Long id, ModelMap modelMap,

		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "2") int size) {
		//Supprimer la reservation qui a l'id passer comme parametre
		reservationRepository.deleteById(id);
		Page<Reservation> resv = reservationRepository.findAll(PageRequest.of(page, size));
		modelMap.addAttribute("reservations", resv);
		modelMap.addAttribute("pages", new int[resv.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeReservations";
	}

	

}
