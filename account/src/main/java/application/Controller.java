package application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Controller {
    @Value("${app.address.server}")
    String urlServer;

    @RequestMapping(value = "/account/new/{name}", method = GET)
    public String createNewAccount(@PathVariable("name") String login) throws Exception {
        return "";
    }

    @RequestMapping(value = "/transaction/money_transfer/{from}/{to}/{amount}", method = GET)
    public String moneyTransfer(@PathVariable("from") String from,
                                @PathVariable("to") String to,
                                @PathVariable("amount") String amount) throws Exception {
        return "";
    }
    @RequestMapping(value = "status", method = GET)
    public String status() {
        return "Work";
    }
}
