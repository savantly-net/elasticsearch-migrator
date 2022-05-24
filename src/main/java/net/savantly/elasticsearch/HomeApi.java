package net.savantly.elasticsearch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeApi {

    @GetMapping
    public String imok() {
        return "imok";
    }
}
