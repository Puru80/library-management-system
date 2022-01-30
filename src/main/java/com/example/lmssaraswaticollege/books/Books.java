package com.example.lmssaraswaticollege.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    @Field(name = "accNo")
    @MongoId(targetType = FieldType.STRING)
    private String accNo;

    private String bookName;
    private String authorName;
    private String yearOfPub;
    private int noOfPages;
    private String language;
    private Double price;
    private boolean issued;

    public Books(String accNo, String bookName, String authorName, String yearOfPub,
                 int noOfPages, String language, Double price) {
        this.accNo = accNo;
        this.bookName = bookName;
        this.authorName = authorName;
        this.yearOfPub = yearOfPub;
        this.noOfPages = noOfPages;
        this.language = language;
        this.price = price;
    }

    public Books(String bookName, String authorName, String yearOfPub,
                 int noOfPages, String language, Double price, boolean issued) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.yearOfPub = yearOfPub;
        this.noOfPages = noOfPages;
        this.language = language;
        this.price = price;
        this.issued = issued;
    }
}
