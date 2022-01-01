package com.example.lmssaraswaticollege;

import com.example.lmssaraswaticollege.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BackendAccess {

    private final UserService userService;

}
