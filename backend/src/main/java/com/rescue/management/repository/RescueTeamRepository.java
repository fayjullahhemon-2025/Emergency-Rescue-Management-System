package com.rescue.management.repository;

import com.rescue.management.model.RescueTeam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RescueTeamRepository extends MongoRepository<RescueTeam, String> {

    List<RescueTeam> findByStatus(RescueTeam.TeamStatus status);

    List<RescueTeam> findBySpecialization(String specialization);

    long countByStatus(RescueTeam.TeamStatus status);
}
