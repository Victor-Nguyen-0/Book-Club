package com.victor.bookclub.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.victor.bookclub.models.Book;
import com.victor.bookclub.models.User;

public interface UserRepo extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);
	List<User> findAll();
	List<User> findAllByFavorites(Book book);
	
}

