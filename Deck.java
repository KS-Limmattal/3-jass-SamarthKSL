import java.util.Random;
import java.util.Arrays;

/**
 * Diese Klasse repräsentiert einen Kartenstapel mit einer variablen Anzahl
 * Karten
 * Sie soll ein Array cards von Typ Card als Instanzvariable haben,
 * - einen Konstruktor Deck(Card[] cards), welches eine Instanz bestehend aus
 * den gegebenen Karten kreiert,
 * - einen Konstruktor Deck(), welcher ein vollständiges Kartenset (4x9 Karten)
 * erzeugt,
 * - einen (trivialen) Getter getCards()
 * - eine Methode addCard(Card card), welche zum Deck eine Karte hinzufügt,
 * falls diese noch nicht im Deck enthalten ist und andernfalls eine Warnung auf
 * der Konsole ausgibt
 * - eine Methode pop(), welche die letzte Karte im Array aus dem Deck entfernt,
 * sofern Karten vorhanden sind
 * - eine Methode shuffle(), welche die Karten im Array durchmischt
 * 
 * Tipps:
 * - Um ein Array zu redimensionieren, verwende den Befehl "Arrays.copyOf" aus
 * java.util.Arrays
 * - Um eine zufällige Ganzzahl in einem gegebenen Bereich zu erzeugen, verwende
 * "rnd.nextInt", wobei "rnd" eine Instanz der Klasse "Random" aus
 * "java.util.Random" bezeichnet
 *
 */
public class Deck {
    public Suit trumpf;

    private Card[] cards;

    public Deck(Card[] cards) {
        this.cards = Arrays.copyOf(cards, cards.length);
    }

    public Deck() {
        this.cards = new Card[0];
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.addCard(new Card(suit, rank));
            }
        }
    }

    public Card[] getCards() {
        return Arrays.copyOf(cards, cards.length);
    }

    public void addCard(Card card) {
        for (Card c : cards) {
            if (c.equals(card)) {
                System.out.println("Warnung: Karte" + card + "bereits im Deck vorhanden!");
                return;
            }
        }
        cards = Arrays.copyOf(cards, cards.length + 1);
        cards[cards.length - 1] = card;
    }

    public Card pop() {
        if (cards.length == 0) {
            return null;
        }
        Card lastCard = cards[cards.length - 1];
        cards = Arrays.copyOf(cards, cards.length - 1);
        return lastCard;
    }

    public void shuffle() {
        Random rnd = new Random();
        for (int i = cards.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }   
    }

    public Card[] validCards(Deck played) {
        if (played.getCards().length == 0) {
            return getCards();
        }

        Suit startSuit = played.getCards()[0].getSuit();

        boolean hasSuit = false;
        for (Card c : getCards()) {
            if (c.getSuit() == startSuit) {
                hasSuit = true;
                break;
            }
        }

        if (!hasSuit) {
            return getCards();
        }

        Card[] valid = new Card[0];
        for (Card c : cards) {
            if (c.getSuit() == startSuit) {
                valid = Arrays.copyOf(valid, valid.length + 1);
                valid[valid.length - 1] = c;
            }
        }

        return valid;
    }
    

    }

    
