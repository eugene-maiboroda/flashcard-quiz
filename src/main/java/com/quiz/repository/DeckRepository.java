package com.quiz.repository;

import com.quiz.model.Deck;
import java.util.List;
import java.util.Optional;

public interface DeckRepository {

    void createDeck(Deck deck);
    void updateDeck(Deck deck);
    boolean deleteeDeck(String id);
    List<Deck> getALLDecks();
    Optional<Deck> findDeckById(String id);
}
