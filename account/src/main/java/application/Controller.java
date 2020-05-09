package application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Value("${app.address.server}")
    String urlServer;


}
