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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "translation",
            joinColumns = @JoinColumn(name = "word_id_1"),
            inverseJoinColumns = @JoinColumn(name = "word_id_2"))
    private Set<Word> translations = new LinkedHashSet<>();

    public Set<Word> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Word> translations) {
        this.translations = translations;
    }
}
