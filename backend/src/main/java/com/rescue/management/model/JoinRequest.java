package com.rescue.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "join_requests")
public class JoinRequest {
    @Id
    private String id;
    private String userId;
    private String userName;
    private String userEmail;
    private String teamId;
    private String teamName;
    private String cvData; // CV/Experience details in text form
    private Status status = Status.PENDING;
    private LocalDateTime timestamp = LocalDateTime.now();

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}
