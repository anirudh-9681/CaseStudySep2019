package com.beehyv.case_study.repositories;

import com.beehyv.case_study.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepo extends JpaRepository<MyUser, Integer> {
    MyUser getByUserId(int userId);
    boolean existsByUserId(int userId);
}
