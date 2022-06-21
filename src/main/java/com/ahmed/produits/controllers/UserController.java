package com.ahmed.produits.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahmed.produits.entites.Role;
import com.ahmed.produits.entites.User;
import com.ahmed.produits.repos.RoleRepository;
import com.ahmed.produits.security.SecurityConfig;
import com.ahmed.produits.service.RoleService;
import com.ahmed.produits.service.UserService;


@Controller
public class UserController {
	
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepo;
	
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	// retournner la Page d'Ajout User depuis l'interface Admin
	@RequestMapping("/showCreate2")
	public String showCreate(ModelMap modelMap) {
				
		User user = new User();
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("user", user);
		
		List<Role> roles = roleService.findAll();
		modelMap.addAttribute("roles", roles);
		return "formUser";
	}
	
	// retournner la Page register User 

	@RequestMapping("/showCreate5")
	public String showCreate5(ModelMap modelMap) {
				
		User user = new User();
		user.setRoles(null);
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("user", user);
		
	
		return "register";
	}
	
	@RequestMapping("/search2")
	public String doSearchUser(@ModelAttribute("userSearchFormData")User formData,Model modelMap, @RequestParam("s") Long s) {
		List<Role> roles = roleRepo.findAll();
		modelMap.addAttribute("roles", roles);
		
		List<User> user=userService.findByRolesIdRole(s);
		modelMap.addAttribute("users",user);

		return "listeUsers";
	}

	

	
	// Lister Les uutilisateurs
	@RequestMapping("/ListeUsers")
	public String listeUsers(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {
		Page<User> use = userService.getAllUsersParPage(page, size);
		
		List<Role> roles = roleService.findAll();
		modelMap.addAttribute("roles", roles);
		modelMap.addAttribute("users", use);
		modelMap.addAttribute("pages", new int[use.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeUsers";
	}
	

	//Sauvegarder Un utilisateur depuis un admin 
		// Coder le mot de passe si la "$2a$10$" n'existe pas pour ne le coder 2 fois
	
	@RequestMapping("/saveUser")
	public String saveUser(@Valid User user, int s,
			 BindingResult bindingResult) 
	{
		if (bindingResult.hasErrors()) return "formUser";
		
		
		SecurityConfig sec = new SecurityConfig();
		PasswordEncoder passwordEncoder = sec.passwordEncoder();
		//ne contient $2a$10$ => Coder
		if(user.getPassword().contains("$2a$10$")!=true)
      	user.setPassword(passwordEncoder.encode(user.getPassword()));
		
     	user.setEnabled(true);
     			
     	List<Role> All= roleService.findAll();
     	   
     	Role r1=All.get(s -1);
     	List<Role> listR = new ArrayList<Role>();
     	listR.add(r1);
     	user.setRoles(listR);
  
     	List<Role> list=user.getRoles();
     	
		userService.saveUser(user);
	 return "redirect:/ListeUsers";
	}
	
	

//Sauvegarder Un utilisateur d'apres la formulaire du register en Donnant un role BLOCKER PAR DEFAUT
	// Coder le mot de passe 
	@RequestMapping("/saveUser5")
	public String saveUser5(@Valid User user,
			 BindingResult bindingResult) 
	{
		if (bindingResult.hasErrors()) return "register";
		
		
		SecurityConfig sec = new SecurityConfig();
		PasswordEncoder passwordEncoder = sec.passwordEncoder();
      	user.setPassword(passwordEncoder.encode(user.getPassword()));
      	
      	
     	user.setEnabled(true);
     	List<Role> All= roleService.findAll();
  	   //ID du Role BLOCKED 
     	Role r1=All.get(3);
     	List<Role> listR = new ArrayList<Role>();
     	listR.add(r1);
     	user.setRoles(listR);
  
     			
     	
  
     	
     	
		userService.saveUser(user);
	 return "redirect:/login";
	}
	
	
	//Supprimer Un utilisateur 
	@RequestMapping("/supprimerUser")
	public String supprimerUser(@RequestParam("id") Long id, ModelMap modelMap,

		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "2") int size) {

		User user =userService.getUser(id);
		user.setRoles(null);
		userService.updateUser(user);
		userService.deleteUserById(id);
		Page<User> use = userService.getAllUsersParPage(page,
				size);
		modelMap.addAttribute("users", use);
		modelMap.addAttribute("pages", new int[use.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeUsers";
	}
	
	
	@RequestMapping("/modifierUser")
	public String editerUser(@RequestParam("id") Long id, ModelMap modelMap) {
		
		User u = userService.getUser(id);
		List<Role> roles = roleService.findAll();
		modelMap.addAttribute("roles", roles);
		modelMap.addAttribute("user", u);
		modelMap.addAttribute("mode", "edit");
		


		return "formUser";
	}

	@RequestMapping("/updateUser")
	public String updateUser(@ModelAttribute("user") User user, @RequestParam("date") String date,int s,
			ModelMap modelMap) throws ParseException {
		SecurityConfig sec = new SecurityConfig();
		 PasswordEncoder passwordEncoder = sec.passwordEncoder();
		 user.setPassword(passwordEncoder.encode(user.getPassword()));
     	user.setEnabled(true);
     	List<Role> All= roleService.findAll();
     	Role r1=All.get(s -1);
    	List<Role> listR = new ArrayList<Role>();
     	listR.add(r1);
     	user.setRoles(listR);
     	List<Role> list=user.getRoles();
		userService.updateUser(user);
		List<User> use = userService.findAll();
		modelMap.addAttribute("users", use);
		return "listeUsers";
	}
	
	//Chercher Un utiliseur  En saisant un nom 
	@RequestMapping("/chercherNom")
    public String chercherProduit(@RequestParam("username") String nom,
    		ModelMap modelMap,
    		@RequestParam (name="page",defaultValue = "0") int page,
    		@RequestParam (name="size", defaultValue = "2") int size)
    
    
    {     
	 	
    	  User prods = userService.findUserByUsername(nom);
    	  
    	  modelMap.addAttribute("users",prods);
    	  
    	  return "listeUsers";}
	
	
	 
	
	


}
