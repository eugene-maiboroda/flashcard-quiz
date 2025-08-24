package com.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deck {

    private String id;
    private String deckName;
    private List<Card> cards = new ArrayList<>();

    public Deck() {}

    public Deck(String id, String deckName) {
        setId(id);
        setDeckName(deckName);
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }


    public String getDeckName() { return deckName; }


    public void setDeckName(String deckName) {
        String res = deckName == null ? null : deckName.trim();
        if (res == null || res.isEmpty()) {
            throw new IllegalArgumentException("Назва колоди не може бути порожньою");
        }
        this.deckName = res;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public void setCards(List<Card> cards) {
        this.cards = (cards == null) ? new ArrayList<>() : new ArrayList<>(cards);
    }

    @JsonIgnore
    public int getSize() {
        return cards.size();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(id, deck.id) && Objects.equals(deckName, deck.deckName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "id='" + id + '\'' +
                ", deckName='" + deckName + '\'' +
                ", cards=" + cards +
                '}';
    }
}