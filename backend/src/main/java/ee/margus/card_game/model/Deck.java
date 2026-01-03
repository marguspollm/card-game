package ee.margus.card_game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private final List<Card> deck = new ArrayList<>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(deck);
    }

    public Card draw(Card previous) {
        Random random = new Random();
        if (previous == null) {
            return deck.get(random.nextInt(deck.size()));
        }
        List<Card> possibleNextCards = deck.stream().filter(card -> !card.equals(previous)).toList();

        return possibleNextCards.get(random.nextInt(possibleNextCards.size()));
    }
}