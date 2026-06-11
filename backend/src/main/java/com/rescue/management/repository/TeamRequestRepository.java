package com.rescue.management.repository;

import com.rescue.management.model.TeamRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TeamRequestRepository extends MongoRepository<TeamRequest, String> {
    List<TeamRequest> findByLeaderUserId(String leaderUserId);
    List<TeamRequest> findByStatus(TeamRequest.Status status);
}
