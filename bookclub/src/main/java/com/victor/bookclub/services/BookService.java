package com.victor.bookclub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.bookclub.models.Book;
import com.victor.bookclub.repositories.BookRepo;

@Service
public class BookService {

	@Autowired
	private BookRepo bookRepo;
	
	public BookService(BookRepo bookRepo ) {
		this.bookRepo = bookRepo;
	}
	
	public List<Book> allBooks() {
		return bookRepo.findAll();
	}
	
	public Book findBookById(Long id) {
		
		Optional<Book> optionalBook = bookRepo.findById(id);
		
		if (optionalBook.isPresent()) {
			return optionalBook.get();
		}
		else {
			return null;
		}
		
	}
	
	public Book createBook(Book book) {
		return bookRepo.save(book);
	}
	
	public Book updateBook(Book book) {
		return bookRepo.save(book);
	}
	
	public void deleteBook(Book book) {
		bookRepo.delete(book);
		
	}
	
}
