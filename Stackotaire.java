import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A class to play Solitaire
 *
 * @author Zhuo-Wei Lee 111520570 zhlee CSE214 R07
 */
public class Stackotaire {
    static CardStack stock = new CardStack('s');
    static CardStack waste = new CardStack('w');
    static CardStack f1 = new CardStack('f');
    static CardStack f2 = new CardStack('f');
    static CardStack f3 = new CardStack('f');
    static CardStack f4 = new CardStack('f');
    static CardStack t1 = new CardStack('t');
    static CardStack t2 = new CardStack('t');
    static CardStack t3 = new CardStack('t');
    static CardStack t4 = new CardStack('t');
    static CardStack t5 = new CardStack('t');
    static CardStack t6 = new CardStack('t');
    static CardStack t7 = new CardStack('t');
    static CardStack shallow = new CardStack('t');
    static CardStack deck = new CardStack('t'); //create deck stack, arbitrary type
    static CardStack foundations[] = {f1, f2, f3, f4};
    static CardStack tableaus[] = {t1, t2, t3, t4, t5, t6, t7};

    static String values[] = {" ","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    static char suits[]    = {' ', '\u2666', '\u2663','\u2665', '\u2660'};   // {' ', '♦', '♣','♥', '♠'}

    /**
     * Creates cards and distributes them to the stacks
     */
    public static void initialize(){
        for(CardStack stack : foundations) stack.clear();
        for(CardStack stack : tableaus) stack.clear();
        stock.clear();
        waste.clear();
        shallow.clear();
        deck.clear();

        for(int i = 0; i < 4; i++){
            foundations[i].setStackNum(i + 1);
        }
        for(int i = 0; i < 7; i++){
            tableaus[i].setStackNum(i + 1);
        }
        for(int i = 1; i <= 13; i++){
            for(int j = 1; j <= 4; j++){
                Card newCard = new Card(suits[j], values[i]);
                deck.push(newCard);
            }
        }
        Collections.shuffle(deck);
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (tableaus[j].getStackNum() >= i) tableaus[j].push(deck.pop());//distribute cards to tableau piles
            }
        }
        for(int i = 0; i < 7; i++){
            tableaus[i].peek().setFaceUp(true);//set the last cards in the tableau as face up
            CardStack temp = new CardStack('t');//place all tableau cards into shallow stack
            temp = (CardStack)tableaus[i].clone();
            while(!temp.isEmpty()){
                shallow.push(temp.pop());
            }
        }

