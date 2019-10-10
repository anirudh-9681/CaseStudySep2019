package com.beehyv.case_study.repositories;

import com.beehyv.case_study.entities.MyUserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserCredentialsRepo extends JpaRepository<MyUserCredentials, Long> {
    Optional<MyUserCredentials> getByEmail(String email);
    Boolean existsByEmail(String email);
}
