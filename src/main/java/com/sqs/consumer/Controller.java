package com.sqs.consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/teste")
    public String getTeste() {
        return "fnblkdfnbnkfdkbnkfldbdlnf";
    }
}
