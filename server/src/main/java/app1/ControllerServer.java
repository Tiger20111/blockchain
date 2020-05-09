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
    public Double getBalance(@PathVariable("name") String name) throws Exception {
        return service.getBalance(name);
    }

    @RequestMapping(value = "/bank/new/{name}", method = GET)
    public String createBank(@PathVariable("name") String name) throws Exception {
        return service.createNewBank(name);
    }

    @RequestMapping(value = "/transaction/{transaction}", method = GET)
    public String moneyTransfer(@PathVariable("transaction") String transaction) throws Exception {
        return service.pushTransaction(transaction);
    }

    @RequestMapping(value = "/transaction/replenishment/{bank}/{to}/{amount}", method = GET)
    public String replenishmentCash(@PathVariable("bank") String bank,
                                @PathVariable("to") String wallet,
                                @PathVariable("amount") String amount) throws Exception {
        return service.replenishmentAccount(bank, wallet, Double.parseDouble(amount));
    }

    @RequestMapping(value = "/miner/mine", method = GET)
    public void mineBlock() throws Exception {

    }

    @RequestMapping(value = "status", method = GET)
    public String status() {
        return "Work";
    }

    private final ServiceServer service;
}
