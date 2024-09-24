// A client-side class that uses a secure TCP/IP socket

import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.ssl.*;
import java.util.*;

public class SecureAdditionClient {

    private InetAddress host;
    private int port;
    // This is not a reserved port number
    static final int DEFAULT_PORT = 8189;
    // static final String KEYSTORE = "LIUkeystore.ks";
    // static final String TRUSTSTORE = "LIUtruststore.ks";
    static final String KEYSTOREPASS = "123456";
    static final String TRUSTSTOREPASS = "abcdef";

    static final String KEYSTORE = "Lab3\\src\\client\\LIUkeystore.ks";
    static final String TRUSTSTORE = "Lab3\\src\\client\\LIUtruststore.ks";

    // Constructor @param host Internet address of the host where the server is
    // located
    // @param port Port number on the host where the server is listening
    public SecureAdditionClient(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    // The method used to start a client object
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
            SSLSocketFactory sslFact = sslContext.getSocketFactory();
            SSLSocket client = (SSLSocket) sslFact.createSocket(host, port);
            client.setEnabledCipherSuites(client.getSupportedCipherSuites());
            System.out.println("\n>>>> SSL/TLS handshake completed");

            BufferedReader socketIn;
            socketIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter socketOut = new PrintWriter(client.getOutputStream(), true);

            System.out.println("Select option\nOption 1: download\nOption 2: upload\nOption 3: delete");

            Scanner scan = new Scanner(System.in);
            String option = scan.nextLine();
            String filename = "";

            socketOut.println(option);
            switch (option) {
                case "1": // download
                    System.out.println("Selected download\nWhich file do you want to download");
                    filename = scan.nextLine();
                    socketOut.println(filename);

                    try {
                        // String serverFilename = socketIn.readLine();
                        File downloadedFile = new File("./Lab3/src/client/files/" + filename);
                        System.out.println("downloading file: " + filename);
                        downloadedFile.createNewFile();

                        FileWriter writer = new FileWriter(downloadedFile);
                        String line1;

                        while ((line1 = socketIn.readLine()) != null) {
                            writer.write(line1 + "\n");
                        }

                        writer.close();
                    } catch (Exception e) {
                        System.out.println("Skibidi");
                        e.printStackTrace();
                    }

                    // sending server the name of the file
                    // socketOut.println(filename);

                    // about to recive the file from the server
                    // String line;

                    break;
                case "2": // upload
                    System.out.println("Selected upload\nWhich file do you want to upload");
                    filename = scan.nextLine();
                    socketOut.println(filename);

                    File file = new File("./Lab3/src/client/files/");

                    BufferedReader reader = new BufferedReader(new FileReader(file + "/" + filename));
                    String line2;
                    while ((line2 = reader.readLine()) != null) {
                        System.out.println(line2);
                        socketOut.println(line2);
                    }
                    reader.close();

                    break;
                case "3": // delete
                    System.out.println("Selected delete\nWhich file do you want to delete");
                    filename = scan.nextLine();
                    socketOut.println(filename);

                    System.out.println("Response: " + socketIn.readLine());
                    break;

                default:
                    System.out.println("default case bozo");
                    break;
            }
            scan.close();
            socketOut.close();
            // String numbers = "1.2 3.4 5.6";
            // System.out.println(">>>> Sending the numbers " + numbers + " to
            // SecureAdditionServer");
            // socketOut.println(numbers);
            // System.out.println(socketIn.readLine());

            // socketOut.println("");
        } catch (Exception x) {
            System.out.println(x);
            x.printStackTrace();
        }
    }

    // The test method for the class @param args Optional port number and host name
    public static void main(String[] args) {
        try {
            InetAddress host = InetAddress.getLocalHost();
            int port = DEFAULT_PORT;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            if (args.length > 1) {
                host = InetAddress.getByName(args[1]);
            }
            SecureAdditionClient addClient = new SecureAdditionClient(host, port);
            addClient.run();
        } catch (UnknownHostException uhx) {
            System.out.println(uhx);
            uhx.printStackTrace();
        }
    }
}

// trustor holds the place where you validate your keystore authentication
// getsupportedciphersuites holds the type of encypting the cliet/server has
// setEnabledciphersuites tells the client or server which ciphers it supports
// so the client/server can choose.