package com.example.lmssaraswaticollege.issue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "issue")
@AllArgsConstructor
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
