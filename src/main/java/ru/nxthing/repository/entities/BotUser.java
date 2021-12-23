package ru.nxthing.repository.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "telegram_bot_user")
public class BotUser {
    @Id
    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "active")
    private boolean active;
}
