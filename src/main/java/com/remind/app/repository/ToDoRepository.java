package com.remind.app.repository;

import com.remind.app.model.ToDo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    // tìm kiếm todo bằng user id
    @Query("select u from ToDo u join u.client c where c.clientId = ?1")
    List<ToDo> findByClient_Id(int id);

    //thêm mới todo bởi cliend id
    @Modifying
    @Query(value = "insert into to_do(title, detail, client_id) values (:title, :detail, :clientId)", nativeQuery = true)
    @Transactional
    void addTodoByClientId(@Param("title") String title,@Param("detail") String detail, @Param("clientId") int clientId);

    // xóa theo id
    @Modifying
    @Query(value = "delete from to_do where id = :id and client_id = :clientId", nativeQuery = true)
    @Transactional
    void deleteTodo(@Param("id") long id, @Param("clientId") int clientId);

    // xoa theo client id
    @Modifying
    @Query(value = "delete from to_do where client_id = :clientId", nativeQuery = true)
    @Transactional
    void deleteByClientId(@Param("clientId") long clientId);
}
