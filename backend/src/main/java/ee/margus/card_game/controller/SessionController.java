package ee.margus.card_game.controller;

import ee.margus.card_game.dto.StartGameDTO;
import ee.margus.card_game.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
