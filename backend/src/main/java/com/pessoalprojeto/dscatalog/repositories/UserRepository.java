package com.pessoalprojeto.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoalprojeto.dscatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
