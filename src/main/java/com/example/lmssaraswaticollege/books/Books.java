package com.example.lmssaraswaticollege.books;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Books {

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

}
