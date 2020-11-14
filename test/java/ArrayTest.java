import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ArrayTest {
    public static int[] arTest;
    public int [] arrayFortyOne = new int[]{4,1,4,1,1,4,1,1,1,4};


    @Before
    public static void init(){
        System.out.println("ArTest_Complited");
        arTest = new int[]{2,4,9,0,1,1,1};

    }
    @Test
    public int[] test1()throws RuntimeException{
        try {
            for (int i = arTest.length; i > 0; i--) {
                while (i !=4){
                    int[] ints = Arrays.stream(arTest).toArray();
                }
            }

        }catch (RuntimeException e){
            e.printStackTrace();
        }

        return arTest;
    }

    @Test
    public boolean test2() {
        for (int i = 0; i < arrayFortyOne.length; i++) {
            if (i != 4 & i != 1) {
                return false;

            }

        }

        return true;
    }

}
