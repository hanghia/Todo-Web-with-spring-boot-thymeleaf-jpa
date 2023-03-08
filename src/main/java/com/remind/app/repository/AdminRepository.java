package com.remind.app.repository;

import com.remind.app.model.Admin;
import com.remind.app.model.Client;
import com.remind.app.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("select u.detail, u.title from ToDo u where u.client = ?1")
    List<ToDo> viewById(int id);

    @Query("select a from Admin a where a.username = ?1")
    Admin findByAdName(String username);
}
