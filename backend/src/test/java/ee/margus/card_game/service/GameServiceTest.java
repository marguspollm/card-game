package ee.margus.card_game.service;

import ee.margus.card_game.dto.CardGameDTO;
import ee.margus.card_game.entity.Player;
import ee.margus.card_game.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private SessionService sessionService;
    @Mock
    private ScoresService scoresService;
    @InjectMocks
    private GameService gameService;

    String uuid = "test-random-uuid";

    @BeforeEach
    void tearDown() {
        sessionService.deleteSession(uuid);
    }

    @Test
    void start() {
        Deck deck = mock(Deck.class);
        GameSession gameSession = new GameSession(uuid, deck);
        CardGameDTO response = new CardGameDTO();
        response.setSessionId(uuid);
        response.setScore(0);
        response.setLives(3);
        response.setStatus(Status.ACTIVE);

        assertEquals(response, gameService.start(gameSession));
    }

    @Test
    void giveGuessIsCorrect_thenIncrementScoreAndReturnResponse() {
        Card card = new Card(Rank.JACK, Suit.DIAMONDS);
        Card nextCard = new Card(Rank.ACE, Suit.SPADES);
        Deck deck = mock(Deck.class);
        GameSession gameSession = new GameSession(uuid, deck);
        gameSession.setCurrentCard(card);
        gameSession.resetGuessTime();

        CardGameDTO response = new CardGameDTO();
        response.setSessionId(gameSession.getSessionId());
        response.setCard(nextCard);
        response.setPreviousCard(card);
        response.setLives(3);
        response.setScore(1);
        response.setStatus(Status.ACTIVE);

        when(deck.draw(any(Card.class))).thenReturn(nextCard);

        assertEquals(response, gameService.guess(gameSession, Guess.EQUAL));
    }

    @Test
    void giveGuessIsNotCorrect_thenLoseLifeAndReturnResponse() {
        Card card = new Card(Rank.JACK, Suit.DIAMONDS);
        Card nextCard = new Card(Rank.ACE, Suit.SPADES);
        Deck deck = mock(Deck.class);
        GameSession gameSession = new GameSession(uuid, deck);
        gameSession.setCurrentCard(card);
        gameSession.resetGuessTime();

        CardGameDTO response = new CardGameDTO();
        response.setSessionId(gameSession.getSessionId());
        response.setCard(nextCard);
        response.setPreviousCard(card);
        response.setLives(2);
        response.setScore(0);
        response.setStatus(Status.ACTIVE);

        when(deck.draw(any(Card.class))).thenReturn(nextCard);

        assertEquals(response, gameService.guess(gameSession, Guess.HIGHER));
    }

    @Test
    void giveLivesReachZero_thenReturnLivesLost() {
        Card card = new Card(Rank.JACK, Suit.DIAMONDS);
        Card nextCard = new Card(Rank.ACE, Suit.SPADES);
        Deck deck = mock(Deck.class);
        GameSession gameSession = new GameSession(uuid, deck);
        gameSession.setCurrentCard(card);
        gameSession.setLives(1);
        gameSession.resetGuessTime();

        CardGameDTO response = new CardGameDTO();
        response.setSessionId(gameSession.getSessionId());
        response.setCard(nextCard);
        response.setPreviousCard(card);
        response.setLives(0);
        response.setScore(0);
        response.setStatus(Status.LIVES_LOST);

        when(deck.draw(any(Card.class))).thenReturn(nextCard);

        assertEquals(response, gameService.guess(gameSession, Guess.LOWER));
    }

    @Test
    void giveGuessTimerReachesZero_thenReturnTimedOut() {
        Card card = new Card(Rank.JACK, Suit.DIAMONDS);
        Deck deck = mock(Deck.class);
        GameSession gameSession = new GameSession(uuid, deck);
        gameSession.setCurrentCard(card);
        gameSession.setGuessTime(System.currentTimeMillis()-20000);

        CardGameDTO response = new CardGameDTO();
        response.setSessionId(gameSession.getSessionId());
        response.setCard(card);
        response.setPreviousCard(null);
        response.setLives(3);
        response.setScore(0);
        response.setStatus(Status.TIME_OUT);


        assertEquals(response, gameService.guess(gameSession, Guess.HIGHER));
    }

    @Test
    void endGame() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Test");
        GameSession gameSession = new GameSession(uuid, new Deck(new Random()));
        gameSession.setScore(10);
        gameSession.setPlayer(player);

        gameService.endGame(gameSession);

        verify(scoresService).saveResult(gameSession);
    }
}