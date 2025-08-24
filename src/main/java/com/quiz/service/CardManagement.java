package com.quiz.service;

import com.quiz.model.Card;
import com.quiz.model.Deck;
import com.quiz.repository.DeckRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CardManagement {

    private final DeckRepository deckRepository;

    public CardManagement(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;

    }
    public void addCard(String deckId, Card card) {

      Deck deck = deckRepository.findDeckById(deckId)
              .orElseThrow(() -> new IllegalArgumentException("Колоду не знайдено " +  deckId));
        if (card == null) {
            throw  new IllegalArgumentException("Картка пуста");
        }

        List<Card> updated = new ArrayList<>(deck.getCards());
        boolean duplicate = updated.stream().anyMatch(listCard -> Objects.equals(listCard.getId(), card.getId()));
        if (duplicate) {
            throw new IllegalArgumentException("Карта з таким ID уже існує " + card.getId());
        }
        updated.add(card);
        deck.setCards(updated);
        deckRepository.updateDeck(deck);
  }
    public List<Card> getAllCards(String deckId) {
        Deck deck = deckRepository.findDeckById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Колоду не знайдено " + deckId));
        List<Card> cards = new ArrayList<>(deck.getCards());
        return cards;
    }
    public void updateCard(String deckId, Card card) {
        Deck deck = deckRepository.findDeckById(deckId).orElseThrow(() ->
                new IllegalArgumentException("Колоду не знайдено " +  deckId));
        List<Card> updated = new ArrayList<>(deck.getCards());

        for (int i = 0; i < updated.size(); i++) {
            if (Objects.equals(updated.get(i).getId(), card.getId())) {
                updated.set(i, card);
            } else {
                throw new IllegalArgumentException("Карту не знайдено");
            }
        }
        deck.setCards(updated);
        deckRepository.updateDeck(deck);
    }
    public boolean deleteCard(String deckId,  String cardId) {
        Deck deck = deckRepository.findDeckById(deckId).orElseThrow(() ->
                 new IllegalArgumentException("Колоду не знайдено "  +  deckId));
        List<Card> updated = new ArrayList<>(deck.getCards());
        boolean removed = updated.removeIf(card -> Objects.equals(card.getId(), cardId));
        deck.setCards(updated);
        deckRepository.updateDeck(deck);
        return removed;
    }
}
