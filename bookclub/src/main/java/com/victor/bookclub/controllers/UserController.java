package com.victor.bookclub.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.victor.bookclub.models.LoginUser;
import com.victor.bookclub.models.User;
import com.victor.bookclub.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userServ;
	
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("newUser", new User());
		model.addAttribute("newLoginObject", new LoginUser());
		return "index.jsp";
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.setAttribute("userId", null);
		return "redirect:/";
		
	}
	
	@PostMapping("/register")
	public String register(
			@Valid @ModelAttribute("newUser") User newUser,
			BindingResult result,
			Model model,
			HttpSession session
			) {
		
		User user = userServ.register(newUser, result);
		
		if (result.hasErrors() || user == null) {
			model.addAttribute("newLoginObject", new LoginUser());
			return "index.jsp";
		}
		else {
			session.setAttribute("userId",  user.getId());
			return "redirect:/books";
		}
	}
	
	@PostMapping("/login")
	public String login(
			@Valid @ModelAttribute("newLoginObject") LoginUser newLoginObject,
			BindingResult result,
			Model model,
			HttpSession session
			) {
		
		User user = userServ.login(newLoginObject, result);
		
		if (result.hasErrors() || user == null) {
			model.addAttribute("newUser", new User());
			return "index.jsp";
		}
		else {
			session.setAttribute("userId", user.getId());
			return "redirect:/books";
		}
		
	}
	
	
}
