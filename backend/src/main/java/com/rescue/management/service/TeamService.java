package com.rescue.management.service;

import com.rescue.management.model.RescueTeam;
import com.rescue.management.repository.RescueTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final RescueTeamRepository rescueTeamRepository;

    public RescueTeam createTeam(RescueTeam team) {
        team.setStatus(RescueTeam.TeamStatus.AVAILABLE);
        return rescueTeamRepository.save(team);
    }

    public List<RescueTeam> getAllTeams() {
        return rescueTeamRepository.findAll();
    }

    public Optional<RescueTeam> getTeamById(String id) {
        return rescueTeamRepository.findById(id);
    }

    public List<RescueTeam> getAvailableTeams() {
        return rescueTeamRepository.findByStatus(RescueTeam.TeamStatus.AVAILABLE);
    }

    public List<RescueTeam> getTeamsOnMission() {
        return rescueTeamRepository.findByStatus(RescueTeam.TeamStatus.ON_MISSION);
    }

    public RescueTeam updateTeam(String id, RescueTeam updatedTeam) {
        return rescueTeamRepository.findById(id).map(team -> {
            if (updatedTeam.getName() != null) team.setName(updatedTeam.getName());
            if (updatedTeam.getSpecialization() != null) team.setSpecialization(updatedTeam.getSpecialization());
            if (updatedTeam.getMembers() != null) team.setMembers(updatedTeam.getMembers());
            if (updatedTeam.getContact() != null) team.setContact(updatedTeam.getContact());
            if (updatedTeam.getStatus() != null) team.setStatus(updatedTeam.getStatus());
            return rescueTeamRepository.save(team);
        }).orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
    }

    public RescueTeam updateTeamStatus(String id, RescueTeam.TeamStatus status) {
        return rescueTeamRepository.findById(id).map(team -> {
            team.setStatus(status);
            if (status == RescueTeam.TeamStatus.AVAILABLE) {
                team.setCurrentEmergencyId(null);
            }
            return rescueTeamRepository.save(team);
        }).orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
    }

    public void deleteTeam(String id) {
        rescueTeamRepository.deleteById(id);
    }
}
