package com.rescue.management.controller;

import com.rescue.management.model.TeamRequest;
import com.rescue.management.service.TeamRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/team-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeamRequestController {

    private final TeamRequestService teamRequestService;

    // POST /api/team-requests — Submit team creation request
    @PostMapping
    public ResponseEntity<TeamRequest> createTeamRequest(@RequestBody TeamRequest request) {
        return ResponseEntity.ok(teamRequestService.createTeamRequest(request));
    }

    // GET /api/team-requests — Fetch all team creation requests (Admin view)
    @GetMapping
    public ResponseEntity<List<TeamRequest>> getAllRequests() {
        return ResponseEntity.ok(teamRequestService.getAllRequests());
    }

    // PUT /api/team-requests/{id}/status — Update request status
    @PutMapping("/{id}/status")
    public ResponseEntity<TeamRequest> updateRequestStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        TeamRequest.Status status = TeamRequest.Status.valueOf(body.get("status"));
        return ResponseEntity.ok(teamRequestService.updateRequestStatus(id, status));
    }
}
