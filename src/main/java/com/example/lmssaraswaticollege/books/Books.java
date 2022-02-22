package com.example.lmssaraswaticollege.books;

import com.mongodb.lang.Nullable;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "books")
@CompoundIndexes(
        @CompoundIndex(name = "book_unique", def = "{'accNo' : 1, 'department': 1}", unique = true)
)
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    //@Field(name = "accNo")
    //@MongoId(targetType = FieldType.STRING)

    @Field(name = "accNo")
    private String accNo;

    private String bookName;
    private String department;
    private String authorName;
    private String publisher;
    private String yearOfPub;
    private String noOfPages;
    private String language;
    private String price;
    private boolean issued;

    /*public Books(String accNo, String bookName, String department, String authorName, String yearOfPub, int noOfPages,
                 String language, Double price, boolean issued) {
        this.accNo = accNo;
        this.bookName = bookName;
        this.department = department;
        this.authorName = authorName;
        this.yearOfPub = yearOfPub;
        this.noOfPages = noOfPages;
        this.language = language;
        this.price = price;
        this.issued = issued;
    }*/
}