        while(!deck.isEmpty()){//put rest of cards in the deck into stock
            stock.push(deck.pop());
        }
    }

    /**
     * Prints out the board of cards
     */
    public static void displayBoard(){
        f1.printStack(f1.getType());
        f2.printStack(f2.getType());
        f3.printStack(f3.getType());
        f4.printStack(f4.getType());
        System.out.print("     W1 ");
        waste.printStack(waste.getType());
        System.out.print("    ");
        stock.printStack(stock.getType());
        System.out.print(stock.size() + "\n\n");

        System.out.print("T7 ");
        t7.printStack(t7.getType());
        System.out.print("\nT6 ");
        t6.printStack(t6.getType());
        System.out.print("\nT5 ");
        t5.printStack(t5.getType());
        System.out.print("\nT4 ");
        t4.printStack(t4.getType());
        System.out.print("\nT3 ");
        t3.printStack(t3.getType());
        System.out.print("\nT2 ");
        t2.printStack(t2.getType());
        System.out.print("\nT1 ");
        t1.printStack(t1.getType());
    }

    /**
     * Checks to see if the tableau is all face up
     *
     * @return True if the tableau cards are all face up, false otherwise
     */
    public static boolean hasWon(){//check shallow CardStack for face down cards
        CardStack temp = (CardStack)shallow.clone();
        while(!temp.isEmpty()){
            if(!temp.pop().isFaceUp()) return false;
        }
        return true;
    }
    public static void main(String[] args){
        initialize();
        String prompt = "Enter a command: ";
        Scanner scan = new Scanner(System.in);

        while(true){//loop indefinitely until user quits and confirms
            displayBoard();
            System.out.print("\n\n" + prompt);
            String input = scan.nextLine();
            input.toLowerCase();
            String[] arguments = input.split(" ");
            input = arguments[0];
            if(input.equals("draw")) {
                if(waste.isEmpty() && stock.isEmpty()) //if both stock and waste are empty do nothing
                    System.out.println("Cannot draw; stock and waste are empty!");

                else if (stock.isEmpty()) { //if only stock is empty, move cards in waste back to stock and draw
                    while (!waste.isEmpty()){
                        waste.peek().setFaceUp(false);
                        stock.push(waste.pop());
                    }
                    stock.peek().setFaceUp(true);
                    waste.push(stock.pop());
                }
                else{
                    stock.peek().setFaceUp(true);
                    waste.push(stock.pop());//otherwise there must be cards in the stock to draw
                }
            }

            else if (input.equals("move")) {
                if(arguments.length != 3) {
                    System.out.println("Invalid number of input arguments!");
                    continue;
                }
                if(arguments[1].length() != 2 || arguments[2].length() != 2) { //check length of input arguments
                    System.out.println("Invalid input arguments!");
                    continue;
                }
                ArrayList<String> possibleArgs = new ArrayList<String>(Arrays.asList("w1", "f1", "f2", "f3",
                        "f4", "t1", "t2", "t3", "t4", "t5", "t6", "t7"));
                boolean isValidStack1 = false, isValidStack2 = false; //represents first and second arguments
                for(String s : possibleArgs){
                    if(arguments[1].equals(s))
                        isValidStack1 = true;
                    if(arguments[2].equals(s))
                        isValidStack2 = true;
                }
                if(!isValidStack1 || !isValidStack2){
                    System.out.println("Invalid stack inputs!");
                    continue;
                }

                int firstIndex = Character.getNumericValue(arguments[1].charAt(1)) - 1;
                int secondIndex = Character.getNumericValue(arguments[2].charAt(1)) - 1;
                CardStack sourceStack, destStack;
                if (arguments[1].charAt(0) == 'f')
                    sourceStack = foundations[firstIndex];//must be foundation or tableau or waste
                else if (arguments[1].charAt(0) == 'w') sourceStack = waste;
                else sourceStack = tableaus[firstIndex];
                if (arguments[2].charAt(0) == 'f')
                    destStack = foundations[secondIndex];//must be either foundation or tableau
                else destStack = tableaus[secondIndex];

                if (sourceStack.isEmpty())//check if source stack is empty
                    System.out.println("Cannot move cards from empty source stack!");
                else if (arguments[1].equalsIgnoreCase(arguments[2]))
                    System.out.println("Cannot move. The source and destination stacks are the same!");// check if stacks are the same
                else {//check for validity for foundation and tableau destination stacks

                    if (arguments[2].charAt(0) == 't') { //check tableau
                        if (destStack.isEmpty() && !sourceStack.peek().getValue().equals("K"))//check if king is moved to dest stack
                        System.out.println("Only a King can be moved to an empty stack!");
                        else if (!destStack.isEmpty() && destStack.peek().getValue().equals("A"))
                            System.out.println("Nothing can follow an Ace in the tableau! Place the Ace in a foundation stack.");
                        else if (!destStack.isEmpty() && !destStack.peek().canBeAbove(sourceStack.peek()))
                            System.out.println("The source card cannot follow the card in the destination stack!");
                        else {
                            destStack.push(sourceStack.pop());//move is valid for tableau
                            if(!sourceStack.isEmpty())
                                sourceStack.peek().setFaceUp(true);
                        }
                    }
                    else if (arguments[1].charAt(0) == 'f' && arguments[2].charAt(0) == 'f')
                        System.out.println("Cannot move cards between foundations stacks!");


                    else if (destStack.isEmpty()) {//if foundation is empty
                        if (sourceStack.peek().getValue().equals("A")) {
                            destStack.push(sourceStack.pop());
                            if(!sourceStack.isEmpty()) sourceStack.peek().setFaceUp(true);
                        }
                        else
                            System.out.println("Only an Ace can be moved to an empty foundation stack!");
                    }
                    else {
                        if (destStack.peek().getValue().equals("K"))
                            System.out.println("Nothing can follow a King in the foundation stack!");

                        else if (destStack.peek().getSuit() != sourceStack.peek().getSuit())
                            System.out.println("The cards in a foundation stack must have the same suit!");
                        else {
                            int lowerNumIndex = 0, higherNumIndex = 0;
                            boolean canFollow;
                            for (int i = 0; i < 14; i++) {//determine if the values are valid
                                if (destStack.peek().getValue() == values[i])
                                    lowerNumIndex = i;
                            }
                            for (int i = 0; i < 14; i++) {
                                if (sourceStack.peek().getValue() == values[i])
                                    higherNumIndex = i;
                            }
                            if (higherNumIndex == lowerNumIndex + 1) canFollow = true;
                            else canFollow = false;
                            if (!canFollow)
                                System.out.println("The card value must be one higher than the card in the foundation!");
                            else {
                                destStack.push(sourceStack.pop());
                                if (!sourceStack.isEmpty()) sourceStack.peek().setFaceUp(true);
                            }
                        }
                    }
                }
            }

            /*
            check if both stacks are the same
            1.Check if numcards is greater than stack size or is not positive
            2.Check if source stack is empty
            3.Check if dest last card is Ace
            4.Check if moved cards are all face up
            5.If dest stack is empty then bottom card of numCards stack must be king
            6.Check if first of movecards can follow dest last card
             */
            else if (input.equals("moven")){
                if(arguments.length != 4) {//check number of input arguemnts
                    System.out.println("Invalid number of input arguments!");
                    continue;
                }
                if(arguments[1].length() != 2 || arguments[2].length() != 2 || (arguments[3].length() !=1 && arguments[3].length() != 2)) { //check length of input arguments
                    System.out.println("Invalid input arguments!");
                    continue;
                }
                if(arguments[1].charAt(0) != 't' || arguments[2].charAt(0) != 't') {//check first letter of input args is 't'
                    System.out.println("Must select tableau stacks for moven command!");
                    continue;
                }
                if(Character.getNumericValue(arguments[1].charAt(1)) < 1 || Character.getNumericValue(arguments[2].charAt(1)) > 7){
                    System.out.println("Tableau stacks range from 1-7 only.");
                    continue;
                }
                int numCards = Integer.parseInt(arguments[3]);
                int firstIndex = Character.getNumericValue(arguments[1].charAt(1)) - 1;
                int secondIndex = Character.getNumericValue(arguments[2].charAt(1)) - 1;

                if(numCards > tableaus[firstIndex].size())//check validity of numCards
                    System.out.println("The number of cards to be moved is larger than the source stack size!");
                else if(numCards <= 0) //numCards must be positive
                    System.out.println("Enter a positive number of cards to move!");
                else if(tableaus[firstIndex].isEmpty())//check if source stack is empty
                    System.out.println("Cannot move cards from empty source stack!");
                else if(arguments[1].equalsIgnoreCase(arguments[2]))
                    System.out.println("Cannot move. The source and destination stacks are the same!");// check if stacks are the same
                else if(!tableaus[secondIndex].isEmpty() && tableaus[secondIndex].peek().getValue().equals("A"))
                    System.out.println("Nothing can follow an Ace in the tableau! Place the Ace in a foundation stack.");
                else {
                    ArrayList<Card> moveCards = new ArrayList<Card>();
                    moveCards.addAll(tableaus[firstIndex]);
                    moveCards.subList(0, tableaus[firstIndex].size() - numCards).clear();//create arraylist with cards to be moved
                    boolean allFaceUp = true;
                    for (Card card : moveCards) {
                        if (!card.isFaceUp())
                            allFaceUp = false;
                    }
                    if (!allFaceUp) //Check if cards to be moved are all face up
                        System.out.println("The cards to be moved are not all face up!");
                    else if (tableaus[secondIndex].isEmpty() && !moveCards.get(0).getValue().equals("K"))//first card in move cards must be king if dest is empty
                            System.out.println("Only a King can be moved to a blank spot");
                    else if (!tableaus[secondIndex].isEmpty() && !tableaus[secondIndex].peek().canBeAbove(moveCards.get(0)))
                        System.out.println("Those cards cannot follow the card in the destination stack!");
                    else {
                        for (Card card : moveCards) {//Cards can be moved
                            tableaus[secondIndex].push(card);
                            tableaus[firstIndex].pop();
                            if (!tableaus[firstIndex].isEmpty()) tableaus[firstIndex].peek().setFaceUp(true);
                        }
                    }
                }

            }
            else if (input.equals("restart")){
                while(!input.equals("y") || !input.equals("n")) {
                    System.out.print("Do you want to start another game? (Y/N): ");
                    input = scan.nextLine();
                    input.toLowerCase();
                    if (input.equals("y")) {
                        initialize();
                        break;
                    }
                    else if (input.equals("n")) break;
                    else System.out.println("Enter \"Y\" or \"N\"");
                }
            }
            else if (input.equals("quit")){
                while(!input.equals("y") || !input.equals("n")) {
                    System.out.print("Do you want to quit? (Y/N): ");
                    input = scan.nextLine();
                    input.toLowerCase();
                    if (input.equals("y") || input.equals("n")) break;
                    else System.out.println("Enter \"Y\" or \"N\"");
                }
                if(input.equals("y")){
                    System.out.println("\nSorry you lose.\n");
                    break;
                }
            }
            else {
                System.out.println("Enter a valid option");
            }
            if(hasWon()){//If the user wins, offer to play again.
                System.out.println("Congratulations, you win! (You win when all cards in the tableau are face up!)");
                while(!input.equals("y") || !input.equals("n")) {
                    System.out.print("Do you want to play again? (Y/N): ");
                    input = scan.nextLine();
                    input.toLowerCase();
                    if (input.equals("y") || input.equals("n")) break;
                    else System.out.println("Enter \"Y\" or \"N\"");
                }
                if(input.equals("n")) break;
                else initialize();
            }
        }
        System.out.print("Program terminating...");
    }
}
