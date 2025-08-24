package com.quiz.ui;

import com.quiz.model.Card;
import com.quiz.model.Deck;
import com.quiz.service.CardManagement;
import com.quiz.service.DeckManagement;
import com.quiz.service.QuizMode;

import java.io.UncheckedIOException;
import java.util.*;

public class FlashCardController {

    private final DeckManagement deckManager;
    private final CardManagement cardManagement;
    private final QuizMode quizMode;
    private final ConsoleView consoleView;
    private final Scanner scanner;

    public FlashCardController(DeckManagement deckManager, CardManagement cardManagement, QuizMode quizMode, ConsoleView consoleView, Scanner scanner) {
        this.deckManager = deckManager;
        this.cardManagement = cardManagement;
        this.quizMode = quizMode;
        this.consoleView = consoleView;
        this.scanner = scanner;
    }

    public void run() {
        makeChoice();
    }

    private void makeChoice() {

        try {
            do {
                consoleView.showMainMenu();
                int choice = getUserChoice(1, 2);
                if (choice == 0) {
                    break;
                }
                callChoice(choice);
            }
            while (true);
        } catch (Exception e) {
            consoleView.showError(e.getMessage());
        }
    }

    private void callChoice(int choice) {
        switch (choice) {
            case 1 -> quizMode();
            case 2 -> managerDeck();
        }
    }

    private void quizMode() {
        List<Deck> decks = deckManager.getAllDecks();
        if (decks.isEmpty()) {
            consoleView.showMessage("Немає жодної колоди, треба спочатку створити");
            return;
        }
        showAllDeck();
        int choice = getUserChoice(1, decks.size());
        Deck selectedDeck = decks.get(choice -1);
        quizMode.run(selectedDeck, () -> scanner.nextLine(),  s -> consoleView.showMessage(s) );
    }

