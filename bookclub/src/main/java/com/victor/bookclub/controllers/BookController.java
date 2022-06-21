package com.victor.bookclub.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.victor.bookclub.models.Book;
import com.victor.bookclub.models.User;
import com.victor.bookclub.services.BookService;
import com.victor.bookclub.services.UserService;

@Controller
public class BookController {

	@Autowired
	private BookService bookServ;
	@Autowired
	private UserService userServ;
	
	@GetMapping("/books")
	public String books(@ModelAttribute("book") Book book, Model model, HttpSession session) {
		
		if (session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		else {
			Long userId = (Long) session.getAttribute("userId");
			model.addAttribute("user", userServ.findById(userId));
			model.addAttribute("books", bookServ.allBooks());
			return "books.jsp";
		}
	}
	
	@PostMapping("/books")
	public String addBooks(
			@Valid @ModelAttribute("book") Book book,
			BindingResult result,
			Model model,
			HttpSession session
			) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/logout";
		}
		
		if (result.hasErrors()) {
			model.addAttribute("user", userServ.findById(userId));
			model.addAttribute("books", bookServ.allBooks());
			return "books.jsp";
		}
		else {
			User user = userServ.findById(userId);
			Book newBook = new Book(book.getTitle(), book.getDescription(), userServ.findById(userId));
			bookServ.createBook(newBook);
			user.getFavorites().add(newBook);
			userServ.updateUser(user);
			return "redirect:/books";
		}
	}
	
	@RequestMapping("/books/{id}/favorite/main")
	public String favoriteBookMain(@PathVariable("id") Long id, HttpSession session, Model model) {
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		Long userId = (Long) session.getAttribute("userId");
		
		Book book = bookServ.findBookById(id);
		User user = userServ.findById(userId);
		
		user.getFavorites().add(book);
		userServ.updateUser(user);
		
		model.addAttribute("user", userServ.findById(userId));
		
		return "redirect:/books";
		
	}
	
	@RequestMapping("/books/{id}/favorite")
	public String favoriteBook(@PathVariable("id") Long id, HttpSession session, Model model) {
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		Long userId = (Long) session.getAttribute("userId");
		
		Book book = bookServ.findBookById(id);
		User user = userServ.findById(userId);
		
		user.getFavorites().add(book);
		userServ.updateUser(user);
		
		model.addAttribute("user", userServ.findById(userId));
		
		return "redirect:/books/{id}";
		
	}
	
	@RequestMapping("/books/{id}/unfavorite")
	public String unfavoriteBook(@PathVariable("id") Long id, HttpSession session, Model model) {
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		Long userId = (Long) session.getAttribute("userId");
		
		Book book = bookServ.findBookById(id);
		User user = userServ.findById(userId);
		
		user.getFavorites().remove(book);
		userServ.updateUser(user);
		
		model.addAttribute("user", userServ.findById(userId));
		
		return "redirect:/books/{id}";
	}
	
	@GetMapping("/books/{id}")
	public String showBook(
			@PathVariable("id") Long id,
			Model model,
			HttpSession session
			) {
		
		if (session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		else {
			Book book = bookServ.findBookById(id);
			User user = book.getUser();
			Long bookUserId = user.getId();
			if (session.getAttribute("userId") != bookUserId) {				
				model.addAttribute("book", book);
				model.addAttribute("user", userServ.findById((Long) session.getAttribute("userId")));
				model.addAttribute("favoriteUsers", userServ.getFavoriteBookUsers(book));
				return "showbook.jsp";
			}
			else {
				model.addAttribute("book", book);
				model.addAttribute("user", userServ.findById((Long) session.getAttribute("userId")));
				model.addAttribute("favoriteUsers", userServ.getFavoriteBookUsers(book));
				return "showbookuseredit.jsp";
			}	
		}	
	}
	
	@PutMapping("/books/{id}/edit")
	public String editBookPut(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute("book") Book book,
			BindingResult result,
			Model model,
			HttpSession session
			) {
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		if (result.hasErrors()) {
			model.addAttribute("book", book);
			model.addAttribute("user", userServ.findById((Long) session.getAttribute("userId")));
			model.addAttribute("favoriteUsers", userServ.getFavoriteBookUsers(book));
			return "showbookuseredit.jsp";
		}
		else {
			Book thisBook = bookServ.findBookById(id);
			book.setUsers(thisBook.getUsers());
			bookServ.updateBook(book);
			return "redirect:/books";
		}
	}
	
	@RequestMapping("/books/{id}/delete")
	public String deleteBook(@PathVariable("id") Long id, HttpSession session) {
		 
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
    	 
    	bookServ.deleteBook(bookServ.findBookById(id));
    	 
    	return "redirect:/books";
	}
	
}