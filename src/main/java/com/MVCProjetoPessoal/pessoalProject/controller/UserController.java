package com.MVCProjetoPessoal.pessoalProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.MVCProjetoPessoal.pessoalProject.model.User;
import com.MVCProjetoPessoal.pessoalProject.model.repository.UserRepository;

@Controller
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/cadastro")
	public String getCadastro() {
		return "cadastro";
	}

	@PostMapping("/processarCadastro")
	public String cadastrando(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, RedirectAttributes redirectAttributes) {
		System.out.println("Username: " + username);
		Optional<User> existingUser = userRepository.findByUsername(username);
		if (existingUser.isPresent()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Username already exists.");
			return "redirect:/cadastro";
		}
		User newUser = new User(null, username, password); // Create a new user
		userRepository.save(newUser);
		return "redirect:/cadastroSucesso";
	}

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@PostMapping("/loginAcess")
	public String login(@RequestParam(name = "usingUser") String username,
			@RequestParam(name = "usingPassword") String password, Model model) {
		var user = userRepository.findByUsername(username);
		var userPass = userRepository.findByPassword(password);

		if (user.isPresent() && userPass.isPresent()) {
			return "redirect:/loginSucesso";
		} else {
			model.addAttribute("error", "Username or Password invalid! ");
			return "login";
		}
	}
	
	

	@GetMapping("/loginSucesso")
	public String getLoginSuccess() {
		return "loginSucesso.html";
	}
	
	

	@GetMapping("/cadastroSucesso")
	public String cadastroSucesso(Model model) {
	    model.addAttribute("exibirLogin", "/login"); // alterado para o caminho da página de login
	    return "sucesso";
	}
	
	
	
	@GetMapping("/redirectLogin")
	public String redirecionarLogin() {
		return "login.html";
	}
	

	@GetMapping("/users")
	public ResponseEntity<List<User>> findAll() {
		var user = userRepository.findAll();
		return ResponseEntity.ok().body(user);
	}
}
