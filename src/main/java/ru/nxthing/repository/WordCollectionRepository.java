package ru.nxthing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nxthing.repository.entities.WordCollection;

public interface WordCollectionRepository extends JpaRepository<WordCollection, Long> {

}