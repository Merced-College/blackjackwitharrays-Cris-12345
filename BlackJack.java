// Cristopher Gomez
// Abraham Gonzalez 
// Anthony Madrigal-Murillo
//1/21/25
//asigment to update blackJack
import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    // constant- cannot change their values 
    //static - I can use these in every function without having to pass them in
    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King","Ace" };     //Deck of cards reprecented as integers 
    private static final int[] DECK = new int[52];
    private static int currentCardIndex = 0;
    private static int playerbalance = 100;                          //ADDED FOR BETTING
    private static int playerBet = 0;                              //ADDED FOR BETTING
    private static boolean betPlaced = false;                      //ADDED FOR BETTING

    public static void main(String[] args) { // 
        Scanner scanner = new Scanner(System.in);


        initializeDeck(); // Initialize the deck with card values
        shuffleDeck(); // Shuffles the deck 

        int playerTotal = dealInitialPlayerCards(); // deals the initials cards to the player 
        int dealerTotal = dealInitialDealerCards(); // deals the inntial cards to the dealer 

        playerTotal = playerTurn(scanner, playerTotal); //players turn 
        if (playerTotal > 21) {
            System.out.println("You busted! Dealer wins."); // if the player has a value higher than 21 prints out "you loose"
            playerbalance -= playerBet;                                     //ADDED FOR BETTING
            System.out.println("Remaining Balance: " + playerbalance + "!"); //ADDED FOR BETTING
            return;
        }

        dealerTotal = dealerTurn(dealerTotal); //Handles the dealers turn 
        determineWinner(playerTotal, dealerTotal); // determines the winner 

        scanner.close();
    }
     //Initializing deck to integer 0 to 51
    private static void initializeDeck() {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = i;
        }
    }
       //shuffle deck using random swaps 
    private static void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < DECK.length; i++) {
            int index = random.nextInt(DECK.length); //swaping two integers in the array
            int temp = DECK[i];
            DECK[i] = DECK[index];
            DECK[index] = temp;
        }
        System.out.println("printed deck");
        for (int i = 0; i < DECK.length; i++) {
            System.out.println(DECK[i] + " ");
        }
    }
// this function of code gives out the initial cards to the player 
    private static int dealInitialPlayerCards() {
        int card1 = dealCard();
        int card2 = dealCard();
        System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4] + " and " + RANKS[card2] + " of " + SUITS[card2 / 13]);
        return cardValue(card1) + cardValue(card2);
    }
// This function gives the initial cards to the dealer
    private static int dealInitialDealerCards() {
        int card1 = dealCard();
        System.out.println("Dealer's card: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4]);
        return cardValue(card1);
    }
// This chunck of code is responsible for asking the user their prompts to start the game once
// the cards are dealt they are given two options to hit or stand, and if they decide to input
// any other commands they will recieve a prompt to only type hit or stand invalidating previous responses
    private static int playerTurn(Scanner scanner, int playerTotal) {
        while (true) {
            if(betPlaced == false){
                System.out.println("Pick a bet amount. Your current balance is " + playerbalance + "."); //ADDED FOR BETTING
                playerBet = scanner.nextInt();                                                           //ADDED FOR BETTING
                String dummy = scanner.nextLine();                                                       //ADDED FOR BETTING
                betPlaced = true;                                                                        //ADDED FOR BETTING
            }
            
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {
                int newCard = dealCard();
                playerTotal += cardValue(newCard);
                System.out.println("new card index is " + newCard);
                System.out.println("You drew a " + RANKS[newCard] + " of " + SUITS[DECK[currentCardIndex] % 4]);
                if (playerTotal > 21) {
                    break;
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return playerTotal;
    }
// Once the player's turn has ended the dealer's turn will commence and if the cards the dealer has are less than 17 he gets a new card
// but only as long as it equals his total or adds to it, once done the chunk of code will move on to the next function     
private static int dealerTurn(int dealerTotal) {
        while (dealerTotal < 17) {
            int newCard = dealCard();
            dealerTotal += cardValue(newCard);
        }
        System.out.println("Dealer's total is " + dealerTotal);
        return dealerTotal;
    }


    //determi es winner by checking if dealer busted, if the player has a higher 
    //total than the dealer, or if the dealer and player totals are equal 
     private static void determineWinner
    (int playerTotal, int dealerTotal)
     {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
            playerbalance += playerBet;                                      //ADDED FOR BETTING
            System.out.println("Remaining Balance: " + playerbalance + "!");  //ADDED FOR BETTING
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins!");
            playerbalance -= playerBet;                                     //ADDED FOR BETTING
            System.out.println("Remaining Balance:" + playerbalance + "!"); //ADDED FOR BETTING
        }
    }

    //draws next card from deck (next index in integer array)
    //modulus used to keep number between 1 - 13
    private static int dealCard() {
        return DECK[currentCardIndex++] % 13;
    }

    //determines if card value is between 1-9 or 10-12
    private static int cardValue(int card) {
        return card < 9 ? card + 2 : 10;
    }

    
    //iterates through first parametetr and reutrn value equal to the key   
    int linearSearch(int[] numbers, int key) {
        int i = 0;
        for (i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i;
            }
        }
        return -1; // not found
    }
}

