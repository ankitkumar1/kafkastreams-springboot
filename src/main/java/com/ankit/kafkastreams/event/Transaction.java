package com.ankit.kafkastreams.event;

public record Transaction(String transactionId,
                          String userId,
                          double amount,
                          String timestamp) {
}
