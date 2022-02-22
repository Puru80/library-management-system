package com.example.lmssaraswaticollege.issue;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {
    Optional<Issue> findByAcIdAndDepartment(String acId, String dept);
}

