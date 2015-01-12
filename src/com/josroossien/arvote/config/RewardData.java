package com.josroossien.arvote.config;

import com.josroossien.arvote.reward.internal.Reward;

import java.util.List;
import java.util.Map;

public class RewardData {

    public String name;
    public String[] groups;

    public String type;
    public int mode;
    public float percentage;

    public int value;
    public int minValue;
    public int maxValue;
    public List<Integer> values;
    public Map<Integer, Double> valuesPerc;

    //Command data
    public String cmd;
    public String resultMsg;

    //Item data
    public String item;

    //Effect data
    public String effectType;
    public int amplifier;



    public RewardData() {
        //--
    }

    public RewardData(String name, String type, int mode, Reward reward) {
        this.name = name;
        this.type = type;
        this.mode = mode;
        groups = reward.getGroups();
        percentage = reward.getPercentage();

        value = reward.getVal();
        minValue = reward.getMinVal();
        maxValue = reward.getMaxVal();
        values = reward.getValues();
        valuesPerc = reward.getValuesPerc();

    }
}
