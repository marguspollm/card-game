package ee.margus.card_game.service;

import ee.margus.card_game.dto.StartGameDTO;
import ee.margus.card_game.entity.Player;
import ee.margus.card_game.model.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {
    private final Map<String, GameSession> games = new ConcurrentHashMap<>();
    @Autowired
    private PlayerService playerService;

    public StartGameDTO createSession(StartGameDTO request) {
        String uuid = UUID.randomUUID().toString();
        GameSession gameState = new GameSession();
        if (request.getPlayer().getId() == null) {
            Player createdPlayer = playerService.create(request.getPlayer().getName());
            gameState.setPlayer(createdPlayer);
        } else {
            gameState.setPlayer(request.getPlayer());
        }
        gameState.setSessionId(uuid);
        games.put(uuid, gameState);
        return new StartGameDTO(uuid, gameState.getPlayer());
    }

    public GameSession getSession(String id) {
        GameSession session = games.get(id);
        if (session == null) {
            throw new RuntimeException("Session not found");
        }
        return session;
    }

    public void deleteSession(String id) {
        games.remove(id);
    }
}
