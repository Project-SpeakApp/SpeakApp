package com.speakapp.userservice.repositories;

import com.speakapp.userservice.entities.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {

  Page<AppUser> findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(String firstName, String lastName, Pageable page);
  Page<AppUser> findAllByFirstNameEqualsIgnoreCase(String firstName, Pageable page);

}
