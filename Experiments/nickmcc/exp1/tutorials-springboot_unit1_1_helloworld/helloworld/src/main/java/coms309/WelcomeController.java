package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Please enter a movie name:";
    }


    @GetMapping

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Please enter a movie name: " + name;
    }
}
