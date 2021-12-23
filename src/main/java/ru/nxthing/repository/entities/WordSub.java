package ru.nxthing.repository.entities;

import lombok.Data;

@Data
public class WordSub {
    private int id;
    private Word word;

    private int iteration;

}
