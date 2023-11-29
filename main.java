import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Blackjack {
    public enum Card {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    public static class Hand {
        private List<Card> cards;

        public Hand() {
            cards = new ArrayList<>();
        }

        public void addCard(Card card) {
            cards.add(card);
        }

        public int getValue() {
            int sum = 0;
            int numAces = 0;

            for (Card card : cards) {
                switch (card) {
                    case ACE:
                        numAces++;
                        sum += 11;
                        break;
                    case TWO:
                    case THREE:
                    case FOUR:
                    case FIVE:
                    case SIX:
                    case SEVEN:
                    case EIGHT:
                    case NINE:
                    case TEN:
                        sum += card.ordinal() + 1;
                        break;
                    case JACK:
                    case QUEEN:
                    case KING:
                        sum += 10;
                        break;
                }
            }

            while (sum > 21 && numAces > 0) {
                sum -= 10;
                numAces--;
            }

            return sum;
        }

        public List<Card> getCards() {
            return cards;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            playBlackjack(scanner);

            System.out.println("Do you want to play again? (Y/N)");
            String playAgain = scanner.nextLine().toUpperCase();

            if (!playAgain.equals("Y")) {
                break;
            }
        }

        System.out.println("Thanks for playing!");
    }

    private static void playBlackjack(Scanner scanner) {
        List<Card> deck = new ArrayList<>();
        for (Card card : Card.values()) {
            for (int i = 0; i < 4; i++) {
                deck.add(card);
            }
        }

        Collections.shuffle(deck);

        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        // Deal initial cards
        playerHand.addCard(drawCard(deck));
        dealerHand.addCard(drawCard(deck));
        playerHand.addCard(drawCard(deck));
        dealerHand.addCard(drawCard(deck));

        System.out.println("Player's hand: " + playerHand.getCards() + " (Total Value: " + playerHand.getValue() + ")");
        System.out.println("Dealer's hand: " + dealerHand.getCards().get(0));

        // Player's turn
        while (playerHand.getValue() < 21) {
            System.out.println("Do you want to hit (H) or stand (S)?");
            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("H")) {
                playerHand.addCard(drawCard(deck));
                System.out.println("Player's hand: " + playerHand.getCards() + " (Total Value: " + playerHand.getValue() + ")");
            } else if (choice.equals("S")) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 'H' or 'S'.");
            }
        }

        // Dealer's turn
        while (dealerHand.getValue() < 17) {
            dealerHand.addCard(drawCard(deck));
        }

        // Display final hands
        System.out.println("Player's hand: " + playerHand.getCards() + " (Total Value: " + playerHand.getValue() + ")");
        System.out.println("Dealer's hand: " + dealerHand.getCards());

        // Determine the winner
        int playerScore = playerHand.getValue();
        int dealerScore = dealerHand.getValue();

        if (playerScore > 21) {
            System.out.println("Bust! You went over 21. Dealer wins.");
        } else if (dealerScore > 21) {
            System.out.println("Dealer busts! You win.");
        } else if (playerScore > dealerScore) {
            System.out.println("You win!");
        } else if (playerScore < dealerScore) {
            System.out.println("Dealer wins.");
        } else {
            System.out.println("It's a tie!");
        }
    }

    private static Card drawCard(List<Card> deck) {
        return deck.remove(0);
    }
}
