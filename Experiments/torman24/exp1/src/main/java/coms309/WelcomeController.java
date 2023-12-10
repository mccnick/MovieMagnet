package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }

    @GetMapping("/test")
    public String test() { return "This is a test";}

    @GetMapping("/getTest")
    public String getTest(@RequestParam(value = "username", defaultValue = "World") String message) {
        return String.format("Hello, %s! You sent a get request with a parameter!", message);
    }

}

/* Notes:
*
* SerpAPI Key - 7706019b8b7ecb7a323af90882018f4f079cb82ecf51822da4309647611b99e2
*
* */