package com.congeer.game.strategy.model;

import java.util.List;

public class ShuffleData {

    private int count;

    private int start;

    private List<Integer> result;

    public int getCount() {
        return count;
    }

    public ShuffleData setCount(int count) {
        this.count = count;
        return this;
    }

    public int getStart() {
        return start;
    }

    public ShuffleData setStart(int start) {
        this.start = start;
        return this;
    }

    public List<Integer> getResult() {
        return result;
    }

    public ShuffleData setResult(List<Integer> result) {
        this.result = result;
        return this;
    }

}
