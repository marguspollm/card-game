package ee.margus.card_game.model;

import ee.margus.card_game.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameSession {
    private String sessionId;
    private final Deck deck = new Deck();
    private Card currentCard;
    private Card previousCard;
    private int lives = 3;
    private int score = 0;
    private long guessTime;
    private Instant startTime;
    private long duration;
    private Player player;

    public void loseLife() {
        if (lives > 0) lives -= 1;
    }

    public void incrementScore() {
        score += 1;
    }

    public void resetGuessTime() {
        guessTime = System.currentTimeMillis();
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public boolean isTimedOut() {
        return System.currentTimeMillis() - guessTime > 10000;
    }

    public void setStartTime() {
        startTime = Instant.now();
    }

    public void calcDuration() {
        duration = Duration.between(startTime, Instant.now()).toMillis();
    }
}
