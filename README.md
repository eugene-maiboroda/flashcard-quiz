# Flashcard Quiz (Console Application)

**Flashcard Quiz** is a console-based application for practicing knowledge with flashcards.  
You can create decks, add questions and answers, and test yourself in quiz mode.

### ✨ Features
The application provides functionality to:
* Create and delete decks
* Add, edit, and delete flashcards
* Save and load decks from JSON file
* Automatic data loading on startup
* Quiz mode with results and scoring
* Console-based UI with menu navigation

### 📂 Project Structure
```
src/main/java/com/quiz
 ├── model        → POJO classes (`Card`, `Deck`)
 ├── repository   → Data persistence (`DeckRepository`, `FileDeckRepository`)
 ├── service      → Business logic (`CardManagement`, `DeckManagement`, `QuizMode`)
 ├── ui           → Console interaction (`ConsoleView`, `FlashCardController`)
 └── StartApp     → Entry point
```

### 🚀 Getting Started

**Requirements**
* Java 21 (or newer)
* Maven (if you want to rebuild)

**Clone**
```bash
git clone https://github.com/eugene-maiboroda/flashcard-quiz.git
cd flashcard-quiz
```

### 📸 Console Preview

**Main Menu:**
```
********** Flashcard Quiz **********

> MAIN MENU <
1. Start Quiz
2. Manage Decks

0. Exit
```

**Deck Management:**
```
> Deck Management <
1. Create new deck
2. Show all decks
3. Select deck
4. Delete deck

0. <- Return to main menu
```

### 🛠 Technologies
* Java 21
* Maven
* Jackson (for JSON serialization)

