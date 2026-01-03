package ee.margus.card_game.dto;

import ee.margus.card_game.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartGameDTO {
    private String sessionId;
    private Player player;
}
