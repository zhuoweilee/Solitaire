/**
 * A class representing a playing card
 *
 * @author Zhuo-Wei Lee 111520570 zhlee CSE214 R07
 */
public class Card {
    private char suit;
    private String value;
    private boolean isFaceUp = false; //cards are face down be default

    /**
     * Contructs a new Card Object with a suit and value
     *
     * @param suit The suit of the new card
     *
     * @param value The value of the new card
     */
    public Card(char suit, String value){
        this.suit = suit;
        this.value = value;
    }

    /**
     * Returns the String version of the card
     *
     * @return "[XX]" if face down and actual card if face up
     */
    public String toString(){
        if(isFaceUp()) return "[" + value + suit + "]";
        else return "[XX]";
    }

    /**
     * Checks if the argument card follow the calling card in solitaire
     *
     * @param under The card to follow the current card
     *
     * @return True if the card can follow the current card, false otherwise
     */
    public boolean canBeAbove(Card under){
        String values[] = {" ","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        boolean checkSuits, checkValues = false;
        if((getSuit() == '\u2663' || getSuit() == '\u2660') && (under.getSuit() == '\u2665' || under.getSuit() == '\u2666'))
            checkSuits = true;
        else if((getSuit() == '\u2665' || getSuit() == '\u2666') && (under.getSuit() == '\u2663' || under.getSuit() == '\u2660'))
            checkSuits = true;
        else return false;

        int underVal = 0;
        for (int i = 0; i < 14; i++){
            if (under.getValue() == values[i])
                underVal = i;
        }
        for (int i = 0; i < 14; i++){
            if (getValue() == values[i] && underVal + 1== i)
                checkValues = true;
        }
        return checkSuits && checkValues;
    }

    /**
     * Returns the Character suit of the Card Object
     *
     * @return The Character suit of the Card Object
     */
    public char getSuit() {
        return suit;
    }

    /**
     * Sets the Card suit as the new input suit
     *
     * @param suit The new Character suit of the card
     */
    public void setSuit(char suit) {
        this.suit = suit;
    }

    /**
     * Returns the String value of the card
     *
     * @return The String value of the card
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets a new String value to the card
     * @param value The new String value of the card
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns a true if the card is face up, false if fase down
     *
     * @return Whether the card is face up
     */
    public boolean isFaceUp() {
        return isFaceUp;
    }

    /**
     * Sets whether the card is face up
     *
     * @param faceUp True if the card is to be face up, false for face down
     */
    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }
}
