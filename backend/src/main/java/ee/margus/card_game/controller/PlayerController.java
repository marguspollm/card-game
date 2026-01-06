package ee.margus.card_game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ee.margus.card_game.entity.Player;
import ee.margus.card_game.service.PlayerService;

@RestController
@CrossOrigin(origins = "*")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/player")
    public Player createPlayer(@RequestBody Player player){
        return playerService.create(player);
    }
    
}
