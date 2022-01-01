package com.example.lmssaraswaticollege.issue;

import com.example.lmssaraswaticollege.books.BookRepository;
import com.example.lmssaraswaticollege.books.Books;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
@AllArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;
    private final MongoTemplate mongoTemplate;

    public boolean issueBook(Issue issue){
        boolean succ = issueRepository.findById(issue.getAcId()).isPresent();

        if(succ)
            return false;
        else{
            String issueDate = LocalDate.now(ZoneId.of("Asia/Kolkata")).
                    format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
            String returnDate = LocalDate.now(ZoneId.of("Asia/Kolkata")).plusDays(7).
                    format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

            issue.setIssueDate(issueDate);
            issue.setReturnDate(returnDate);

            /*
            Query query = new Query();
            query.addCriteria(Criteria.where("accNo").is(issue.getAcId()));

            List<Books> list = mongoTemplate.find(query, Books.class);

             */

            Books book = mongoTemplate.findById(issue.getAcId(), Books.class);

            if(book==null)
                return false;

            book.setIssued(true);
            bookRepository.save(book);
            issueRepository.insert(issue);

            return true;
        }
    }
}
