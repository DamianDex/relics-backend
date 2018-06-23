package com.relics.backend.model;
import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;

import java.util.List;

public class RouteBufferResult {

    @JsonView(View.BasicDescription.class)
    private List<Relic> relics;

    @JsonView(View.BasicDescription.class)
    private double[][] bufferPoints;

    public RouteBufferResult(List<Relic> relics, double[][] bufferPoints){
        setRelics(relics);
        setBufferPoints(bufferPoints);
    }

    public List<Relic> getRelics() {
        return relics;
    }

    public void setRelics(List<Relic> relics) {
        this.relics = relics;
    }

    public double[][] getBufferPoints() {
        return bufferPoints;
    }

    public void setBufferPoints(double[][] bufferPoints) {
        this.bufferPoints = bufferPoints;
    }
}
