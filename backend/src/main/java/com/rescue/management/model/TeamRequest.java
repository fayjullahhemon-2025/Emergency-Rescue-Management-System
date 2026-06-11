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
@Document(collection = "team_requests")
public class TeamRequest {
    @Id
    private String id;
    private String leaderUserId;
    private String leaderName;
    private String leaderEmail;
    private String teamName;
    private String specialization;
    private String contact;
    private String cvData; // Leader qualifications/text CV details
    private Status status = Status.PENDING;
    private LocalDateTime timestamp = LocalDateTime.now();

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}
