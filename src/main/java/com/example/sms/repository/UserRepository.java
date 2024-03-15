package com.example.sms.repository;

import com.example.sms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByOrgId(Integer orgId);
    
    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findByActive(boolean active);

    List<User> findByOnline(boolean online);

    User findByEmailId(String emailid);
    
    @Query("SELECT u FROM User u WHERE u.online = true AND u.emailId <> ?1 AND u.orgId = (SELECT t.orgId FROM User t WHERE t.emailId = ?1)")
    List<User> findUsersByOrgIdForEmail(String emailId);
    
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.online = ?2 WHERE u.emailId = ?1")
    void updateOnlineStatusByEmail(String emailId, boolean online);
    
}