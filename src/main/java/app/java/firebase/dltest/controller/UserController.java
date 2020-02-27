package app.java.firebase.dltest.controller;

import javax.validation.Valid;

import app.java.firebase.dltest.entity.User;
import app.java.firebase.dltest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/")
public class UserController {

	private final UserRepository userRepository;

	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("signup")
	public String showSignUpForm(User user) {
		return "add-user";
	}

	public String generateDynamicUrl () {

		String SUB_DOMAIN = "testdynamiclinks00";
		String DEEP_LINK = "www.google.com?param";
		String PACKAGE_NAME = "com.google.app";
		String FALLBACK_LINK = "https://play.google.com/store/apps/details?id=com.attijariwafabank.corporate&hl=fr";
		String MIN_VERSION = "8.0";

		return "https://" + SUB_DOMAIN + ".page.link/?link=" + DEEP_LINK
				+ "&apn=" + PACKAGE_NAME + "&amv=" + MIN_VERSION + "&afl=" + FALLBACK_LINK;
	}

	@GetMapping("list")
	public String getUsers(Model model) {
		model.addAttribute("user", userRepository.findAll());
		userRepository.findAll().forEach(u -> log.info(u.getName()));
		log.info(String.valueOf(model));
		return "index";
	}

	@PostMapping("add")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-user";
		}
		log.info("saved user {}", user.getName());
		user.setUserurl(generateDynamicUrl());
		userRepository.save(user);
		return "redirect:list";
	}

}
