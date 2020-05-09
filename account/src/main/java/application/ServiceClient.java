package application;

import application.acc.Account;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import java.security.Security;
import java.util.HashMap;


@Component
public class ServiceClient {
    ServiceClient() {
    accounts = new HashMap<>();
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
        return "Account created";
    }

    Integer getNumAccounts() {
        return accounts.size();
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

    private HashMap<String, Account> accounts;
}
