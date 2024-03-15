package com.example.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.model.FAQ;
import com.example.sms.model.FAQSummary;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

    List<FAQSummary> findAllBy();

    @Query("SELECT f.answer FROM FAQ f WHERE f.id = :id")
    String findAnswerById(@Param("id") Long id);

}