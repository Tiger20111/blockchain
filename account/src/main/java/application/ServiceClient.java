package application;

import application.acc.Account;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;


@Component
public class ServiceClient {
    ServiceClient() {
    accounts = new HashMap<>();
    banks = new ArrayList<>();
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    String addAccount(String name) {
        if (accounts.containsKey(name)) {
            return "Account is already exist";
        }
        Account account = new Account(name);
        accounts.put(name, account);
        postCreateAccount(name, account.getPublicKey());
        return "Account created";
    }

    private void postCreateAccount(String name, PublicKey publicKey) {
        String request = urlServer + "account/new/" + name + "/" + encodePublicKey(publicKey);

        try {
            getUrl(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Integer getNumAccounts() {
        return accounts.size();
    }

    String transferMoney(String from, String to, Double amount) {
        return "Complete";
    }

    String createBank(String bank) throws IOException {
        if (banks.contains(bank)) {
            return "Already exist";
        }

        banks.add(bank);
        String request = urlServer + "bank/new/" + bank;
        return getUrl(request);
    }

    String getBankNames() {
        StringBuilder names = new StringBuilder();
        int num = 0;
        for (String name : banks) {
            if (num != 0) {
                names.append(", ");
            }
            names.append(name);
            num++;
        }
        return names.toString();
    }

    Double getBalance(String name) throws IOException {
        String request = urlServer + "account/balance/" + name;
        String textResponse = getUrl(request);
        return Double.parseDouble(textResponse);
    }

    String getNamesAccounts() {
        StringBuilder names = new StringBuilder();
        int num = 0;
        for (String name : accounts.keySet()) {
            if (num != 0) {
                names.append(", ");
            }
            names.append(name);
            num++;
        }
        return names.toString();
    }

    void setUrlServer(String urlServer) {
        this.urlServer = urlServer;
    }

    String replenishmentAccount(String bank, String wallet, Double amount) throws IOException {
        String request = urlServer + "/transaction/replenishment/" + bank + "/" + wallet + "/" + amount;
        return getUrl(request);
    }

    private String encodePublicKey(PublicKey publicKey) {
        Base64.Encoder encoder = Base64.getEncoder();
        String keyPub = encoder.encodeToString(publicKey.getEncoded());
        return keyPub.replace('/', '$');
    }

    private String getUrl(String url) throws IOException {
        StringBuffer response;
        try {

            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            throw new RuntimeException("wrong address: " + url);
        }
        return response.toString();
    }

    private HashMap<String, Account> accounts;
    private String urlServer;
    private ArrayList<String> banks;
}
