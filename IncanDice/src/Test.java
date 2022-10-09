public class Test {
    public static void TestCompareAll() {
        System.out.println("Testing dice roll");
        for (int i = 0; i < 100; i++) {
            System.out.println("Dice roll no: " + i + ". Roll " + Dice.getDiceScore());
        }

        System.out.println("Testing all scores");
        for (int i = 11; i <= 66; i++) {
            for (int j = 11; j <= 66; j++) {
                if ((!(i % 10 == 0) && !(j % 10 == 0)) && i != j)
                    System.out.println("Testing " + i + " with " + j + " result = " + Dice.compareDice(i,j));
            }
        }
    }
}
