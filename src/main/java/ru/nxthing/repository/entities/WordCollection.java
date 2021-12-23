package ru.nxthing.repository.entities;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "collection")
public class WordCollection {
    @Id
    @Column(name = "collection_id")
    private long id;

    @Column(name = "popularity")
    private int popularity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "collection_word",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"))
    public Set<Word> words = new LinkedHashSet<>();

}
