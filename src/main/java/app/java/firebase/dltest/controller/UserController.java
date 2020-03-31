package app.java.firebase.dltest.controller;

import javax.validation.Valid;

import app.java.firebase.dltest.entity.User;
import app.java.firebase.dltest.repository.UserRepository;
import app.java.firebase.dltest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
@RequestMapping("/users/")
public class UserController {

	private final UserService userService;

	private final UserRepository userRepository;

	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserRepository userRepository, RestTemplate restTemplate, UserService userService, UserRepository userRepository1) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@GetMapping("signup")
	public String showSignUpForm(User user) {
		return "add-user";
	}

	@GetMapping("list")
	public String getUsers(Model model) {
		model.addAttribute("user", userRepository.findAll());
		userRepository.findAll().forEach(u -> log.info(u.getName()));
		log.info(String.valueOf(model));
		return "index";
	}

	@PostMapping("add")
	public String addUser(@Valid User user, BindingResult result, Model model) throws IOException {
		if (result.hasErrors()) {
			return "add-user";
		}
		log.info("saved user {}", user.getName());
		userService.saveUser(user);
		return "redirect:list";
	}

}
