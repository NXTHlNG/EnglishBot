package ru.nxthing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nxthing.repository.entities.BotUser;

import java.util.List;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    List<BotUser> findAllByActiveTrue();
}
