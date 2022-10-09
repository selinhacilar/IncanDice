import java.util.Random;
import java.util.Scanner;

public class Game {
    final static int MAX_ROLL = 3;

    /* I created a boolean valid and String choice. The method will ask the player if they want to roll again. The player can only use R or S
    and their lower case letters. When the method asks "Stop (S) or roll again (R)", if the player writes anything other than these letters as
    an answer, the game will warn the player to write a valid letter.
    */
    private static boolean askPlayerRollAgain() {
        Scanner sc = new Scanner(System.in);
        String choice;
        boolean valid;
        do {
            valid = false;
            System.out.println("Stop (S) or roll again (R)?");
            choice = sc.nextLine().toUpperCase();
            //If the choices are not R,r, S or s; the game will ask player to write a valid letter
            if (!(choice.equals("R") || choice.equals("r") || choice.equals("S") || choice.equals("s"))) {
                System.out.println("Please enter a valid letter");
                valid = true;
            }
        } while (valid);
        //If the choice is R or r, the player will roll again. Else, the player will stop rolling
        if (choice.equals("R") || choice.equals("r"))
            return true;
        else {
            return false;
        }
    }

    private static boolean getHumanDecision(Player player) {
        boolean rolling;
        if (player.getTurnRolls() < MAX_ROLL) {
            rolling = askPlayerRollAgain();
            if (rolling) {
                player.increaseTurnRolls();
            }
        } else {
            rolling = false;
        }
        //System.out.println("rolling" + " " + rolling);
        return rolling;
    }
    /* We have to make computer play smarter. For example, it has to stop rolling when it sees 21 because it is the best score, or when it sees
     a double number because it is the second-best score. I added a parameter named rollScore to assign the value of player1's score since it
     is not assigned in playPlayerTurn() method.
     */
    private static boolean getComputerDecision(Player computerPlayer, Player humanPlayer, int rollScore) {
        boolean rolling;
        Random rnd=new Random();
        if (computerPlayer.getTurnRolls() < MAX_ROLL) {
            //System.out.printf("computerPlayer  c:%d  h:%d",computerPlayer.getTurnScore(),humanPlayer.getTurnScore());
            if(humanPlayer.getTurnScore() == 12) //Computer plays first because 12 is the score that we have used to reset the round.
            {
                //System.out.println("humanPlayer hasn't played yet");
                //if the first score is higher
                //the first score is higher, so I can stop
                if (Dice.compareDice(rollScore,computerPlayer.getTurnScore())){
                    rolling = false;
                }else if(Dice.compareDice(rollScore,computerPlayer.getTurnScore()) && computerPlayer.getTurnScore()!=12){
                    rolling = false;
                } else {
                    /* computerPlayer can play 3 rounds if humanPlayer has played 3. I wanted it to decide randomly whether it wants to roll again or
                    not if there is no special rule. I generated a random number between 0 and 2. If the random number is 0, it will continue rolling
                    and if it is not 0, it will stop rolling.
                     */
                    if(rnd.nextInt(2) == 0) {
                        rolling = true;
                    } else {
                        rolling = false;
                    }
                }
            //We compare human's and computer's score
            /*If humanPlayer's score is not 12, that means humanPlayer has already played. I compared humanPlayer's score and computerPlayer's score.
            If computerPlayer's score is higher than humanPlayer's score, it can stop rolling since he has won the round.
             */
            } else if(humanPlayer.getTurnScore()!=12 && Dice.compareDice(rollScore, humanPlayer.getTurnScore())){
                //System.out.printf("computerPlayer rolled a higher score c:%d  h:%d",computerPlayer.getTurnScore(),humanPlayer.getTurnScore());
                rolling = false;
            } else { //that means human has won the round and the computer has lost
                rolling = !Dice.compareDice(computerPlayer.getTurnScore(), humanPlayer.getTurnScore());
                //if computer has lost, I have to make rolling true for it to continue rolling in the next round
                //System.out.printf(" c:%d  h:%d tr:%d",computerPlayer.getTurnScore(),humanPlayer.getTurnScore(),humanPlayer.getTurnRolls());
            }
            if (rolling) {
                //If rolling is true, I increased turnRolls
                computerPlayer.increaseTurnRolls();
            }
        } else {
            rolling = false;
        }
        return rolling;
    }

