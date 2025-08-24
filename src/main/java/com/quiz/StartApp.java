package com.quiz;

import com.quiz.repository.DeckRepository;
import com.quiz.repository.FileDeckRepository;
import com.quiz.service.CardManagement;
import com.quiz.service.DeckManagement;
import com.quiz.service.QuizMode;
import com.quiz.ui.ConsoleView;
import com.quiz.ui.FlashCardController;

import java.util.Scanner;

public class StartApp {
    public static void main(String[] args) {

        DeckRepository deckRepository = new FileDeckRepository();
        DeckManagement deckManager = new DeckManagement(deckRepository);
        ConsoleView consoleView = new ConsoleView();
        QuizMode quizMode = new QuizMode();
        CardManagement cardManagement = new CardManagement(deckRepository);
        Scanner scanner = new Scanner(System.in);

        FlashCardController flashcardController = new FlashCardController(deckManager, cardManagement,quizMode,consoleView,scanner);
        flashcardController.run();
    }
}