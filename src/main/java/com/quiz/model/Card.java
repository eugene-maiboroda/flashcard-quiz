package com.quiz.model;

import java.util.Objects;

public class Card {

    private String id;
    private String question;
    private String answer;

    public Card() {}

    public Card(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getId() { return id; }
    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(question, card.question) && Objects.equals(answer, card.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, answer);
    }
}

