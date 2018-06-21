package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class RouteBuffer {

    private double[][] routeArray;
    private double buffer;

    public double[][] getRouteArray() {
        return routeArray;
    }

    public void setRouteArray(double[][] routeArray) {
        this.routeArray = routeArray;
    }

    public double getBuffer() {
        return buffer;
    }

    public void setBuffer(double buffer) {
        this.buffer = buffer;
    }
}
