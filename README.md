Flashcard Quiz (Console Application)
Flashcard Quiz is a console-based application for practicing knowledge with flashcards.  
You can create decks, add questions and answers, and test yourself in quiz mode.

***✨ Features
Create and delete decks
Add, edit, and delete flashcards
Save and load decks from JSON file
Automatic data loading on startup
Quiz mode with results and scoring
Console-based UI with menu navigation

***📂 Project Structure
src/main/java/com/quiz
 ├── model        → POJO classes (`Card`, `Deck`)
 ├── repository   → Data persistence (`DeckRepository`, `FileDeckRepository`)
 ├── service      → Business logic (`CardManagement`, `DeckManagement`, `QuizMode`)
 ├── ui           → Console interaction (`ConsoleView`, `FlashCardController`)
 └── StartApp     → Entry point
 
***🚀 Getting Started
Requirements
Java 21 (or newer)
Maven (if you want to rebuild)
Clone
git clone https://github.com/USERNAME/flashcard-quiz.git
cd flashcard-quiz
