package ee.margus.card_game.service;

import ee.margus.card_game.dto.CardGameDTO;
import ee.margus.card_game.entity.Player;
import ee.margus.card_game.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private ScoresService scoresService;
    @InjectMocks
    private GameService gameService;

    String uuid = "test-random-uuid";

    @Test
    void start() {
        Deck deck = mock(Deck.class);
        GameState gameState = new GameState(uuid, deck);
        CardGameDTO response = new CardGameDTO();
        response.setSessionId(uuid);
        response.setScore(0);
        response.setLives(3);
        response.setStatus(Status.ACTIVE);
        response.setCorrect(true);

        assertEquals(response, gameService.start(gameState));
    }

    @Test
    void giveGuessIsCorrect_thenIncrementScoreAndReturnResponse() {
        Card card = new Card(Rank.JACK, Suit.DIAMONDS);
        Card nextCard = new Card(Rank.ACE, Suit.SPADES);
        Deck deck = mock(Deck.class);
        GameState gameState = new GameState(uuid, deck);
        gameState.setCurrentCard(card);
        gameState.resetGuessTime();

        CardGameDTO response = new CardGameDTO();
        response.setSessionId(gameState.getSessionId());
        response.setCard(nextCard);
        response.setPreviousCard(card);
        response.setLives(3);
        response.setScore(1);
        response.setStatus(Status.ACTIVE);
        response.setCorrect(true);

        when(deck.draw(any(Card.class))).thenReturn(nextCard);

        assertEquals(response, gameService.guess(gameState, Guess.EQUAL));
    }

    @Test
    void giveGuessIsNotCorrect_thenLoseLifeAndReturnResponse() {
        Card card = new Card(Rank.JACK, Suit.DIAMONDS);
        Card nextCard = new Card(Rank.ACE, Suit.SPADES);
        Deck deck = mock(Deck.class);
        GameState gameState = new GameState(uuid, deck);
        gameState.setCurrentCard(card);
        gameState.resetGuessTime();

        CardGameDTO response = new CardGameDTO();
        response.setSessionId(gameState.getSessionId());
        response.setCard(nextCard);
        response.setPreviousCard(card);
        response.setLives(2);
        response.setScore(0);
        response.setStatus(Status.ACTIVE);

        when(deck.draw(any(Card.class))).thenReturn(nextCard);

        assertEquals(response, gameService.guess(gameState, Guess.HIGHER));
    }

    @Test
    void giveLivesReachZero_thenReturnLivesLost() {
        Card card = new Card(Rank.JACK, Suit.DIAMONDS);
        Card nextCard = new Card(Rank.ACE, Suit.SPADES);
        Deck deck = mock(Deck.class);
        GameState gameState = new GameState(uuid, deck);
        gameState.setCurrentCard(card);
        gameState.setLives(1);
        gameState.resetGuessTime();

        CardGameDTO response = new CardGameDTO();
        response.setSessionId(gameState.getSessionId());
        response.setCard(nextCard);
        response.setPreviousCard(card);
        response.setLives(0);
        response.setScore(0);
        response.setStatus(Status.LIVES_LOST);

        when(deck.draw(any(Card.class))).thenReturn(nextCard);

        assertEquals(response, gameService.guess(gameState, Guess.LOWER));
    }

    @Test
    void giveGuessTimerReachesZero_thenReturnTimedOut() {
        Card card = new Card(Rank.JACK, Suit.DIAMONDS);
        Deck deck = mock(Deck.class);
        GameState gameState = new GameState(uuid, deck);
        gameState.setCurrentCard(card);
        gameState.setGuessTime(Instant.now().minusMillis(20000));

        CardGameDTO response = new CardGameDTO();
        response.setSessionId(gameState.getSessionId());
        response.setCard(card);
        response.setPreviousCard(null);
        response.setLives(3);
        response.setScore(0);
        response.setStatus(Status.TIME_OUT);


        assertEquals(response, gameService.guess(gameState, Guess.HIGHER));
    }

    @Test
    void endGame() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Test");
        GameState gameState = new GameState(uuid, new Deck(new Random()));
        gameState.setScore(10);
        gameState.setPlayer(player);

        gameService.endGame(gameState);

        verify(scoresService).saveResult(gameState);
    }
}