package com.example.lmssaraswaticollege.books;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Books {

    @Field(name = "accNo")
    @MongoId(targetType = FieldType.STRING)
    private String accNo;

    private String bookName;
    private String authorName;
    private String yearOfPub;
    @Indexed(unique = true)
    private String abscissionNo;
    private int noOfPages;
    private String language;
    private Double price;
    private boolean issued;

    public Books(String accNo, String bookName, String authorName, String yearOfPub, String abscissionNo, int noOfPages, String language, Double price) {
        this.accNo = accNo;
        this.bookName = bookName;
        this.authorName = authorName;
        this.yearOfPub = yearOfPub;
        this.abscissionNo = abscissionNo;
        this.noOfPages = noOfPages;
        this.language = language;
        this.price = price;
    }
}
