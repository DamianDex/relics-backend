package com.relics.backend.controller;

import com.relics.backend.repository.RelicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RelicController {

    @Autowired
    private RelicRepository relicRepository;

    @GetMapping("/relics")
    public void getAllRelics() {
        System.out.println("Here");
    }
}
