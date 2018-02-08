package com.relics.backend.controller;

import com.relics.backend.model.Relic;
import com.relics.backend.repository.RelicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RelicController {

    @Autowired
    private RelicRepository relicRepository;

    @PostMapping("/relics")
    public void createNewRelic(@Valid @RequestBody Relic relic) {
        relicRepository.save(relic);
    }

    @GetMapping("/relics")
    @ResponseBody
    public List<Relic> getAllRelics() {
        return relicRepository.findAll();
    }
}
