package com.ankit.kafkastreams.controller;

import com.ankit.kafkastreams.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public String sendTransaction() {
        return this.transactionService.sendTransaction();
    }

    @GetMapping("/test")
    public String test(){
        return "UP!";
    }
}
