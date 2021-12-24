package ru.nxthing.repository.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "collection_word",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"))
    private Set<Word> words = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "subscribedCollections", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BotUser> botUserList = new LinkedList<>();

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