    private void managerDeck() {
        while (true) {
            consoleView.showDeckManagementMenu();
            int choice = getUserChoice(0, 4);
            switch (choice) {
                case 1 -> createDeck();
                case 2 -> showAllDeck();
                case 3 -> choiceDeck();
                case 4 -> deleteDeck();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void createDeck() {
        List<Deck> decks = deckManager.getAllDecks();
        if (decks.size() == 20) {
            consoleView.showMessage("Максимальна кільеість колод, спочатку видаліть");
            return;
        }
        consoleView.showMessage("Вкажіть назву колоди: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            consoleView.showMessage("Назва колоди не може бути порожньою");
            return;
        }
        try {
            deckManager.createDeck(name);
            consoleView.showMessage("Колоду: " + name + " успішно створено");
        } catch (IllegalArgumentException | UncheckedIOException e) {
            consoleView.showMessage("Помилка" + e.getMessage());
        }
    }

    private void deleteDeck() {
        List<Deck> decks = deckManager.getAllDecks();


        if (decks.isEmpty()) {
            consoleView.showMessage("Немає доступних колод для видалення");
            return;
        }

        consoleView.showMessage("Виберіть колоду для видалення:");
        for (int i = 0; i < decks.size(); i++) {
            Deck deck = decks.get(i);
            consoleView.showMessage((i + 1) + ". " + deck.getDeckName() + " (" + deck.getSize() + " карток)");
        }
        consoleView.showMessage("Введіть номер колоди (0 - повернутися назад):");
        int choice = getUserChoice(1, decks.size());
        if (choice == 0) {
            return;
        }

        Deck selectDeck = decks.get(choice - 1);
        consoleView.showMessage("Ви впевнені що хочете видалити колоду " + selectDeck.getDeckName() + "? y/n");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("y") || confirm.equals("yes") || confirm.equals("так")) {
            try {
                deckManager.deleteDeck(selectDeck.getId());
                consoleView.showMessage("Колода успішно видалена");
            } catch (IllegalArgumentException | UncheckedIOException e) {
                consoleView.showMessage("Помилка видалення" + e.getMessage());
            }
        } else {
            consoleView.showMessage("Видалення відмінено");
        }
    }

    private void showAllDeck() {
        List<Deck> decks = deckManager.getAllDecks();
        if (decks.isEmpty()) {
            consoleView.showMessage("Немає доступних колод, треба створити");
            return;
        }
        consoleView.showMessage("**********Доступні колоди**********\n");
        for (int i = 0; i < decks.size(); i++) {
            Deck deck = decks.get(i);
            consoleView.showMessage((i + 1) + ". " + deck.getDeckName() + " (" + deck.getSize() + " карток)");
        }
    }

    private void choiceDeck() {
        List<Deck> decks = deckManager.getAllDecks();
        showAllDeck();

        int choice = getUserChoice(0, decks.size());
        if (choice == 0) {
            return;
        }

        Deck selectDeck = decks.get(choice - 1);
        managerCard(selectDeck.getDeckName(), selectDeck.getId());


    }

    private void managerCard(String nameDeck, String deckId) {
        while (true) {
            consoleView.showFlashcardManagementMenu(nameDeck);
            int choice = getUserChoice(0, 4);
            switch (choice) {
                case 1 -> addCard(nameDeck, deckId);
                case 2 -> showAllCard(nameDeck, deckId);
                case 3 -> updateCard(nameDeck, deckId);
                case 4 -> deleteCard(nameDeck, deckId);
                case 0 -> {
                    return;
                }
            }

        }
    }

    private void addCard(String nameDeck, String deckId) {

        consoleView.showMessage("\tДодавання нової картки до колоди " + nameDeck);
        String id = UUID.randomUUID().toString();
        consoleView.showMessage("Введіть питання до картки");
        String question = scanner.nextLine().trim();
        if (question.isEmpty()) {
            consoleView.showMessage("Питання не може бути порожнім");
            return;
        }
        consoleView.showMessage("Введіть відповідь до картки");
        String answer = scanner.nextLine().trim();
        if (answer.isEmpty()) {
            consoleView.showMessage("Відповідь не може бути порожня");
            return;
        }

        Card card = new Card(id, question, answer);
        try {
            cardManagement.addCard(deckId, card);
            consoleView.showMessage("Картка успішно додана");
        } catch (IllegalArgumentException | UncheckedIOException e) {
            consoleView.showMessage("Помилка додовання " + e.getMessage());
        }
    }

    private void showAllCard(String nameDeck, String deckId) {
        List<Card> allCards = cardManagement.getAllCards(deckId);

        if (allCards.isEmpty()) {
            consoleView.showMessage("Немає доступних карток у колоді " + nameDeck);
            return;
        }
        printCardsToChoice(nameDeck, allCards);
    }

    private void updateCard(String nameDeck, String deckId) {
        List<Card> allCards = cardManagement.getAllCards(deckId);

        if (allCards.isEmpty()) {
            consoleView.showMessage("Немає доступних карток у колоді " + nameDeck);
            return;
        }
        printCardsToChoice(nameDeck, allCards);
        consoleView.showMessage("Виберіть картку для редагування");
        int choice = getUserChoice(1, allCards.size());
        consoleView.showMessage("\tРедагування картки колоди " + nameDeck + "\n");

        consoleView.showMessage("Введіть питання до картки");
        String question = scanner.nextLine().trim();
        if (question.isEmpty()) {
            consoleView.showMessage("Питання не може бути порожня");
        }
        consoleView.showMessage("Введіть відповідь до картки");
        String answer = scanner.nextLine().trim();
        if (answer.isEmpty()) {
            consoleView.showMessage("Відповідь не може бути порожня");
        }
        try {
            Card newCard = allCards.get(choice - 1);
            newCard.setQuestion(question);
            newCard.setAnswer(answer);
            cardManagement.updateCard(deckId, newCard);
            consoleView.showMessage("Картка успішно змінена");
        } catch (IllegalArgumentException | UncheckedIOException e) {
            consoleView.showMessage("Помилка редагування " + e.getMessage());
        }
    }

    private void deleteCard(String nameDeck, String deckId) {
        List<Card> allCards = cardManagement.getAllCards(deckId);

        if (allCards.isEmpty()) {
            consoleView.showMessage("Немає доступних карток у колоді " + nameDeck);
        }
        printCardsToChoice(nameDeck, allCards);
        consoleView.showMessage("Введіть номер картки для видалення (0 - повернутися назад):");
        int choice = getUserChoice(0, allCards.size());
        if (choice == 0) {
            return;
        }
        Card selectCard = allCards.get(choice - 1);
        consoleView.showMessage("Ви впевнені що хочете видалити картку? y/n");
        String confirm = scanner.nextLine().trim();
        if (confirm.equals("y") || confirm.equals("yes") || confirm.equals("так")) {
            try {
                cardManagement.deleteCard(deckId, selectCard.getId());
               consoleView.showMessage("Картка успішно видалена");
            } catch (IllegalArgumentException | UncheckedIOException e) {
                consoleView.showMessage("Помилка видалення картки " +  e.getMessage());
            }
            } else {
            System.out.println("Видалення відмінено");
        }
    }

    private void printCardsToChoice(String nameDeck, List<Card> cards) {
        consoleView.showMessage("**********Доступні картки у колоді " + nameDeck + "**********\n");
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            consoleView.showMessage((i + 1) + ".  Питання: " + card.getQuestion());
            consoleView.showMessage("\tВідповідь: " + card.getAnswer());
            consoleView.showMessage("-".repeat(50));
        }
    }

    private int getUserChoice(int min, int max) {

        while (true) {
            try {
                consoleView.showMessage("Ваш вибір: ");
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                if (choice == 0) {
                    return 0;
                }
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                consoleView.showError("Будь ласка введіть коректне число");
            }
        }
    }
}

