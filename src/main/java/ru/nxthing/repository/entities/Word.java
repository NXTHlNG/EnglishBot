package ru.nxthing.repository.entities;

import lombok.Data;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "word")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id", nullable = false)
    private long id;

    @Column(name = "word_text")
    private String word;



    @ManyToMany(mappedBy = "words", fetch = FetchType.EAGER)
    private List<WordCollection> wordCollections = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "translation",
            joinColumns = @JoinColumn(name = "word_id_1"),
            inverseJoinColumns = @JoinColumn(name = "word_id_2"))
    private List<Word> translations = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder reply = new StringBuilder(word + " — ");

        int k = 0;
        for (Word translation : translations) {
            reply.append(translation.getWord());
            if (++k < translations.size()) {
                reply.append(", ");
            }
        }

        return reply.toString();
    }
}
