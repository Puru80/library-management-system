package com.example.lmssaraswaticollege.sequence;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "sequence")
@AllArgsConstructor
public class Sequence {

    @MongoId(targetType = FieldType.INT64)
    private Long id;

    private Long sequenceValue;
}
