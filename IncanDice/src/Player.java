enum playerType {
    HUMAN,
    COMPUTER
}

public class Player {
    public static final int MAX_LIVES = 5;
    private String name;
    private int lives;
    private int turnScore;
    private int turnRolls;
    private playerType playerID;

    public Player(String name, playerType playerID) {
        this.turnScore = 0;
        this.playerID = playerID;
        this.name = name;
        this.turnScore = 0;
        this.lives = MAX_LIVES;
        this.turnRolls=turnRolls;
    }
    //Set the name
    public void setName(String name){
        this.name=name;
    }
    //Get the name
    public String getName(){
        return this.name;
    }
    //Get the turnScore
    public int getTurnScore(){
        return this.turnScore;
    }
    //Set the turnScore
    public void setTurnScore(int turnScore){
        this.turnScore=turnScore;
    }
    //Get lives
    public int getLives(){
        return this.lives;
    }
    //Get turnRolls
    public int getTurnRolls(){
        return this.turnRolls;
    }

    //Reset the turnRolls to 1
    public void resetTurnRolls(){
        turnRolls = 1;
    }

    //Increase the turnRolls by 1
    public void increaseTurnRolls(){
        turnRolls++;
    }

    // Get the player ID
    public playerType getPlayerID() {
        return this.playerID;
    }

    //Set the player ID
    public void setPlayerID(playerType playerID) {
        this.playerID = playerID;
    }

    //If lives are greater than 0, reduce lives by 1
    public void reduceLife(){
        if(lives > 0){
            lives--;
        }
    }
}



