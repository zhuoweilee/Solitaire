import java.util.Stack;
/**
 * A class representing a card stack
 *
 * @author Zhuo-Wei Lee 111520570 zhlee CSE214 R07
 */
public class CardStack extends Stack<Card> {
    private char type;
    private int stackNum = 0; //Represents the stack number for tableau and foundation stacks

    /**
     * Constructs an empty stack
     *
     * @param type The Character type of stack
     */
    public CardStack(char type){
        super();
        this.type = type;
    }

    /**
     * Removes the top card from the stack
     *
     * @return The removed card
     */
    public Card pop(){
        return super.pop();
    }

    /**
     * Returns the top card of the stack
     *
     * @return The top card of the stack
     */
    public Card peek(){
        return super.peek();
    }

    /**
     * Adds a card to the top of the stack
     *
     * @param c The card to be added to the top of the stack
     *
     * @return The card that was added to the stack
     */
    public Card push(Card c){
        return super.push(c);
    }

    /**
     * Returns whether the stack is empty
     *
     * @return Returns true if the stack is empty, false otherwise
     */
    public boolean isEmpty(){
        return super.empty();//from Stack API
    }

    /**
     * Prints the input stack
     *
     * @param type The Character type of stack
     */
    public void printStack(char type){
        if(type == 's') {
            if (isEmpty()) {
                System.out.print("[  ]");
            } else {
                System.out.print(peek().toString());
            }
        }
        else if(type == 'w') {
            if (isEmpty()) {
                System.out.print("[  ]");
            } else {
                System.out.print(peek().toString());
            }
        }
        else if(type == 'f'){
            if(isEmpty()){
                System.out.print("[F" + stackNum + "]");
            }
            else{
                System.out.print(peek().toString());
            }
        }
        else{//must be tableau stack
            if(isEmpty()){
                System.out.print("[  ]");
            }
            else {
                CardStack temp = new CardStack('t');//create temp stack to reverse stack and print entire stack with bottom card first
                while(!isEmpty()){
                    temp.push(pop());
                }
                while(!temp.isEmpty()) {
                    System.out.print(temp.peek());
                    push(temp.pop());
                }
            }
        }
    }

    /**
     * Returns the Character type of stack
     *
     * @return The Character type of stack
     */
    public char getType(){
        return type;
    }

    /**
     * Sets the type of stack to a new type
     *
     * @param type The new Character type of stack
     */
    public void setType(char type){
        this.type = type;
    }

    /**
     * Sets the Integer stack number
     *
     * @param num The new Integer stack number
     */
    public void setStackNum(int num){
        stackNum = num;
    }

    /**
     * Returns the Integer stack number
     *
     * @return The Integer stack number
     */
    public int getStackNum(){
        return stackNum;
    }
}
