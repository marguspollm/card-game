package ee.margus.card_game.controller;

import ee.margus.card_game.dto.PlayerDTO;
import ee.margus.card_game.entity.Player;
import ee.margus.card_game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/login")
    public Player login(@RequestBody PlayerDTO player) {
        return playerService.login(player);
    }

}
