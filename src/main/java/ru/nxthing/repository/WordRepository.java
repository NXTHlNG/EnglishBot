package ru.nxthing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nxthing.repository.entities.Word;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
}
