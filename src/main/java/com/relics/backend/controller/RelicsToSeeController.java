package com.relics.backend.controller;

import com.relics.backend.model.ApplicationUser;
import com.relics.backend.model.Relic;
import com.relics.backend.model.RelicsToSee;
import com.relics.backend.repository.RelicsToSeeRepository;
import com.relics.backend.security.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:3000")
public class RelicsToSeeController {

    @Autowired
    private RelicsToSeeRepository relicsToSeeRepository;

    @Autowired
    private LoginUtils loginUtils;

    @PostMapping("/relics/relics-to-see")
    public void createNewRelicToSee(@Valid @RequestBody RelicsToSee relicsToSee){
        ApplicationUser applicationUser = loginUtils.getLoggedUser();
        Long userId = applicationUser.getId();
        Long relicId = relicsToSee.getRelic().getId();
        RelicsToSee relicsToSeeOld = relicsToSeeRepository.getRelicToSee(relicId,userId);
        if(relicsToSeeOld != null){
            relicsToSeeOld.setRelicToSee(relicsToSee.isRelicToSee());
            relicsToSeeRepository.save(relicsToSeeOld);
        } else{
            relicsToSee.setAppUser(applicationUser);
            relicsToSeeRepository.save(relicsToSee);
        }
    }

    @GetMapping("relics/{id}/isChecked")
    @ResponseBody
    public Boolean checkIfUserReviewRelic(@PathVariable(value = "id") Long id) {
        Long userId = loginUtils.getLoggedUser().getId();
        return relicsToSeeRepository.checkIfUserWantToSeeRelic(id, userId);
    }

    @GetMapping("/relics-to-see")
    @ResponseBody
    public List<BigInteger> getRelicsToSeeByUser(
            @RequestParam(value = "category", defaultValue = "%") String category,
            @RequestParam(value = "place", defaultValue = "%") String place){
        Long userId = loginUtils.getLoggedUser().getId();
        return relicsToSeeRepository.getRelicsToSeeByUser(userId,category,place);
    }


}
