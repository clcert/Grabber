package cl.tesis.mail;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import cl.tesis.ssl.HostCertificate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class SMTPData implements CSVWritable, JsonWritable{

    private String ip;
    private String start;
    private String help;
    private String ehlo;
//    private HostCertificate certificate;

    public SMTPData(String ip, String start, String help, String ehlo) {
        this.ip = ip;
        this.start = start;
        this.help = help;
        this.ehlo = ehlo;
    }

    public boolean supportTLS() {
        return this.ehlo.contains("STARTTLS");
    }

    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("ip");
        parameters.add("start");
        parameters.add("help");
        parameters.add("ehlo");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values =  new ArrayList<>();

        values.add(this.ip);
        values.add(this.start);
        values.add(this.help);
        values.add(this.ehlo);

        return values;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
