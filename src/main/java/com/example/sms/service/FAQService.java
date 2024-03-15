package com.example.sms.service;

import com.example.sms.dto.FAQResponseDTO;
import com.example.sms.model.FAQ;
import com.example.sms.model.FAQSummary;
import com.example.sms.repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FAQService {

    @Autowired
    private FAQRepository faqRepository;

    public List<FAQSummary> findAllBy() {
        List<FAQSummary> faqs = faqRepository.findAllBy();
        return faqs;
    }

    public Optional<FAQResponseDTO> getFAQById(Long id) {
        Optional<FAQ> faqOptional = faqRepository.findById(id);
        return faqOptional.map(this::convertToDTO);
    }

    public String getAnswerById(Long id) {
        return faqRepository.findAnswerById(id);
    }

    private FAQResponseDTO convertToDTO(FAQ faq) {
        FAQResponseDTO dto = new FAQResponseDTO();
        dto.setId(faq.getId());
        dto.setQuestion(faq.getQuestion());
        return dto;
    }
}

