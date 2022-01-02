package com.example.lmssaraswaticollege.books;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private MongoTemplate mongoTemplate;

    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }

    public boolean addBook(Books book){
        boolean succ = bookRepository.findById(book.getAccNo()).isPresent();

        if(succ)
            return false;
        else{
            bookRepository.insert(book);
            return true;
        }
    }

    public List<Books> getIssuedBooks(){
        Query query = new Query();
        query.addCriteria(Criteria.where("issued").is(true));

        return mongoTemplate.find(query, Books.class);
    }
}
