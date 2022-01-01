package com.example.lmssaraswaticollege.sequence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "sequence")
@AllArgsConstructor
@Getter
@Setter
public class Sequence {

    @MongoId(targetType = FieldType.INT64)
    private Long id;

    private Long sequenceValue;
}
