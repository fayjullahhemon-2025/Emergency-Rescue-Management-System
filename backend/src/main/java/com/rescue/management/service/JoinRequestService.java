package com.rescue.management.service;

import com.rescue.management.model.JoinRequest;
import com.rescue.management.model.RescueTeam;
import com.rescue.management.model.User;
import com.rescue.management.repository.JoinRequestRepository;
import com.rescue.management.repository.RescueTeamRepository;
import com.rescue.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;
    private final RescueTeamRepository rescueTeamRepository;
    private final UserRepository userRepository;

    public JoinRequest createJoinRequest(JoinRequest request) {
        request.setStatus(JoinRequest.Status.PENDING);
        request.setTimestamp(LocalDateTime.now());
        return joinRequestRepository.save(request);
    }

    public List<JoinRequest> getRequestsByTeam(String teamId) {
        return joinRequestRepository.findByTeamId(teamId);
    }

    public List<JoinRequest> getRequestsByUser(String userId) {
        return joinRequestRepository.findByUserId(userId);
    }

    public JoinRequest updateRequestStatus(String id, JoinRequest.Status status) {
        JoinRequest request = joinRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Join request not found: " + id));

        request.setStatus(status);

        if (status == JoinRequest.Status.APPROVED) {
            // Add user to the team's members list
            RescueTeam team = rescueTeamRepository.findById(request.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Rescue team not found: " + request.getTeamId()));

            List<String> members = team.getMembers();
            if (members == null) {
                members = new ArrayList<>();
            }
            if (!members.contains(request.getUserName())) {
                members.add(request.getUserName());
                team.setMembers(members);
                rescueTeamRepository.save(team);
            }

            // Update user role to RESCUE_TEAM and link their teamId
            userRepository.findById(request.getUserId()).ifPresent(user -> {
                user.setRole(User.Role.RESCUE_TEAM);
                user.setTeamId(request.getTeamId());
                userRepository.save(user);
            });
        }

        return joinRequestRepository.save(request);
    }
}
