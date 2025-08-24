import com.quiz.model.Card;
import com.quiz.model.Deck;
import com.quiz.repository.DeckRepository;
import com.quiz.service.CardManagement;
import com.quiz.service.DeckManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeckAndCardManagementCrudTest {

    @Mock
    DeckRepository deckRepository;

    DeckManagement deckManagement;
    CardManagement cardManagement;

    @BeforeEach
    void setUp() {
        deckManagement = new DeckManagement(deckRepository);
        cardManagement = new CardManagement(deckRepository);
    }

    @Test
    @DisplayName("CREATE: createDeck(..) повинeн зберігати нову колоду з не-порожнім id і заданою назвою")
    void createDeck_createsNewDeck() {

        String name = "English A1";

        deckManagement.createDeck(name);

        ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
        verify(deckRepository, times(1)).createDeck(captor.capture());
        Deck saved = captor.getValue();
        assertNotNull(saved.getId(), "id має бути згенерований");
        assertFalse(saved.getId().isBlank(), "id не має бути порожнім");
        assertEquals(name, saved.getDeckName(), "має зберігатись передана назва");
        assertNotNull(saved.getCards(), "список карт не має бути null");
        assertEquals(0, saved.getCards().size(), "нова колода має бути без карток");
    }

    @Test
    @DisplayName("READ: getAllDecks() повертає список з репозиторію")
    void getAllDecks_returnsFromRepo() {

        Deck d1 = new Deck();
        d1.setId(UUID.randomUUID().toString());
        d1.setDeckName("Java Core");
        Deck d2 = new Deck();
        d2.setId(UUID.randomUUID().toString());
        d2.setDeckName("English A2");
        when(deckRepository.getALLDecks()).thenReturn(Arrays.asList(d1, d2));


        List<Deck> result = deckManagement.getAllDecks();

        assertEquals(2, result.size());
        assertEquals("Java Core", result.get(0).getDeckName());
        assertEquals("English A2", result.get(1).getDeckName());
        verify(deckRepository, times(1)).getALLDecks();
    }

    @Test
    @DisplayName("UPDATE: updateCard(..) замінює карту з відповідним id та зберігає оновлену колоду")
    void updateCard_updatesExistingCard() {

        String deckId = "deck-1";
        Card initial = new Card("c1", "Q1", "A1");
        Deck deck = new Deck();
        deck.setId(deckId);
        deck.setDeckName("Test");
        deck.setCards(List.of(initial));

        when(deckRepository.findDeckById(deckId)).thenReturn(Optional.of(deck));

        Card updated = new Card("c1", "Q1-upd", "A1-upd");


        cardManagement.updateCard(deckId, updated);

        ArgumentCaptor<Deck> deckCaptor = ArgumentCaptor.forClass(Deck.class);
        verify(deckRepository, times(1)).updateDeck(deckCaptor.capture());
        Deck saved = deckCaptor.getValue();
        assertEquals(deckId, saved.getId());
        assertEquals(1, saved.getCards().size());
        assertEquals("Q1-upd", saved.getCards().get(0).getQuestion());
        assertEquals("A1-upd", saved.getCards().get(0).getAnswer());
    }

    @Test
    @DisplayName("DELETE: deleteCard(..) видаляє карту по id і оновлює колоду")
    void deleteCard_removesById() {

        String deckId = "deck-1";
        Card c1 = new Card("c1", "Q1", "A1");
        Card c2 = new Card("c2", "Q2", "A2");
        Deck deck = new Deck();
        deck.setId(deckId);
        deck.setDeckName("Test");
        deck.setCards(Arrays.asList(c1, c2));

        when(deckRepository.findDeckById(deckId)).thenReturn(Optional.of(deck));

        cardManagement.deleteCard(deckId, "c1");

        ArgumentCaptor<Deck> deckCaptor = ArgumentCaptor.forClass(Deck.class);
        verify(deckRepository, times(1)).updateDeck(deckCaptor.capture());
        Deck saved = deckCaptor.getValue();
        assertEquals(1, saved.getCards().size());
        assertEquals("c2", saved.getCards().get(0).getId());
    }

    @Test
    @DisplayName("DELETE: deleteDeck(..) кидає NoSuchElementException, якщо репозиторій повертає false")
    void deleteDeck_throwsIfNotFound() {

        when(deckRepository.deleteeDeck("missing")).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> deckManagement.deleteDeck("missing"));
        verify(deckRepository, times(1)).deleteeDeck("missing");
    }

    @Test
    @DisplayName("DELETE: deleteDeck(..) успішно викликає репозиторій, якщо колода існує")
    void deleteDeck_success() {

        when(deckRepository.deleteeDeck("ok")).thenReturn(true);
        deckManagement.deleteDeck("ok");
        verify(deckRepository, times(1)).deleteeDeck("ok");
    }
}
