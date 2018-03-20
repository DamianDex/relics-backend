package com.relics.backend.controller;

import com.relics.backend.model.GeographicLocation;
import com.relics.backend.repository.RelicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GeographicLocationController {

    @Autowired
    RelicRepository relicRepository;

    @GetMapping("/geographic/{id}")
    @ResponseBody
    public GeographicLocation getGeographicForRelicId(@PathVariable(value = "id") Long id) {
        return relicRepository.findOne(id).getGeographicLocation();
    }
}
