package com.example.lmssaraswaticollege.user;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @MongoId(targetType = FieldType.INT64)
    private Long id;

    @Indexed(unique = true)
    private String userName;
    private String password;
    private Role role;

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
