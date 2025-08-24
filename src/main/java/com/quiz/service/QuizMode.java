package com.quiz.service;

import com.quiz.model.Card;
import com.quiz.model.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class QuizMode {
    public void run(Deck deck, Supplier<String> readLine,Consumer<String> writeLine) {

     writeLine.accept("Обрана колода для гри " + deck.getDeckName());
        List<Card> cards = new ArrayList<>(deck.getCards());
        if (cards.isEmpty()) {
            writeLine.accept("Немає жодної картки, треба спочатку створити");
        }
        int count = 0;
        writeLine.accept("Уведіть exit щоб вийти з гри");
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            writeLine.accept("Питання " + (i + 1) + "/" + cards.size() + ": " + card.getQuestion());

            String answer =  readLine.get().trim().toLowerCase();
            if (answer.equalsIgnoreCase("exit")) {
                writeLine.accept("Гру завершино достроково");
                return;
            }
            if(card.getAnswer().trim().equalsIgnoreCase(answer)) {
                count++;
                writeLine.accept("✓ Правильно!");
            } else  {
                writeLine.accept("✗ Помилка. Правильна відповідь - " + card.getAnswer());
            }
        }
        writeLine.accept("Гра закінчена, ви відповили правилно на " + count + " питань з " + cards.size());
    }
}
