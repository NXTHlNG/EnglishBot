package ru.nxthing.repository.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "telegram_bot_user")
public class BotUser {
    @Id
    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bot_user_collection",
            joinColumns = @JoinColumn(name = "bot_user_id"),
            inverseJoinColumns = @JoinColumn(name = "collection_id"))
    private Set<WordCollection> subscribedCollections = new LinkedHashSet<>();
}
