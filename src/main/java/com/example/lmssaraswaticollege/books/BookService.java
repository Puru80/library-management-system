package com.example.lmssaraswaticollege.books;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private MongoTemplate mongoTemplate;

    public Books getBookByAccNoAndDept(String accNo, String department){
        boolean succ = bookRepository.findByAccNoAndDepartment(accNo, department).isPresent();

        if(succ)
            return bookRepository.findByAccNoAndDepartment(accNo, department).get();

        return null;
    }

    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }

    public boolean addBook(Books book){
        boolean succ = bookRepository.findByAccNoAndDepartment(book.getAccNo(), book.getDepartment()).isPresent();

        if(succ)
            return false;

        bookRepository.save(book);
        return true;
    }

    public boolean updateBook(Books book){
        Query query = new Query();
        query.addCriteria(Criteria.where("accNo").is(book.getAccNo()).and("department").
                is(book.getDepartment()));

        Update update = new Update();
        update.set("bookName", book.getBookName());
        update.set("authorName", book.getAuthorName());
        update.set("publisher", book.getPublisher());
        update.set("yearOfPub", book.getYearOfPub());
        update.set("noOfPages", book.getNoOfPages());
        update.set("language", book.getLanguage());
        update.set("price", book.getPrice());

        mongoTemplate.upsert(query, update, Books.class);

        return true;
    }

    public List<Books> getBooksByQuery(String field, String queryString){
        Query query;

        switch (field) {
            case "All Books":
                return bookRepository.findAll();
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

    public boolean isValidBook(Books book){
        logger.debug("Book name: {}", book.getBookName());
        logger.debug("Book AccNo: {}", book.getAccNo());

        boolean succ = StringUtils.isBlank(book.getBookName()) && "Select Department".equals(book.getAccNo());
        logger.debug("Book validation: {}", succ);

        return succ;
    }
}
