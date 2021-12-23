package ru.nxthing.service;

import org.springframework.stereotype.Service;
import ru.nxthing.repository.BotUserRepository;
import ru.nxthing.repository.entities.BotUser;

import java.util.Optional;

@Service
public class BotUserService {
    private BotUserRepository botUserRepository;

    public BotUserService(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    public void save(BotUser user) {
        botUserRepository.save(user);
    }

    public Optional<BotUser> findByChatId(long chatId) {
        return botUserRepository.findById(chatId);
    }
}
