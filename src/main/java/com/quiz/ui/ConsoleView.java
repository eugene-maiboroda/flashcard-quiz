package com.quiz.ui;

public class ConsoleView {

    public void showMainMenu() throws InterruptedException {

        System.out.println();
        System.out.println("**********Flashcard Quiz**********");
        Thread.sleep(500);
        System.out.println();
        System.out.println("\t\t >ГОЛОВНЕ МЕНЮ<");
        System.out.println("\t 1. Почати вікторину");
        System.out.println("\t 2. Управління колодами");
        System.out.println();
        System.out.println("\t 0. Вихід");
        System.out.println();
    }
    public void showDeckManagementMenu() {
        System.out.println();
        System.out.println("\t\t>УПРАВЛІННЯ КОЛОДАМИ<");
        System.out.println("\t 1. Створити нову колоду");
        System.out.println("\t 2. Показати всі колоди");
        System.out.println("\t 3. Вибрати колоду");
        System.out.println("\t 4. Видалити колоду");
        System.out.println();
        System.out.println("\t 0. <- Повернутися до головного меню ");
        System.out.println();
    }

    public void showFlashcardManagementMenu(String name) {

        System.out.println();
        System.out.println("\t\t >УПРАВЛІННЯ КАРТКАМИ<");
        System.out.println("\t 1. Додати нову картку");
        System.out.println("\t 2. Показати всі картки");
        System.out.println("\t 3. Редагувати картку");
        System.out.println("\t 4. Видалити картку");
        System.out.println();
        System.out.println("\t 0. <- Повернутися до головного меню ");
        System.out.println();
    }

    public void showError(String error) {
        System.out.println("Помилка: " + error);
    }
    public void showMessage(String message) {
        System.out.println(message);
    }
}
