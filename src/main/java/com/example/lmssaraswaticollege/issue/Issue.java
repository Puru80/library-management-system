package com.example.lmssaraswaticollege.issue;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "issue")
@CompoundIndexes(
        @CompoundIndex(name = "book_issue", def = "{'acId' : 1, 'department': 1}", unique = true)
)
@AllArgsConstructor
@NoArgsConstructor
public class Issue {

    private String acId;
    private String department;

    private String issueDate;
    private String returnDate;

    public Issue(String acId, String department) {
        this.acId = acId;
        this.department = department;
    }
}
