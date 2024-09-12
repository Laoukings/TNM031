
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random; 


public class Lab2 {

    public static void main(String[] args) throws IOException {

        int bitLength = 256;

        Random genVal = new Random();
        // middle value is % probability to be a prime value?
        BigInteger p = new BigInteger(bitLength, 100, genVal);
        BigInteger q = new BigInteger(bitLength, 100, genVal);
        //BigInteger g = BigInteger.probablePrime(bitLength, genVal); //same thing as above

        System.out.println("är");
        // System.out.println(q);
        //System.out.println(g);

        //n = pq
        BigInteger n = p.multiply(q);

        //generate gcd(e, p-1, q-1)
        BigInteger fard = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));


        int ttt = 0;
        //find gcd of fard to get e 
        BigInteger e = fard.subtract(BigInteger.ONE); 
        while (e.gcd(fard).equals(BigInteger.ONE)) { //shid gets stuck here not correct go fix later :))
            e.subtract(BigInteger.ONE);
            ttt++;
            System.out.println(ttt);
        }
        
        // d = e^-1 mod(fard);
        BigInteger d = e.modInverse(fard);


        //Alice encrypts message
        String aliceMessage = "papi santana";
        BigInteger m = new BigInteger(aliceMessage.getBytes());

        //encrypted message
        BigInteger c = m.modPow(e, n);

        System.out.println("paj");

        //bob decrypts message
        m = c.modPow(d, n);

        //output
        System.out.println("Sauce");
        System.out.println("Message:" + aliceMessage);
        System.out.println("Encrypted Message:" + c.toString());
        System.out.println("Decrypted message: " + m.toString());


        // to convert an integer b into a BigInteger

        /*
        
        --Copy pasted

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
         */
    }

}
