package com.relics.backend.controller;

import com.relics.backend.model.Relic;
import org.springframework.http.ResponseEntity;

public interface BasicController {

    default ResponseEntity<Relic> getNotFoundResponseEntity() {
        return ResponseEntity.notFound().build();
    }

    default ResponseEntity<Boolean> getUpdateFailedEntity(){
        return ResponseEntity.notFound().build();
    }

}
