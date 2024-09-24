
// An example class that uses the secure server socket class
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.*;
import java.util.StringTokenizer;

public class SecureAdditionServer {

    private int port;
    // This is not a reserved port number
    static final int DEFAULT_PORT = 8189;
    // static final String KEYSTORE = "LIUkeystore.ks";
    // static final String TRUSTSTORE = "LIUtruststore.ks";
    static final String KEYSTOREPASS = "123456";
    static final String TRUSTSTOREPASS = "abcdef";

    // relative path to file
    static final String KEYSTORE = "Lab3\\src\\server\\LIUkeystore.ks";
    static final String TRUSTSTORE = "Lab3\\src\\server\\LIUtruststore.ks";

    /**
     * Constructor
     *
     * @param port The port where the server will listen for requests
     */
    SecureAdditionServer(int port) {
        this.port = port;
    }

    /**
     * The method that does the work for the class
     */
    public void run() {
        try {
            KeyStore ks = KeyStore.getInstance("JCEKS");
            ks.load(new FileInputStream(KEYSTORE), KEYSTOREPASS.toCharArray());

            KeyStore ts = KeyStore.getInstance("JCEKS");
            ts.load(new FileInputStream(TRUSTSTORE), TRUSTSTOREPASS.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, KEYSTOREPASS.toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLServerSocketFactory sslServerFactory = sslContext.getServerSocketFactory();
            SSLServerSocket sss = (SSLServerSocket) sslServerFactory.createServerSocket(port);
            sss.setEnabledCipherSuites(sss.getSupportedCipherSuites());

            System.out.println("\n>>>> SecureAdditionServer: active ");
            SSLSocket incoming = (SSLSocket) sss.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);

            String str = in.readLine();
            String line;

            switch (str) {
                case "1": // download
                    System.out.println("Download selected ");
                    try {

                        String fileOption = in.readLine();
                        BufferedReader reader = new BufferedReader(
                                new FileReader("./Lab3/src/server/files/" + fileOption));
                        System.out.println("File name: " + fileOption);
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            out.println(line);
                        }
                        reader.close();
                        out.close();
                    } catch (Exception e) {
                        System.out.println("File does probably not exist");
                        e.printStackTrace();
                    }

                    break;
                case "2": // upload
                    System.out.println("upload selected");
                    try {
                        File uploadedFile = new File("./Lab3/src/server/files/" + in.readLine());
                        System.out.println("File name: " + uploadedFile);
                        uploadedFile.createNewFile();
                        FileWriter whritehead = new FileWriter(uploadedFile);

                        while ((line = in.readLine()) != null) {
                            whritehead.write(line + "\n");

                        }
                        whritehead.close();
                    } catch (Exception e) {
                        System.out.println("FUCK");
                        e.printStackTrace();
                    }
                    break;
                case "3": // delete
                    System.out.println("delete selected");
                    String deleteFile = in.readLine();
                    try {
                        File file = new File("./Lab3/src/server/files/" + deleteFile);
                        if (file.delete())
                            out.println("The file " + deleteFile + " has been deleted from the server");
                        else
                            out.print("File " + deleteFile + " was not found or is already deleted");

                    } catch (Exception e) {
                        System.err.println("Delete failed kys");
                        e.printStackTrace();
                    }

                    break;

                default:
                    System.out.println("Case defaulted closing connection");
                    break;
            }

            incoming.close();
            in.close();

        } catch (Exception e) {
            System.out.println("Server Error ");
            e.printStackTrace();
        }

    }

    /**
     * The test method for the class
     *
     * @param args[0] Optional port number in place of the default
     */
    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        SecureAdditionServer addServe = new SecureAdditionServer(port);
        addServe.run();
    }
}
