package ee.margus.card_game.service;

import ee.margus.card_game.entity.Player;
import ee.margus.card_game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin(origins = "*")
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public Player create(Player player) {
        return playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow();
    }
}
