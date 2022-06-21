package com.victor.bookclub.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.victor.bookclub.models.Book;

public interface BookRepo extends CrudRepository<Book, Long> {
	
	List<Book> findAll();
	List<Book> findByUserIdIs(Long userId);
	
	
}
