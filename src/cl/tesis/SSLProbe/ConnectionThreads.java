package cl.tesis.SSLProbe;

import cl.tesis.exception.SSLConnectionException;
import cl.tesis.exception.SSLHandshakeTimeoutException;
import cl.tesis.input.FileReader;

import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionThreads extends  Thread{
    private static final Logger logger = Logger.getLogger(ConnectionThreads.class.getName());
    private static final int IP = 0;

    private FileReader reader;

    public ConnectionThreads(FileReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String[] columns;

        while ((columns = this.reader.nextLine()) != null) {
            try {
                Certificate certificate = SSLUtil.getServerCertificate(columns[IP], false);
                System.out.println(certificate.toString());
            } catch (SSLHandshakeException e) { // Untrusted Certificate
                logger.log(Level.INFO, "Untrusted certificate {0}", columns[IP]);
//                System.out.println("HandShake " + columns[IP]);
            } catch (SSLConnectionException e) { // Problem creating the socket
                logger.log(Level.INFO, "Problem creating the soceket {0}", columns[IP]);
//                System.out.println("Problem to create the socket " + e.getMessage());
            } catch (SocketTimeoutException e) { // Connection timeout
                logger.log(Level.INFO, "Connection timeout {0}", columns[IP]);
//                System.out.println("TimeOut " + columns[IP]);
            } catch (SSLHandshakeTimeoutException e) {
                logger.log(Level.INFO, "Handshake timeout {0}", columns[IP]);
//                System.out.println("TimeOut " + columns[IP]);
            } catch (ConnectException e) { // Problem in the connection
                logger.log(Level.INFO, "Connection exception {0}", columns[IP]);
//                System.out.println("Connect Exception " + columns[IP]);
            } catch (Throwable e) { // Other errors
                logger.log(Level.INFO, "Other exception {0}", columns[IP]);
//                System.out.println("Exception " + columns[IP]);
            }

        }
    }


}
