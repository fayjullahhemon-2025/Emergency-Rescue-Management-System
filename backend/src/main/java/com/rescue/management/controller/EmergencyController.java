package com.rescue.management.controller;

import com.rescue.management.model.Emergency;
import com.rescue.management.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emergencies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmergencyController {

    private final EmergencyService emergencyService;

    // POST /api/emergencies — Report a new emergency
    @PostMapping
    public ResponseEntity<Emergency> reportEmergency(@RequestBody Emergency emergency) {
        return ResponseEntity.ok(emergencyService.reportEmergency(emergency));
    }

    // GET /api/emergencies — Fetch all emergencies (admin view)
    @GetMapping
    public ResponseEntity<List<Emergency>> getAllEmergencies() {
        return ResponseEntity.ok(emergencyService.getAllEmergencies());
    }

    // GET /api/emergencies/active — Fetch active emergencies
    @GetMapping("/active")
    public ResponseEntity<List<Emergency>> getActiveEmergencies() {
        return ResponseEntity.ok(emergencyService.getActiveEmergencies());
    }

    // GET /api/emergencies/stats — Get statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(emergencyService.getStatistics());
    }

    // GET /api/emergencies/{id} — Fetch single emergency
    @GetMapping("/{id}")
    public ResponseEntity<Emergency> getEmergencyById(@PathVariable String id) {
        return emergencyService.getEmergencyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/emergencies/status/{status} — Filter by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Emergency>> getByStatus(@PathVariable Emergency.Status status) {
        return ResponseEntity.ok(emergencyService.getEmergenciesByStatus(status));
    }

    // GET /api/emergencies/team/{teamId} — Get emergencies for a team
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Emergency>> getByTeam(@PathVariable String teamId) {
        return ResponseEntity.ok(emergencyService.getEmergenciesByTeam(teamId));
    }

    // PUT /api/emergencies/{id}/assign — Assign a team to an emergency
    @PutMapping("/{id}/assign")
    public ResponseEntity<Emergency> assignTeam(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        String teamId = body.get("teamId");
        return ResponseEntity.ok(emergencyService.assignTeam(id, teamId));
    }

    // PATCH /api/emergencies/{id}/status — Update emergency status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Emergency> updateStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        Emergency.Status status = Emergency.Status.valueOf(body.get("status"));
        return ResponseEntity.ok(emergencyService.updateStatus(id, status));
    }

    // DELETE /api/emergencies/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmergency(@PathVariable String id) {
        emergencyService.deleteEmergency(id);
        return ResponseEntity.ok().build();
    }
}
