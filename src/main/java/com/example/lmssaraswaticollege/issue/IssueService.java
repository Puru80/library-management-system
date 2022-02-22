package com.example.lmssaraswaticollege.issue;

import com.example.lmssaraswaticollege.books.BookRepository;
import com.example.lmssaraswaticollege.books.Books;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
        boolean succ = issueRepository.findByAcIdAndDepartment(issue.getAcId(), issue.getDepartment()).isPresent();

        if(succ)
            return false;
        else{
            String issueDate = LocalDate.now(ZoneId.of("Asia/Kolkata")).
                    format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
            String returnDate = LocalDate.now(ZoneId.of("Asia/Kolkata")).plusDays(7).
                    format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

            issue.setIssueDate(issueDate);
            issue.setReturnDate(returnDate);

            Query query = new Query();
            query.addCriteria(Criteria.where("accNo").is(issue.getAcId()).and("department").
                    is(issue.getDepartment()));
            Update update = new Update();
            update.set("issued", true);
            mongoTemplate.upsert(query, update, Books.class);

            issueRepository.insert(issue);

            return true;
        }
    }

    public boolean returnBook(String acID, String department){
        boolean isPresent = issueRepository.findByAcIdAndDepartment(acID, department).isPresent();

        if(isPresent){
            Query query = new Query();
            query.addCriteria(Criteria.where("acId").is(acID).and("department").is(department));
            mongoTemplate.remove(query, Issue.class, "issue");

            return true;
        }

        return false;
    }
}
