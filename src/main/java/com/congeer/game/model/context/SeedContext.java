package com.congeer.game.model.context;

import java.util.ArrayList;
import java.util.List;

public class SeedContext extends RoomContext {

    private String code = "main";

    private int count = 10;

    private int start = 0;

    private int step = 1;

    private boolean result = true;

    private List<Integer> data = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public SeedContext setCode(String code) {
        this.code = code;
        return this;
    }

    public int getCount() {
        return count;
    }

    public SeedContext setCount(int count) {
        this.count = count;
        return this;
    }

    public int getStart() {
        return start;
    }

    public SeedContext setStart(int start) {
        this.start = start;
        return this;
    }

    public int getStep() {
        return step;
    }

    public SeedContext setStep(int step) {
        this.step = step;
        return this;
    }

    public boolean isResult() {
        return result;
    }

    public SeedContext setResult(boolean result) {
        this.result = result;
        return this;
    }

    public List<Integer> getData() {
        return data;
    }

    public SeedContext setData(List<Integer> data) {
        this.data = data;
        return this;
    }

}
