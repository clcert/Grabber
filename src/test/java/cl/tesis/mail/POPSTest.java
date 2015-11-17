package cl.tesis.mail;

import cl.tesis.tls.HostCertificate;
import cl.tesis.tls.ScanCiphersSuites;
import cl.tesis.tls.TLS;
import junit.framework.TestCase;
import tlsNew.ScanTLSProtocols;
import tlsNew.ScanTLSProtocolsData;
import tlsNew.TLSHandshake;

import java.security.cert.X509Certificate;


public class POPSTest extends TestCase {
    public static final String HOST = "64.64.18.121";
    public static final int PORT = 995;

    public void testHandshake() throws Exception {
        TLSHandshake tlsHandshake =  new TLSHandshake(HOST, PORT);
        tlsHandshake.connect();
        X509Certificate[] certs =  tlsHandshake.getChainCertificate();

        HostCertificate hostCertificate = new HostCertificate(certs[0], true, certs);
        assertEquals(true, hostCertificate.isValidation());
        assertEquals("Go Daddy Secure Certificate Authority - G2", hostCertificate.getCertificateAuthority());
    }

    public void testAllTLSVersion() throws Exception {
        ScanTLSProtocols protocols = new ScanTLSProtocols(HOST, PORT);
        ScanTLSProtocolsData version =  protocols.scanAllProtocols();

        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(false, version.isTLS_11());
        assertEquals(false, version.isTLS_12());
    }

    public void testCipherSuite() throws Exception {
        POP3 pop =  new POP3(HOST, PORT);
        TLS tls =  new TLS(pop.getSocket());
        ScanCiphersSuites suites = tls.checkCipherSuites(null);

        assertEquals("TLS_RSA_WITH_RC4_128_SHA", suites.getMedium_ciphers());
        assertEquals("TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }
}