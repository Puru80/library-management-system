package com.example.lmssaraswaticollege.sequence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SequenceService {

    private final SequenceRepository sequenceRepository;

    private void initSequence(){
        Sequence seq = new Sequence(1L, 0L);
        sequenceRepository.save(seq);
    }

    public Long getNextSequenceValue(){
        Sequence seq = sequenceRepository.getById(1L);

        if (seq == null) {
            initSequence();
            seq = sequenceRepository.getById(1L);
        }

        Long count = seq.getSequenceValue();

        seq.setSequenceValue(++count);
        sequenceRepository.save(seq);

        return count;
    }
}
