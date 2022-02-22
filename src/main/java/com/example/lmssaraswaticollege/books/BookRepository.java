package com.example.lmssaraswaticollege.books;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Books, String> {

    Optional<Books> findByAccNoAndDepartment(String accNo, String department);
}
