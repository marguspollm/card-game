package ee.margus.card_game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private final List<Card> cards;
    private final Random random;

    public Deck(Random random) {
        this.random = random;
        this.cards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Card draw(Card previous) {
        if (previous == null) {
            return cards.get(random.nextInt(cards.size()));
        }
        List<Card> possibleNextCards = cards.stream().filter(card -> !card.equals(previous)).toList();

        return possibleNextCards.get(random.nextInt(possibleNextCards.size()));
    }

    public List<Card> getCards() {
        return cards;
    }
}