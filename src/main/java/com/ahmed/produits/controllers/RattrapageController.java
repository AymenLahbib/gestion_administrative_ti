package com.ahmed.produits.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahmed.produits.entites.Rattrapage;
import com.ahmed.produits.entites.Reclamation;
import com.ahmed.produits.entites.Salle;
import com.ahmed.produits.entites.Motif;

import com.ahmed.produits.repos.MotifRepository;
import com.ahmed.produits.repos.RattrapageRepository;
import com.ahmed.produits.repos.SalleRepository;

@Controller
public class RattrapageController {
	
	@Autowired
	RattrapageRepository rattrapageRepository;
	
	@Autowired
	SalleRepository salleRepository;
	
	@Autowired
	MotifRepository motifRepository;
	
	// Une Fonction qui retourne  la formulaire d'Ajout Rattrapage
	@RequestMapping("/showCreateRattrapage")
	public String showCreate(ModelMap modelMap) {
		List<Salle> sals = salleRepository.findAll();
		List<Motif> mots = motifRepository.findAll();
		Rattrapage ratt=new Rattrapage();
		Motif mot=new Motif();
	    Salle sal=new Salle();
		mot=mots.get(0);
		sal=sals.get(0);
        ratt.setMotif(mot);
        ratt.setSalle(sal);
		modelMap.addAttribute("rattrapage", ratt);
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("salles", sals);
		modelMap.addAttribute("motifs", mots);

		return "formRattrapage";
	}
	
	
	// Une Fonction qui sauvegarde le Rattrapage en donnant un Etat "En cours" Automatiquement et Redirect vers La Page Lister
	@RequestMapping("/saveRattrapage")
	public String saveRat(@Valid Rattrapage rattrapage,
			 BindingResult bindingResult) 
	{
		if (bindingResult.hasErrors()) return "formRattrapage";
		rattrapage.setEtat("En cours");
		
		rattrapageRepository.save(rattrapage);
	 return "redirect:/ListeRattrapages";
	}
	
	// Une Fonction qui approuve le Rattrapage en prenant leur ID 
	@RequestMapping("/modifierRat")
	public String editerRec(@RequestParam("id") Long id,ModelMap modelMap,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size)
	{
		String p= rattrapageRepository.getById(id).getEtat();
		//Changer l'etet  de rattrapage qui a l'id passer comme parametre en Approuver

		rattrapageRepository.getById(id).setEtat("Approuvé");

		Rattrapage p1=rattrapageRepository.save(rattrapageRepository.getById(id));
		
		Page<Rattrapage> rec = rattrapageRepository.findAll(PageRequest.of(page, size));
		modelMap.addAttribute("rattrapage", rec);
		modelMap.addAttribute("rattrapages", rec);
		modelMap.addAttribute("pages", new int[rec.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("rattrapage", p);
	modelMap.addAttribute("mode", "edit");
	
	return "listeRattrapages";
	}
	
	
	// Une Fonction qui refus le Rattrapage en prenant leur ID 

	@RequestMapping("/refuserRat")
	public String refuserRec(@RequestParam("id") Long id,ModelMap modelMap,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size)
	{
		String p= rattrapageRepository.getById(id).getEtat();
		
		
		//Changer l'etet  de rattrapage qui a l'id passer comme parametre en Refusé

		rattrapageRepository.getById(id).setEtat("Refusé");

		Rattrapage p1=rattrapageRepository.save(rattrapageRepository.getById(id));
		
		Page<Rattrapage> rec = rattrapageRepository.findAll(PageRequest.of(page, size));
		
		modelMap.addAttribute("rattrapage", rec);
		modelMap.addAttribute("rattrapages", rec);
		modelMap.addAttribute("pages", new int[rec.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		
	modelMap.addAttribute("rattrapage", p);
	modelMap.addAttribute("mode", "edit");
	
	return "listeRattrapages";
	}
	
	
	// Une Fonction qui Liste les Rattrapage 

	@RequestMapping("/ListeRattrapages")
	public String listeRattrapages(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "2") int size) {
		Page<Rattrapage> ratt = rattrapageRepository.findAll(PageRequest.of(page, size));
		List<Salle> sals = salleRepository.findAll();
		modelMap.addAttribute("salles", sals);
		List<Motif> mots = motifRepository.findAll();
		
		Rattrapage rat=new Rattrapage();
		Motif mot=new Motif();
	    Salle sal=new Salle();
		mot=mots.get(0);
		sal=sals.get(0);
        rat.setMotif(mot);
        rat.setSalle(sal);
		modelMap.addAttribute("rattrapage", ratt);
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("salles", sals);
		modelMap.addAttribute("motifs", mots);
		modelMap.addAttribute("motifs", mots);
		modelMap.addAttribute("rattrapages", ratt);
		modelMap.addAttribute("pages", new int[ratt.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeRattrapages";
	}

	
	// Une Fonction qui supprime le Rattrapage en prenant leur ID 

	@RequestMapping("/supprimerRattrapage")
	public String supprimerRattrapage(@RequestParam("id") Long id, ModelMap modelMap,

		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "2") int size) {
		//Supprimer le rattrapage qui a l'id passer comme parametre
		rattrapageRepository.deleteById(id);
		//Refresh
		Page<Rattrapage> ratt = rattrapageRepository.findAll(PageRequest.of(page, size));
		modelMap.addAttribute("rattrapages", ratt);
		modelMap.addAttribute("pages", new int[ratt.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeRattrapages";
	}



	@RequestMapping("/modifierRattrapage")
	public String editerRattrapage(@RequestParam("id") Long id, ModelMap modelMap) {
		//Retouner le rattrapage qui a l'id passer comme parametre
		Rattrapage r = rattrapageRepository.findById(id).get();
		
		//Lister les salles et les motifs
		List<Salle> sals = salleRepository.findAll();
		List<Motif> mots = motifRepository.findAll();

		modelMap.addAttribute("rattrapage", r);
		modelMap.addAttribute("mode", "edit");
		modelMap.addAttribute("salles", sals);
		modelMap.addAttribute("motifs", mots);


		return "formRattrapage";
	}
	
}


