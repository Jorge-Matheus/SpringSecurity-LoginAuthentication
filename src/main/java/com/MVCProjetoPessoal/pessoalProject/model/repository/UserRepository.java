package com.MVCProjetoPessoal.pessoalProject.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MVCProjetoPessoal.pessoalProject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	Optional<User> findByPassword(String password);
}
