package cl.tesis.mail;


import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import cl.tesis.tls.HostCertificate;
import cl.tesis.tls.Heartbleed;
import cl.tesis.tls.ScanCiphersSuites;
import cl.tesis.tls.ScanTLSVersion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class IMAPData implements CSVWritable, JsonWritable{

    private String ip;
    private String error;
    private String banner;
    private HostCertificate certificate;
    private Heartbleed heartbleed;
    private ScanTLSVersion protocols;
    private ScanCiphersSuites ciphersSuites;

    public IMAPData(String ip) {
        this.ip = ip;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setCertificate(HostCertificate certificate) {
        this.certificate = certificate;
    }

    public void setHeartbleed(Heartbleed heartbleed) {
        this.heartbleed = heartbleed;
    }

    public void setProtocols(ScanTLSVersion protocols) {
        this.protocols = protocols;
    }

    public void setCiphersSuites(ScanCiphersSuites ciphersSuites) {
        this.ciphersSuites = ciphersSuites;
    }

    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("ip");
        parameters.add("banner");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values =  new ArrayList<>();

        values.add(this.ip);
        values.add(this.banner);

        return values;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
