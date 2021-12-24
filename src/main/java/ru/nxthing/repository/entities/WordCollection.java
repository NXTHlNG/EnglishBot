package ru.nxthing.repository.entities;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
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

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "collection_word",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"))
    public Set<Word> words = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "<b>" + name + "\n" + description + "</b>" + "\n";
    }

    public String toStringWithWords() {
        StringBuilder reply = new StringBuilder(toString());
        for (Word word : words) {
            reply.append(word.toString());
            reply.append("\n");
        }

        return reply.toString();
    }
}
