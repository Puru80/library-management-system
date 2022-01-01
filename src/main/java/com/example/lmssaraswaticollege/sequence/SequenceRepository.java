package com.example.lmssaraswaticollege.sequence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends MongoRepository<Sequence, Long> {

    Sequence getById(Long id);
}
