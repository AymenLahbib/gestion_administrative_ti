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


import com.ahmed.produits.entites.Reclamation;
import com.ahmed.produits.entites.Salle;
import com.ahmed.produits.repos.ReclamationRepository;
import com.ahmed.produits.repos.SalleRepository;


@Controller
public class ReclamationController {
	
	@Autowired
	SalleRepository salleRepository;
	
	@Autowired
	ReclamationRepository reclamationRepository;
	
	// Une Fonction qui retourne  la formulaire d'Ajout Reclamation
	@RequestMapping("/showCreateReclamation")
	public String showCreate(ModelMap modelMap) {
		List<Salle> sals = salleRepository.findAll();
		Reclamation rec =new Reclamation();
	    Salle sal=new Salle();
		sal=sals.get(0);
        rec.setSalle(sal);
		modelMap.addAttribute("reclamation", rec);
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("salles", sals);
		rec.setEtat("En cours");

		return "formReclamation";
	}
	// Une Fonction qui sauvegarde la Reclamation en donnant un Etat "En cours" Automatiquement et Redirect vers La Page Lister

	@RequestMapping("/saveReclamation")
	public String saveEnseignant(@Valid Reclamation Reclamation,
			 BindingResult bindingResult) 
	{
		if (bindingResult.hasErrors()) return "formReclamation";
		Reclamation.setEtat("En cours");
		reclamationRepository.save(Reclamation);
	 return "redirect:/ListeReclamations";
	}
	
	// Une Fonction qui approuve la Reclamation en prenant leur ID 
	@RequestMapping("/modifierRec")
	public String editerRec(@RequestParam("id") Long id,ModelMap modelMap,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size)
	{
		String p= reclamationRepository.getById(id).getEtat();
		//Changer l'etet  du reclamation qui a l'id passer comme parametre en Approuvé
		reclamationRepository.getById(id).setEtat("Approuvé");

		Reclamation p1=reclamationRepository.save(reclamationRepository.getById(id));
		
		Page<Reclamation> rec = reclamationRepository.findAll(PageRequest.of(page, size));
		modelMap.addAttribute("reclamation", rec);
		modelMap.addAttribute("Reclamations", rec);
		modelMap.addAttribute("pages", new int[rec.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("reclamation", p);
	modelMap.addAttribute("mode", "edit");
	
	return "listeReclamations";
	}
	
	// Une Fonction qui Refus la Reclamation en prenant leur ID 

	@RequestMapping("/refuserRec")
	public String refuserRec(@RequestParam("id") Long id,ModelMap modelMap,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size)
	{
		String p= reclamationRepository.getById(id).getEtat();
		
		//Changer l'etet  du reclamation qui a l'id passer comme parametre en Refusé

		reclamationRepository.getById(id).setEtat("Refusé");

		Reclamation p1=reclamationRepository.save(reclamationRepository.getById(id));
		
		Page<Reclamation> rec = reclamationRepository.findAll(PageRequest.of(page, size));
		
		modelMap.addAttribute("reclamation", rec);
		modelMap.addAttribute("Reclamations", rec);
		modelMap.addAttribute("pages", new int[rec.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("reclamation", p);
	modelMap.addAttribute("mode", "edit");
	
	return "listeReclamations";
	}
	
	
	// Une Fonction qui Liste les Reclamations

	@RequestMapping("/ListeReclamations")
	public String listeReclamations(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "2") int size) {
		Page<Reclamation> rec = reclamationRepository.findAll(PageRequest.of(page, size));
		List<Salle> sals = salleRepository.findAll();
		Reclamation re =new Reclamation();
	    Salle sal=new Salle();
		sal=sals.get(0);
        re.setSalle(sal);
		modelMap.addAttribute("reclamation", rec);
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("salles", sals);
		modelMap.addAttribute("Reclamations", rec);
		modelMap.addAttribute("pages", new int[rec.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeReclamations";
	}
	
	
	// Une Fonction qui supprime la Reclamation en prenant leur ID 

	@RequestMapping("/supprimerReclamation")
	public String supprimerReclamation(@RequestParam("id") Long id, ModelMap modelMap,

		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "2") int size) {
		//Supprimer la reclamation qui a l'id passer comme parametre
		reclamationRepository.deleteById(id);
		Page<Reclamation> rec = reclamationRepository.findAll(PageRequest.of(page, size));
		modelMap.addAttribute("reclamations", rec);
		modelMap.addAttribute("pages", new int[rec.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeReclamations";
	}


}
