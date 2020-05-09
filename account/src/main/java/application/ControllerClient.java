package application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ControllerClient {
    @Value("${app.address.server}")
    String urlServer;

    ControllerClient() {
        this.service = new ServiceClient();
    }

    @RequestMapping(value = "/account/new/{name}", method = GET)
    public String createNewAccount(@PathVariable("name") String name) throws Exception {
        return service.addAccount(name);
    }

    @RequestMapping(value = "/transaction/money_transfer/{from}/{to}/{amount}", method = GET)
    public String moneyTransfer(@PathVariable("from") String from,
                                @PathVariable("to") String to,
                                @PathVariable("amount") String amount) throws Exception {
        return "";
    }

    @RequestMapping(value = "/account/balance/{name}", method = GET)
    public String moneyTransfer(@PathVariable("name") String name) throws Exception {
        return "";
    }

    @RequestMapping(value = "status", method = GET)
    public String status() {
        return "Work";
    }

    @RequestMapping(value = "accounts/names", method = GET)
    public String getNamesAccounts() {
        return service.getNamesAccounts();
    }

    @RequestMapping(value = "accounts/number", method = GET)
    public Integer getNumAccounts() {
        return service.getNumAccounts();
    }

    private final ServiceClient service;
}
