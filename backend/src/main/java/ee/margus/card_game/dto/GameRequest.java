package ee.margus.card_game.dto;

import ee.margus.card_game.model.Guess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRequest {
    private String sessionId;
    private Guess guess;
}
