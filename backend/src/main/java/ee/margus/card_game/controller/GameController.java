package ee.margus.card_game.controller;

import ee.margus.card_game.dto.CardGameDTO;
import ee.margus.card_game.dto.GameRequest;
import ee.margus.card_game.dto.StartGameDTO;
import ee.margus.card_game.model.GameState;
import ee.margus.card_game.service.GameService;
import ee.margus.card_game.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private GameService gameService;


    @PostMapping("/game")
    public StartGameDTO createSession(@RequestBody StartGameDTO request) {
        return sessionService.createSession(request);
    }

    @PostMapping("/game/start-game")
    public CardGameDTO startGame(@RequestBody GameRequest request) {
        GameState session = sessionService.getSession(request.getSessionId());
        return gameService.start(session);
    }

    @PostMapping("/game/guess")
    public CardGameDTO guess(@RequestBody GameRequest request) {
        GameState session = sessionService.getSession(request.getSessionId());
        return gameService.guess(session, request.getGuess());
    }

    @PostMapping("/game/end-game")
    public void endGame(@RequestBody GameRequest request) {
        GameState session = sessionService.getSession(request.getSessionId());
        gameService.endGame(session);
    }

}
