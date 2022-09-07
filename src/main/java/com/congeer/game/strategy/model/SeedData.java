package com.congeer.game.strategy.model;

import java.util.ArrayList;
import java.util.List;

public class SeedData {

    private String code = "main";

    private int count = 10;

    private int start = 0;

    private int step = 1;

    private boolean result = true;

    private List<Integer> data = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public SeedData setCode(String code) {
        this.code = code;
        return this;
    }

    public int getCount() {
        return count;
    }

    public SeedData setCount(int count) {
        this.count = count;
        return this;
    }

    public int getStart() {
        return start;
    }

    public SeedData setStart(int start) {
        this.start = start;
        return this;
    }

    public int getStep() {
        return step;
    }

    public SeedData setStep(int step) {
        this.step = step;
        return this;
    }

    public boolean isResult() {
        return result;
    }

    public SeedData setResult(boolean result) {
        this.result = result;
        return this;
    }

    public List<Integer> getData() {
        return data;
    }

    public SeedData setData(List<Integer> data) {
        this.data = data;
        return this;
    }

}
