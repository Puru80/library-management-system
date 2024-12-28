package com.example.lmssaraswaticollege.user;

import com.example.lmssaraswaticollege.sequence.Sequence;
import com.example.lmssaraswaticollege.sequence.SequenceRepository;
import com.example.lmssaraswaticollege.sequence.SequenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class UserService implements Serializable {

    private final UserRepository userRepository;
    private final SequenceService sequenceService;

    public boolean saveUser(User user){
        AtomicBoolean success = new AtomicBoolean(false);

        userRepository.getUserByUserName(user.getUserName()).ifPresentOrElse(
                s -> success.set(true),
                () -> success.set(false)
        );

        if(!success.get()) {
            Long nextSeq = sequenceService.getNextSequenceValue();

            user.setId(nextSeq);

            userRepository.insert(user);
        }

        return !success.get();
    }

    public boolean login(User user){
        final boolean[] success = new boolean[1];

        Optional<User> userOptional = userRepository.getUserByUserName(user.getUserName());

        userOptional.ifPresentOrElse(
                s -> success[0] = true,
                () -> success[0] = false
        );

        if(success[0]){
            return userOptional.get().getPassword().equals(user.getPassword());
        }

        return false;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


}
