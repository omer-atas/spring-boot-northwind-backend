package com.turkcell.northwind.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.northwind.entities.concretes.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer>{

	UserEntity findByEmail(String email);
}
