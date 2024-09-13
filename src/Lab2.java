
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Lab2 {

    public static void main(String[] args) throws IOException {

        int bitLength = 256;

        Random genVal = new Random();
        // middle value is % probability to be a prime value?
        //Constructs a randomly generated positive BigInteger that is probably prime, with the specified bitLength.
        BigInteger p = new BigInteger(bitLength, 100, genVal);
        BigInteger q = new BigInteger(bitLength, 100, genVal);
        //BigInteger g = BigInteger.probablePrime(bitLength, genVal); //same thing as above

        //n = pq
        BigInteger n = p.multiply(q);

        //generate gcd(e, p-1, q-1)
        BigInteger fard = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        //System.out.println(fard);

        //find gcd of fard to get e 
        BigInteger e = fard.subtract(BigInteger.ONE);

        while (!e.gcd(fard).equals(BigInteger.ONE)) {
            e.subtract(BigInteger.ONE);

        }

        // d = e^-1 mod(fard);
        BigInteger d = e.modInverse(fard);

        //Alice encrypts message
        String aliceMessage = "This is secret information! ";
        BigInteger m = new BigInteger(aliceMessage.getBytes());

        //encrypted message
        BigInteger c = m.modPow(e, n);

        //bob decrypts message
        m = c.modPow(d, n);
        String decryptedmessage = new String(m.toByteArray());

        //output
        System.out.println("Message:" + aliceMessage);
        System.out.println("Encrypted Message:" + c.toString());
        System.out.println("Decrypted message: " + (decryptedmessage));

    }

}
