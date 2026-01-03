package ee.margus.card_game.service;

import ee.margus.card_game.entity.Player;
import ee.margus.card_game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public Player create(String name) {
        Player player = new Player();
        player.setName(name);
        return playerRepository.save(player);
    }
}
