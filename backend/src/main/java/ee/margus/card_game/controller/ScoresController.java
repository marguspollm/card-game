package ee.margus.card_game.controller;

import ee.margus.card_game.entity.Score;
import ee.margus.card_game.service.ScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ScoresController {
    @Autowired
    private ScoresService scoresService;

    @GetMapping("/scores")
    public List<Score> getAllScores() {
        return scoresService.getAll();
    }

    @GetMapping("/player/{id}/scores")
    public List<Score> getPlayerScores(@PathVariable Long id) {
        return scoresService.getPlayerScores(id);
    }
}
