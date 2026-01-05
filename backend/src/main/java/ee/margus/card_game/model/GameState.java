package ee.margus.card_game.model;

import ee.margus.card_game.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class GameState {
    private final String sessionId;
    private final Deck deck;
    private Card currentCard;
    private Card previousCard;
    private int lives = 3;
    private int score = 0;
    private Instant guessTime;
    private Instant startTime = Instant.now();
    private long duration;
    private Player player;

    public GameState(String sessionId, Deck deck) {
        this.sessionId = sessionId;
        this.deck = deck;
    }

    public void loseLife() {
        if (lives > 0) lives -= 1;
    }

    public void incrementScore() {
        score += 1;
    }

    public void resetGuessTime() {
        guessTime = Instant.now();
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public boolean isTimedOut() {
        return Duration.between(guessTime, Instant.now()).toMillis() > 10000;
    }

    public void calcDuration() {
        duration = Duration.between(startTime, Instant.now()).toMillis();
    }

    @Override
    public String toString() {
        boolean safeTimedOut;
        try {
            safeTimedOut = isTimedOut();
        } catch (Exception e) {
            safeTimedOut = false;
        }
        return "\n SessinId:" + sessionId + " - startTime:" + startTime + " - duration:" + duration + " - dead?:" + safeTimedOut + " - guessTime:" + guessTime;
    }
}
