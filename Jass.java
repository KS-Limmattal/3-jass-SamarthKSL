import java.util.Arrays;

/**
 * Diese Klasse ist die Hauptklasse und enthält die main-Methode
 * In der main-Methode soll eine neues (vollstänidges) Deck erzeugt
 * und gemischt werden. Dann sollen (als Testcode) von diesem Deck 30 Karten entfernt
 * werden und anschliessend das EICHELN ASS hinzugefügt werden
 * Danach sollen alle Karten auf der Konsole ausgegeben werden.
 */
public class Jass {

    // Hauptmethode
    public static void main(String[] args) {
        // Deck erzeugen
        Deck deck = new Deck(); 
        
        // Deck mischen
        deck.shuffle();
        
        // 30 Karten entfernen
        for (int i = 0; i < 30; i++){
            deck.pop();
        }
        // Eichel Ass hinzufügen
        Card eichelAss = new Card(Suit.EICHELN, Rank.ASS);
        deck.addCard(eichelAss);

        // Alle Karten ausgeben
        System.out.println();
        System.out.println(" ---- Alle Karten im Deck: ----");
        for (Card c : deck.getCards()) {
        System.out.println(c);
        }

        // ValidCards Test:
        // - Stich leer --> Alle Karten erlaubt
        // - Stich nicht leer --> Nur gespielte Farbe erlaubt
        // - Karte nicht vorhanden --> Alle Karten erlaubt

        Deck hand = new Deck(deck.getCards());

        // Stich leer
        Deck Empty = new Deck(new Card[0]);
        Card[] empty = hand.validCards(Empty);
        System.out.println();
        System.out.println("---- Gültige Karten (Fall: Stich leer):----");
        for (Card c : empty) {
            System.out.println(c);
        }

        Deck played = new Deck(new Card[0]);
        played.addCard(new Card(Suit.EICHELN, Rank.ASS));
        Card[] valid = hand.validCards(played);
        System.out.println();
        System.out.println("---- Gültige Karten (Fall: Stich nicht leer): ----");
        for (Card c : valid) {
            System.out.println(c);
        }
        
        Deck nichtVorhanden = new Deck(new Card[0]);
        nichtVorhanden.addCard(new Card(Suit.SCHELLEN, Rank.ASS));
        nichtVorhanden.addCard(new Card(Suit.ROSEN, Rank.ACHT));
        nichtVorhanden.addCard(new Card(Suit.ROSEN, Rank.BANNER));
        nichtVorhanden.addCard(new Card(Suit.SCHELLEN, Rank.NEUN));

        Deck schilten = new Deck(new Card[0]);
        schilten.addCard(new Card(Suit.SCHILTEN, Rank.OBER));

        Card[] notFound = nichtVorhanden.validCards(schilten);
        System.out.println();
        System.out.println("---- Gültige Karten (Fall: Karte nicht vorhanden): ----");
        for (Card c : notFound) {
            System.out.println(c);
        }

    }
}
  












