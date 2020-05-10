package application;

import application.acc.Account;
import application.transaction.Transaction;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import java.io.*;
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

    String transferMoney(String from, String to, Double amount) throws IOException {
        Transaction transaction = new Transaction(from, to, "", amount);
        String transactionStr = encodeTransaction(transaction);
        return "Complete";
    }

    String encodeTransaction(Serializable transaction) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( transaction );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
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
