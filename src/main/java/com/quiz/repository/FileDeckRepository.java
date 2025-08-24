package com.quiz.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.model.Deck;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileDeckRepository implements DeckRepository {

    private static final Path DATA_DIR = Paths.get("data");
    private static final Path DECKS = DATA_DIR.resolve("decks.json");

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Deck> cache = new LinkedHashMap<>();

    public FileDeckRepository() {
        initStorage();
        loadIntoCache();
    }

    @Override
    public void createDeck(Deck deck) {
        String id = norm(deck.getId());
        if (cache.containsKey(id)) {
            throw new IllegalArgumentException("Немає такої колоди " + id);
        }
        cache.put(id, deck);
        persist();
    }

    @Override
    public void updateDeck(Deck deck) {
        String id = norm(deck.getId());
        if (!cache.containsKey(id)) {
            throw new IllegalArgumentException("Немає такої колоди " + id);
        }
        cache.put(id, deck);
        persist();
    }

    @Override
    public List<Deck> getALLDecks() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public Optional<Deck> findDeckById(String id) {
        return Optional.ofNullable(cache.get(norm(id)));
    }

    @Override
    public boolean deleteeDeck(String deckId) {

        if (!cache.containsKey(deckId)) {
           return false;
        }
        cache.remove(norm(deckId));
        persist();
        return true;
    }

    private void initStorage() {
        try {
            Files.createDirectories(DATA_DIR);
            if (Files.notExists(DECKS)) Files.writeString(DECKS, "[]");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void loadIntoCache() {
        try {
            List<Deck> list = mapper.readValue(DECKS.toFile(), new TypeReference<>() {
            });
            cache.clear();
            list.forEach(deck -> cache.put(norm(deck.getId()), deck));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void persist() {
        try {
            Path tmp = Files.createTempFile(DATA_DIR, "decks", ".json");
            mapper.writeValue(tmp.toFile(), cache.values());
            Files.move(tmp, DECKS,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String norm(Object o) {
        return o == null ? null : o.toString().trim();
    }
}


