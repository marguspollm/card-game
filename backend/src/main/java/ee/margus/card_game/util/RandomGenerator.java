package ee.margus.card_game.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
