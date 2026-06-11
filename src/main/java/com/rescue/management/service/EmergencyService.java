package com.rescue.management.service;

import com.rescue.management.model.Emergency;
import com.rescue.management.model.RescueTeam;
import com.rescue.management.repository.EmergencyRepository;
import com.rescue.management.repository.RescueTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    private final RescueTeamRepository rescueTeamRepository;

    public Emergency reportEmergency(Emergency emergency) {
        emergency.setStatus(Emergency.Status.PENDING);
        emergency.setTimestamp(LocalDateTime.now());
        emergency.setUpdatedAt(LocalDateTime.now());
        return emergencyRepository.save(emergency);
    }

    public List<Emergency> getAllEmergencies() {
        return emergencyRepository.findAllByOrderByTimestampDesc();
    }

    public Optional<Emergency> getEmergencyById(String id) {
        return emergencyRepository.findById(id);
    }

    public List<Emergency> getEmergenciesByStatus(Emergency.Status status) {
        return emergencyRepository.findByStatus(status);
    }

    public List<Emergency> getActiveEmergencies() {
        return emergencyRepository.findByStatusNot(Emergency.Status.COMPLETED);
    }

    public List<Emergency> getEmergenciesByTeam(String teamId) {
        return emergencyRepository.findByAssignedTeamId(teamId);
    }

    public Emergency assignTeam(String emergencyId, String teamId) {
        Emergency emergency = emergencyRepository.findById(emergencyId)
                .orElseThrow(() -> new RuntimeException("Emergency not found: " + emergencyId));

        RescueTeam team = rescueTeamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found: " + teamId));

        // Update emergency
        emergency.setAssignedTeamId(teamId);
        emergency.setAssignedTeamName(team.getName());
        emergency.setStatus(Emergency.Status.IN_PROGRESS);
        emergency.setUpdatedAt(LocalDateTime.now());

        // Update team status
        team.setStatus(RescueTeam.TeamStatus.ON_MISSION);
        team.setCurrentEmergencyId(emergencyId);
        rescueTeamRepository.save(team);

        return emergencyRepository.save(emergency);
    }

    public Emergency updateStatus(String emergencyId, Emergency.Status newStatus) {
        Emergency emergency = emergencyRepository.findById(emergencyId)
                .orElseThrow(() -> new RuntimeException("Emergency not found: " + emergencyId));

        emergency.setStatus(newStatus);
        emergency.setUpdatedAt(LocalDateTime.now());

        // If completed, free the assigned team
        if (newStatus == Emergency.Status.COMPLETED && emergency.getAssignedTeamId() != null) {
            rescueTeamRepository.findById(emergency.getAssignedTeamId()).ifPresent(team -> {
                team.setStatus(RescueTeam.TeamStatus.AVAILABLE);
                team.setCurrentEmergencyId(null);
                rescueTeamRepository.save(team);
            });
        }

        return emergencyRepository.save(emergency);
    }

    public void deleteEmergency(String id) {
        emergencyRepository.deleteById(id);
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", emergencyRepository.count());
        stats.put("pending", emergencyRepository.countByStatus(Emergency.Status.PENDING));
        stats.put("inProgress", emergencyRepository.countByStatus(Emergency.Status.IN_PROGRESS));
        stats.put("completed", emergencyRepository.countByStatus(Emergency.Status.COMPLETED));
        stats.put("teamsAvailable", rescueTeamRepository.countByStatus(RescueTeam.TeamStatus.AVAILABLE));
        stats.put("teamsOnMission", rescueTeamRepository.countByStatus(RescueTeam.TeamStatus.ON_MISSION));
        return stats;
    }
}
