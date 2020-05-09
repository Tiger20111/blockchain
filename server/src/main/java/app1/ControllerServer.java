package app1;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ControllerServer {
    ControllerServer() {
        this.service = new ServiceServer();
    }

    @RequestMapping(value = "/account/new/{name}/{key}", method = GET)
    public String createNewAccount(@PathVariable("name") String name,
                                   @PathVariable("key") String key) throws Exception {
        return service.createNewWallet(name, key);
    }

    @RequestMapping(value = "/account/balance/{name}", method = GET)
    public String getBalance(@PathVariable("name") String name) throws Exception {
        return "";
    }

    @RequestMapping(value = "/transaction/money_transfer/{from}/{to}/{amount}/{digital}", method = GET)
    public String moneyTransfer(@PathVariable("from") String from,
                                @PathVariable("to") String to,
                                @PathVariable("amount") String amount,
                                @PathVariable("digital") String digital) throws Exception {
        return "";
    }

    @RequestMapping(value = "/transaction/replenishment/{bank}/{to}/{amount}", method = GET)
    public String replenishmentCash(@PathVariable("bank") String from,
                                @PathVariable("to") String to,
                                @PathVariable("amount") String amount) throws Exception {
        return "";
    }

    @RequestMapping(value = "status", method = GET)
    public String status() {
        return "Work";
    }

    private final ServiceServer service;
}
