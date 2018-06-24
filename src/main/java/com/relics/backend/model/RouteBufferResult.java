package com.relics.backend.model;
import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;

import java.util.List;

public class RouteBufferResult {

    @JsonView(View.BasicDescription.class)
    private List<Relic> relics;

    @JsonView(View.BasicDescription.class)
    private GoogleMapsLocation[] bufferPoints;

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

    public GoogleMapsLocation[] getBufferPoints() {
        return bufferPoints;
    }

    public void setBufferPoints(double[][] bufferPoints) {
        this.bufferPoints = new GoogleMapsLocation[bufferPoints.length];
        for (int i = 0; i < bufferPoints.length; i++){
            this.bufferPoints[i] = new GoogleMapsLocation(bufferPoints[i][0], bufferPoints[i][1]);
        }
    }

    private class GoogleMapsLocation {

        @JsonView(View.BasicDescription.class)
        private final double lat;
        @JsonView(View.BasicDescription.class)
        private final double lng;

        public GoogleMapsLocation(double lat, double lng){
            this.lat = lat;
            this.lng = lng;
        }

    }

}
