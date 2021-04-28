package com.instructorrob.bonusdemo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.instructorrob.bonusdemo.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);

}