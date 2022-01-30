package com.example.lmssaraswaticollege.books;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private MongoTemplate mongoTemplate;

    public Books getBookByAccNo(String accNo){
        boolean succ = bookRepository.findById(accNo).isPresent();

        if(succ)
            return bookRepository.findById(accNo).get();

        return null;
    }

    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }

    public boolean addBook(Books book){
        boolean succ = bookRepository.findById(book.getAccNo()).isPresent();

        if(succ)
            bookRepository.save(book);
        else{
            book.setIssued(false);
            bookRepository.insert(book);
        }

        return true;
    }

    public List<Books> getIssuedBooks(){
        Query query = new Query();
        query.addCriteria(Criteria.where("issued").is(true));

        return mongoTemplate.find(query, Books.class);
    }

    public List<Books> getBooksByQuery(String field, String queryString){
        Query query;

        switch (field) {
            case "All Books":
                return bookRepository.findAll();
            case "accNo":
                return List.of(Objects.requireNonNull(mongoTemplate.findById(queryString, Books.class)));
            case "issued":
                query = new Query();
                query.addCriteria(Criteria.where(field).is(true));
                return mongoTemplate.find(query, Books.class);
            case "available":
                query = new Query();
                query.addCriteria(Criteria.where("issued").is(false));
                return mongoTemplate.find(query, Books.class);
            case "noOfPages":
                query = new Query();
                query.addCriteria(Criteria.where(field).is(Integer.parseInt(queryString)));
                return mongoTemplate.find(query, Books.class);
            case "price":
                query = new Query();
                query.addCriteria(Criteria.where(field).is(Double.parseDouble(queryString)));
                return mongoTemplate.find(query, Books.class);
        }

        query = new Query();
        query.addCriteria(Criteria.where(field).is(queryString));
        return mongoTemplate.find(query, Books.class);
    }

    //TODO: Update Book Functionality
}
