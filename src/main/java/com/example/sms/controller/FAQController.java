package com.example.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.sms.model.FAQSummary;
import com.example.sms.service.FAQService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/faq")
public class FAQController {

    @Autowired
    private FAQService faqService;

    @GetMapping("/all")
    public List<FAQSummary> getAllFAQs() {
        return faqService.findAllBy();
    }

    @GetMapping("/{id}/answer")
    public String getAnswerById(@PathVariable Long id) {
        String ans = faqService.getAnswerById(id);
        if(ans == null) {
            return "Answer not found";
        }
        return ans;
    }
}
