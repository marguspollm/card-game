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

    public CardGameDTO start(GameState session) {
        Card firstCard = session.getDeck().draw(session.getCurrentCard());
        session.setCurrentCard(firstCard);
        session.resetGuessTime();
        return new CardGameDTO(session.getSessionId(),
                firstCard,
                null,
                session.getLives(),
                session.getScore(),
                Status.ACTIVE
        );
    }

    public CardGameDTO guess(GameState session, Guess guess) {
        if (session.isTimedOut())
            return new CardGameDTO(session.getSessionId(),
                    session.getCurrentCard(), session.getPreviousCard(),
                    session.getLives(),
                    session.getScore(),
                    Status.TIME_OUT
            );
        Card currentCard = session.getCurrentCard();
        session.setPreviousCard(currentCard);
        Card nextCard = session.getDeck().draw(currentCard);
        Guess correctGuess = compare(currentCard, nextCard);
        if (guess.equals(correctGuess)) {
            session.incrementScore();
        } else {
            session.loseLife();
        }
        if (session.isDead()) {
            session.calcDuration();
            endGame(session);
            return new CardGameDTO(session.getSessionId(),
                    nextCard,
                    session.getPreviousCard(),
                    session.getLives(), session.getScore(),
                    Status.LIVES_LOST
            );
        }

        session.setCurrentCard(nextCard);
        session.resetGuessTime();

        return new CardGameDTO(session.getSessionId(),
                nextCard,
                session.getPreviousCard(),
                session.getLives(),
                session.getScore(),
                Status.ACTIVE
        );
    }

    private Guess compare(Card currentCard, Card nextCard) {
        int currentCardValue = currentCard.getRank().getValue();
        int nextCardValue = nextCard.getRank().getValue();
        if (nextCardValue > currentCardValue) return Guess.HIGHER;
        if (nextCardValue < currentCardValue) return Guess.LOWER;
        return Guess.EQUAL;
    }

    public void endGame(GameState session) {
        session.calcDuration();
        scoresService.saveResult(session);
        sessionService.deleteSession(session.getSessionId());
    }
}
