package ee.margus.card_game.service;

import ee.margus.card_game.dto.PlayerDTO;
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

    public Player login(PlayerDTO playerDTO){
        Player dbPlayer =  playerRepository.findByName(playerDTO.getName());
        if(dbPlayer != null) return dbPlayer;
        return create(playerDTO);
    }

    public Player create(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        return playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow();
    }
}
