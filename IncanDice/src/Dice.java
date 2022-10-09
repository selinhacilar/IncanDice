import java.security.SecureRandom;

public class Dice {
    public static final int INCA = 21;

    /* I created two integers named highDigit and lowDigit. if num1 is greater than num2; num1 becomes the highDigit so num2 becomes the
    lowDigit. Now I have to return these 2 numbers as 1 integer. To do that, I multiplied the highDigit by 10 and added the lowDigit;
    so it became one single integer.
     */
    private static int putGreatestDigitFirst(int num1, int num2){
        int highDigit = num1 > num2 ? num1 : num2;
        int lowDigit = num1 < num2 ? num1 : num2;
        return 10 * highDigit + lowDigit;
    }

    /* I want to generate a number of dice which is between 1 and 6. I used SecureRandom class to generate the random number. Then I returned
    the number
     */
    private static int getDiceRoll(){
        SecureRandom secureRandom = new SecureRandom();
        int random = 1 + secureRandom.nextInt(6);
        return random;
    }

    /* I generated 2 numbers by calling the getDiceRoll method and returned the two integers as one integer by calling and returning
    the putGreatestDigitFirst method
     */
    public static int getDiceScore(){
        int dice1 = getDiceRoll();
        int dice2 = getDiceRoll();
        return putGreatestDigitFirst(dice1, dice2);
    }

    /* I created 2 integers as digit1 and digit2. digit1 is going to be the units digit and digit2 will be the tens digit. If they are the
     * same number, that means the score is double and I can return the numbers.
     */
    public static boolean isDouble(int score){
       int digit1 = score % 10;
       int digit2 = (score/10) % 10;
       return digit1 == digit2;
    }

    /* if the score1 is 21, that means it is the highest score and I can return true. If score2 is 21, we return false because score2 beats
    score1. If both of the scores are doubles, we return the highest one as score1. If score 1 is double, I return true because double beats any
    number in descending order and if score2 is double, I return false because score2 beats score1. Else, now that the rest of the numbers are
    in descending order; I return the higher number as score1.
     */
    public static boolean compareDice(int score1, int score2){
        if(score1 == 21) {
            return true;
        } else if(score2 == 21){
            return false;
        } else if(isDouble(score1) && isDouble(score2)){
            return score1 > score2;
        } else if(isDouble(score1)){
            return true;
        } else if(isDouble(score2)){
            return false;
        } else {
            return score1 > score2;
        }
    }
}

