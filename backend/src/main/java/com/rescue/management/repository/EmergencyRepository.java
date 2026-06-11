package com.rescue.management.repository;

import com.rescue.management.model.Emergency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyRepository extends MongoRepository<Emergency, String> {

    List<Emergency> findByStatus(Emergency.Status status);

    List<Emergency> findByAssignedTeamId(String teamId);

    List<Emergency> findByDisasterType(Emergency.DisasterType disasterType);

    List<Emergency> findBySeverity(Emergency.Severity severity);

    List<Emergency> findByStatusNot(Emergency.Status status);

    long countByStatus(Emergency.Status status);

    List<Emergency> findAllByOrderByTimestampDesc();
}
