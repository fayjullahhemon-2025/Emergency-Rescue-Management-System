package com.rescue.management.service;

import com.rescue.management.model.RescueTeam;
import com.rescue.management.model.TeamRequest;
import com.rescue.management.model.User;
import com.rescue.management.repository.RescueTeamRepository;
import com.rescue.management.repository.TeamRequestRepository;
import com.rescue.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamRequestService {

    private final TeamRequestRepository teamRequestRepository;
    private final RescueTeamRepository rescueTeamRepository;
    private final UserRepository userRepository;

    public TeamRequest createTeamRequest(TeamRequest request) {
        request.setStatus(TeamRequest.Status.PENDING);
        request.setTimestamp(LocalDateTime.now());
        return teamRequestRepository.save(request);
    }

    public List<TeamRequest> getAllRequests() {
        return teamRequestRepository.findAll();
    }

    public TeamRequest updateRequestStatus(String id, TeamRequest.Status status) {
        TeamRequest request = teamRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team request not found: " + id));

        request.setStatus(status);

        if (status == TeamRequest.Status.APPROVED) {
            // Create new RescueTeam
            RescueTeam team = new RescueTeam();
            team.setName(request.getTeamName());
            team.setSpecialization(request.getSpecialization());
            team.setContact(request.getContact());
            team.setStatus(RescueTeam.TeamStatus.AVAILABLE);

            List<String> members = new ArrayList<>();
            members.add(request.getLeaderName());
            team.setMembers(members);

            RescueTeam savedTeam = rescueTeamRepository.save(team);

            // Promote leader's user role and link teamId
            userRepository.findById(request.getLeaderUserId()).ifPresent(user -> {
                user.setRole(User.Role.RESCUE_TEAM);
                user.setTeamId(savedTeam.getId());
                userRepository.save(user);
            });
        }

        return teamRequestRepository.save(request);
    }
}
