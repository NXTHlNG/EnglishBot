package ru.nxthing.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nxthing.repository.WordCollectionRepository;
import ru.nxthing.repository.entities.WordCollection;

import java.util.List;

@Service
public class WordCollectionService {
    WordCollectionRepository wordCollectionRepository;

    public WordCollectionService(WordCollectionRepository wordCollectionRepository) {
        this.wordCollectionRepository = wordCollectionRepository;
    }

    @Transactional
    public List<WordCollection> findAll() {
        return wordCollectionRepository.findAll();
    }

    public WordCollection save(WordCollection collection) {
        return wordCollectionRepository.save(collection);
    }
}
