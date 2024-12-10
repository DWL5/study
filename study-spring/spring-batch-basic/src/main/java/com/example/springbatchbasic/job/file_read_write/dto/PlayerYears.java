package com.example.springbatchbasic.job.file_read_write.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Year;

@AllArgsConstructor
@Data
@Builder
public class PlayerYears implements Serializable {

    private String ID;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;
    private int experienceYears;

    public static PlayerYears of(Player player) {
        return PlayerYears.builder()
                .ID(player.getID())
                .lastName(player.getLastName())
                .firstName(player.getLastName())
                .position(player.getPosition())
                .birthYear(player.getBirthYear())
                .debutYear(player.getDebutYear())
                .experienceYears(Year.now().getValue() - player.getDebutYear())
                .build();
    }
}