    /**
     * Handles the human player turn
     * @param player1 current player
     * @param numRolls - set by the opposite player
     * @return how many rolls taken
     */

    private static int playPlayerTurn(Player player1, Player player2, int numRolls) {
        boolean rolling = true;
        player1.resetTurnRolls();
        while (rolling && player1.getTurnRolls() <= numRolls) {
            System.out.println(player1.getName() + " roll number " + player1.getTurnRolls() + " of " + numRolls);
            int rollScore = Dice.getDiceScore();
            System.out.println("Score is " + rollScore);

            if (player1.getPlayerID() == playerType.HUMAN) {
                rolling = getHumanDecision(player1);
            } else {
                rolling = getComputerDecision(player1, player2,rollScore);
            }
            //System.out.println("numRolls" + numRolls);
            player1.setTurnScore(rollScore);

        }
        System.out.println("");
        return player1.getTurnRolls();
    }

    /**
     * Formatted lose message
     * @param losePlayer
     * @param winPlayer
     */
    private static void outputLoseMessage(Player losePlayer, Player winPlayer) {
        losePlayer.reduceLife();
        String winnerStr = winPlayer.getName() + " wins round ";
        if (winPlayer.getTurnScore() == Dice.INCA) {
            winnerStr += " with an Inca (21) ";
        } else
            winnerStr += winPlayer.getTurnScore();
        System.out.println(winnerStr
                + " beats "
                + losePlayer.getTurnScore()
                + ". "
                + losePlayer.getName()
                + " has "
                + losePlayer.getLives()
                + " lives left!\n");
    }

    /**
     * Checks the winner according to the rules of the game
     * @param player1
     * @param player2
     * @return number of the player that won (1 = player 1 2 = computer player)
     */
    private static playerType checkWin(Player player1, Player player2) {
        if (player1.getTurnScore() == player2.getTurnScore()) {
            System.out.println("It is a draw. Score is " + player1.getTurnScore());
        } else {
            if (Dice.compareDice(player1.getTurnScore(), player2.getTurnScore())) {
                outputLoseMessage(player2, player1);
                return playerType.HUMAN;
            } else {
                outputLoseMessage(player1, player2);
                return playerType.COMPUTER;
            }
        }
        System.out.println("");
        return playerType.HUMAN;
    }

    private static String getPlayerName() {
        System.out.println("Player 1 enter your name.");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Main game method
     * Sets up the players & loops until one player has no lives left
     */
    public static void playGame() {
        //12 is the lowest score (as 11 is a good score!)
        final int MIN_SCORE = 12;
        //playing controls the main game loop
        boolean playing = true;
        //Initialise the classes for the human and computer player
        Player humanPlayer = new Player(getPlayerName(), playerType.HUMAN);
        Player computerPlayer = new Player("Computer", playerType.COMPUTER);
        //THis sets up the condition for the human player to go first
        playerType winner = playerType.COMPUTER;
        int lastPlayerRolls = MAX_ROLL;
        while (playing) {
            //If computer player won the previous round human player (humanPlayer) goes first
            if (winner == playerType.COMPUTER) {
                lastPlayerRolls = playPlayerTurn(humanPlayer, computerPlayer, lastPlayerRolls);
                lastPlayerRolls = playPlayerTurn(computerPlayer, humanPlayer, lastPlayerRolls);
            } else {
                //If human player won the previous round computer player goes first
                lastPlayerRolls = playPlayerTurn(computerPlayer, humanPlayer, lastPlayerRolls);
                lastPlayerRolls = playPlayerTurn(humanPlayer, computerPlayer, lastPlayerRolls);
            }
            //Check if who was the winner of the last round
            winner = checkWin(humanPlayer, computerPlayer);
            //If one player has no lives the game is over...
            if (humanPlayer.getLives() == 0 || computerPlayer.getLives() == 0)
                //One of the players has won
                playing = false;
            else {
                //Sets up a new round of the game by resetting the turn scores & numRolls
                lastPlayerRolls = MAX_ROLL;
                humanPlayer.setTurnScore(MIN_SCORE);
                computerPlayer.setTurnScore(MIN_SCORE);
            }
        }
    }
}
