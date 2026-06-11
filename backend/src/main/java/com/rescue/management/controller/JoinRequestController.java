package com.rescue.management.controller;

import com.rescue.management.model.JoinRequest;
import com.rescue.management.service.JoinRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/join-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    // POST /api/join-requests — Submit join request
    @PostMapping
    public ResponseEntity<JoinRequest> createJoinRequest(@RequestBody JoinRequest request) {
        return ResponseEntity.ok(joinRequestService.createJoinRequest(request));
    }

    // GET /api/join-requests/team/{teamId} — View requests for a team (Team Leader view)
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<JoinRequest>> getRequestsByTeam(@PathVariable String teamId) {
        return ResponseEntity.ok(joinRequestService.getRequestsByTeam(teamId));
    }

    // GET /api/join-requests/user/{userId} — View requests submitted by a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JoinRequest>> getRequestsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(joinRequestService.getRequestsByUser(userId));
    }

    // PUT /api/join-requests/{id}/status — Update request status
    @PutMapping("/{id}/status")
    public ResponseEntity<JoinRequest> updateRequestStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        JoinRequest.Status status = JoinRequest.Status.valueOf(body.get("status"));
        return ResponseEntity.ok(joinRequestService.updateRequestStatus(id, status));
    }
}
