package com.relics.backend.distance.utils;

import com.relics.backend.model.Relic;
import com.relics.backend.repository.RelicRepository;

import java.util.List;
import java.util.concurrent.Callable;

public class RelicsInFrameQueryParallel implements Callable<List<Relic>> {

    private final RelicRepository relicRepository;
    private final double[] frame;

    public RelicsInFrameQueryParallel(double[] frame, RelicRepository relicRepository){
        this.frame = frame;
        this.relicRepository = relicRepository;
    }

    @Override
    public List<Relic> call() throws Exception {
        return relicRepository.getRelicsByLocation(frame[0], frame[1], frame[2], frame[3]);
    }
}
