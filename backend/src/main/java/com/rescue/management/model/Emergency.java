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
@Document(collection = "emergencies")
public class Emergency {

    @Id
    private String id;

    private String reporterName;
    private String reporterContact;

    private DisasterType disasterType;
    private Location location;
    private Severity severity;
    private String description;
    private String audioData; // Base64 audio data or S3 URL

    private Status status = Status.PENDING;
    private String assignedTeamId;
    private String assignedTeamName;

    private LocalDateTime timestamp = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum DisasterType {
        FLOOD,
        EARTHQUAKE,
        FIRE,
        STORM,
        LANDSLIDE,
        ACCIDENT,
        BUILDING_COLLAPSE,
        OTHER
    }

    public enum Severity {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        private String address;
        private double lat;
        private double lng;
    }
}
