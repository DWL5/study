package com.example.springbatchbasic.job.file_read_write.mapper;

import com.example.springbatchbasic.job.file_read_write.dto.Player;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PlayerFieldSetMapper implements FieldSetMapper<Player> {
    @Override
    public Player mapFieldSet(FieldSet fieldSet) throws BindException {
        return Player.builder()
                .ID(fieldSet.readRawString(0))
                .lastName(fieldSet.readRawString(1))
                .firstName(fieldSet.readRawString(2))
                .position(fieldSet.readString(3))
                .birthYear(fieldSet.readInt(4))
                .debutYear(fieldSet.readInt(5))
                .build();
    }
}
