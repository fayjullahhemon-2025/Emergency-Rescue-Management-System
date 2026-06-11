package com.rescue.management.controller;

import com.rescue.management.model.RescueTeam;
import com.rescue.management.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeamController {

    private final TeamService teamService;

    // POST /api/teams — Create a new rescue team
    @PostMapping
    public ResponseEntity<RescueTeam> createTeam(@RequestBody RescueTeam team) {
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    // GET /api/teams — Fetch all teams
    @GetMapping
    public ResponseEntity<List<RescueTeam>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    // GET /api/teams/available — Find available teams
    @GetMapping("/available")
    public ResponseEntity<List<RescueTeam>> getAvailableTeams() {
        return ResponseEntity.ok(teamService.getAvailableTeams());
    }

    // GET /api/teams/on-mission — Find teams on mission
    @GetMapping("/on-mission")
    public ResponseEntity<List<RescueTeam>> getTeamsOnMission() {
        return ResponseEntity.ok(teamService.getTeamsOnMission());
    }

    // GET /api/teams/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RescueTeam> getTeamById(@PathVariable String id) {
        return teamService.getTeamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/teams/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RescueTeam> updateTeam(@PathVariable String id, @RequestBody RescueTeam team) {
        return ResponseEntity.ok(teamService.updateTeam(id, team));
    }

    // PATCH /api/teams/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<RescueTeam> updateTeamStatus(
            @PathVariable String id,
            @RequestBody java.util.Map<String, String> body) {
        RescueTeam.TeamStatus status = RescueTeam.TeamStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(teamService.updateTeamStatus(id, status));
    }

    // DELETE /api/teams/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
