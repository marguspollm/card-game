package ee.margus.card_game.dto;

import ee.margus.card_game.model.Card;
import ee.margus.card_game.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardGameDTO {
    private String sessionId;
    private Card card;
    private Card previousCard;
    private int lives;
    private int score;
    private Status status;
}
