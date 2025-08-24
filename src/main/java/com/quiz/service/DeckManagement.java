package com.quiz.service;

import com.quiz.model.Deck;
import com.quiz.repository.DeckRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class DeckManagement {

    private final DeckRepository deckRepository;

    public DeckManagement(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public void createDeck(String nameDeck) {
        if (nameDeck == null || nameDeck.isEmpty()) {
            throw new IllegalArgumentException("Назва колоди не може бути пуста");
        }
        if (nameDeck.trim().length() > 20) {
            throw new IllegalArgumentException("Назва колоди не може перевищувати 20 символів");
        }
        String id = UUID.randomUUID().toString();
        Deck deck = new Deck(id, nameDeck);
            deckRepository.createDeck(deck);
    }

    public List<Deck> getAllDecks() {
        return deckRepository.getALLDecks();

    }

    public void deleteDeck(String idDeck) {
        if (!deckRepository.deleteeDeck(idDeck)) {
            throw new NoSuchElementException("Колода не знайдена " + idDeck);
        }

    }
}