package com.example.lmssaraswaticollege.issue;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "issue")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Issue {

    @MongoId(targetType = FieldType.STRING)
    private String acId;

    private String issueDate;
    private String returnDate;

    public Issue(String acId) {
        this.acId = acId;
    }
}
