package com.rescue.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rescue_teams")
public class RescueTeam {

    @Id
    private String id;

    private String name;
    private String specialization;
    private TeamStatus status = TeamStatus.AVAILABLE;
    private List<String> members;
    private String contact;
    private String currentEmergencyId;

    public enum TeamStatus {
        AVAILABLE,
        ON_MISSION
    }
}
