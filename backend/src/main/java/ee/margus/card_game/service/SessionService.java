package ee.margus.card_game.service;

import ee.margus.card_game.dto.StartGameDTO;
import ee.margus.card_game.entity.Player;
import ee.margus.card_game.model.Deck;
import ee.margus.card_game.model.GameState;
import ee.margus.card_game.util.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {
    private final Map<String, GameState> games = new ConcurrentHashMap<>();
    @Autowired
    private PlayerService playerService;

    @Autowired
    private RandomGenerator randomGenerator;

    public StartGameDTO createSession(StartGameDTO request) {
        if(request.getPlayer() == null || request.getPlayer().getId() == null) throw new RuntimeException("Player id is missing!");
        Player player = playerService.getPlayer(request.getPlayer().getId());
        GameState gameState = new GameState(randomGenerator.generate(), new Deck(new Random()));
        String uuid = gameState.getSessionId();
        gameState.setPlayer(player);
        games.put(uuid, gameState);
        return new StartGameDTO(uuid, gameState.getPlayer());
    }

    public GameState getGameState(String id) {
        GameState session = games.get(id);
        if (session == null) {
            throw new RuntimeException("Session not found");
        }
        return session;
    }

    public void deleteSession(String id) {
        games.remove(id);
    }
}
