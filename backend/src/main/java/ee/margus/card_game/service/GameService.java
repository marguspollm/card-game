package ee.margus.card_game.service;

import ee.margus.card_game.dto.CardGameDTO;
import ee.margus.card_game.model.Card;
import ee.margus.card_game.model.GameState;
import ee.margus.card_game.model.Guess;
import ee.margus.card_game.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    private ScoresService scoresService;
    @Autowired
    private SessionService sessionService;

    public CardGameDTO start(GameState gameState) {
        Card firstCard = gameState.getDeck().draw(gameState.getCurrentCard());
        gameState.setCurrentCard(firstCard);
        gameState.resetGuessTime();
        return new CardGameDTO(gameState.getSessionId(),
                firstCard,
                null,
                gameState.getLives(),
                gameState.getScore(),
                Status.ACTIVE,
                true
        );
    }

    public CardGameDTO guess(GameState gameState, Guess guess) {
        if (gameState.isTimedOut())
            return new CardGameDTO(gameState.getSessionId(),
                    gameState.getCurrentCard(), gameState.getPreviousCard(),
                    gameState.getLives(),
                    gameState.getScore(),
                    Status.TIME_OUT,
                    false
            );
        Card currentCard = gameState.getCurrentCard();
        gameState.setPreviousCard(currentCard);
        Card nextCard = gameState.getDeck().draw(currentCard);
        Guess correctGuess = compare(currentCard, nextCard);
        var isCorrectGuess = guess.equals(correctGuess);
        if (isCorrectGuess) {
            gameState.incrementScore();
        } else {
            gameState.loseLife();
        }
        if (gameState.isDead()) {
            gameState.calcDuration();
            endGame(gameState);
            return new CardGameDTO(gameState.getSessionId(),
                    nextCard,
                    gameState.getPreviousCard(),
                    gameState.getLives(), gameState.getScore(),
                    Status.LIVES_LOST,
                    isCorrectGuess
            );
        }

        gameState.setCurrentCard(nextCard);
        gameState.resetGuessTime();

        return new CardGameDTO(gameState.getSessionId(),
                nextCard,
                gameState.getPreviousCard(),
                gameState.getLives(),
                gameState.getScore(),
                Status.ACTIVE,
                isCorrectGuess
        );
    }

    private Guess compare(Card currentCard, Card nextCard) {
        int currentCardValue = currentCard.getRank().getValue();
        int nextCardValue = nextCard.getRank().getValue();
        if (nextCardValue > currentCardValue) return Guess.HIGHER;
        if (nextCardValue < currentCardValue) return Guess.LOWER;
        return Guess.EQUAL;
    }

    public void endGame(GameState gameState) {
        gameState.calcDuration();
        scoresService.saveResult(gameState);
        sessionService.deleteSession(gameState.getSessionId());
    }
}
