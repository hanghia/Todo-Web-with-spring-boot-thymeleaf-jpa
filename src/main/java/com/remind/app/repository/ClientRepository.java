package com.remind.app.repository;

import com.remind.app.model.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {

    //tìm kiếm bằng tên người dùng
    @Query("select c from Client c where c.clientName = ?1")
    Client findByClientName(String clientName);


    // tìm kiếm bằng id
    @Query("select c.clientId from Client c where c.clientId = ?1")
    boolean findClientByClientId(int clientId);


    // lưu người dùng
    @Modifying
    @Query(value = "insert into client(client_name, full_name, password) values(:clientName, :fullName, :password)", nativeQuery = true)
    @Transactional
    void saveUser(@Param("clientName") String clientName, @Param("fullName") String fullName, @Param("password") String password);

}
