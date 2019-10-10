package com.beehyv.case_study.repositories;

import com.beehyv.case_study.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepo extends JpaRepository<MyUser, Long> {
    MyUser getByUserId(long userId);
    boolean existsByUserId(long userId);
}
