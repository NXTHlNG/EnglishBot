package ru.nxthing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nxthing.repository.entities.BotUser;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {

}
