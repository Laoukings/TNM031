
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Lab2 {

    public static void main(String[] args) throws IOException {
        // to convert an integer b into a BigInteger
        int b = 170;
        BigInteger bigB = new BigInteger(String.valueOf(b));
        System.out.println("Hello, World!");

        //	 	to read a string from keyboard
        String input = (new BufferedReader(new InputStreamReader(System.in))).readLine();

        //	 	to convert a string s into a BigInteger
        String s = "abc";
        BigInteger c = new BigInteger(s.getBytes());

        // to convert a BigInteger back to a string	
        BigInteger a;
        //String s = new String(a.toByteArray());

    }

}
