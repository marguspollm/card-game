package ee.margus.card_game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ee.margus.card_game.dto.StartGameDTO;
import ee.margus.card_game.service.SessionService;

@RestController
@CrossOrigin(origins = "*")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @PostMapping("/session")
    public StartGameDTO createSession(@RequestBody StartGameDTO request) {
        return sessionService.createSession(request);
    }
    
}
