package com.rescue.management.repository;

import com.rescue.management.model.JoinRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JoinRequestRepository extends MongoRepository<JoinRequest, String> {
    List<JoinRequest> findByTeamId(String teamId);
    List<JoinRequest> findByUserId(String userId);
}
