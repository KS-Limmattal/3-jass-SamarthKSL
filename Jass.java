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
        for (Card c : deck.getCards()) {
        System.out.println(c);
        }
     }
 }








