package com.josroossien.arvote.reward.internal;

import com.clashwars.cwcore.utils.CWUtil;

import java.util.List;
import java.util.Map;

public class Reward {

    private String[] groups;
    private float percentage;

    protected int type = -1;

    protected int val = -1;
    protected int minVal = -1;
    protected int maxVal = -1;
    protected List<Integer> values;
    protected Map<Integer, Double> valuesPerc;

    /**
     * Create a reward without a value.
     * @param groups The reward groups.
     * @param percentage The percentage chance to get this.
     */
    public Reward(String[] groups, float percentage) {
        type = 0;
        this.groups = groups;
        this.percentage = percentage;
    }

    /**
     * Create reward with a specific value. [TYPE: 1]
     * @param groups The reward groups.
     * @param percentage The percentage chance to get this.
     * @param val The value to give.
     */
    public Reward(String[] groups, float percentage, int val) {
        type = 1;
        this.groups = groups;
        this.percentage = percentage;
        this.val = val;
    }

    /**
     * Create reward with a random value between the 2 specified numbers. [TYPE: 2]
     * @param groups The reward groups.
     * @param percentage The percentage chance to get this.
     * @param minVal The minimum value.
     * @param maxVal The maximum value.
     */
    public Reward(String[] groups, float percentage, int minVal, int maxVal) {
        type = 2;
        this.groups = groups;
        this.percentage = percentage;
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    /**
     * Create reward with a list of values and it will pick a random one out of it. [TYPE: 3]
     * @param groups The reward groups.
     * @param percentage The percentage chance to get this.
     * @param values A list with values to pick a random one out.
     */
    public Reward(String[] groups, float percentage, List<Integer> values) {
        type = 3;
        this.groups = groups;
        this.percentage = percentage;
        this.values = values;
    }

    /**
     * Create reward with a map of values and percentages.
     *
     * If addTogether is false it will only get 1 random value based on the percentage.
     * The percentages can be anything and the higher the percentage compared with the other percentages the more likely you will get that value.
     * A good example: [50 -> 2.0,  100 -> 1.5,  150 -> 1.0,  200 -> 0.5]
     *
     * If addTogether is true it will check each reward 1 by 1 and sum up all values that matched the random number.
     * so you have 0.4% chance to get 50 and if you get that and then you also get the 0.3% chance you will get 150.
     * You should always specify a value with 1.0% chance because otherwise it's not guaranteed there will be a value.
     * Unlike the addTogether false you need to make sure that all percentages are between 0.0 and 1.0
     *
     * @param groups The reward groups.
     * @param percentage The percentage chance to get this.
     * @param valuesPerc The map with values and percentages.
     * @param addTogether Should it sum up all values?
     */
    public Reward(String[] groups, float percentage, Map<Integer, Double> valuesPerc, boolean addTogether) {
        this.groups = groups;
        this.percentage = percentage;
        this.valuesPerc = valuesPerc;
        if (addTogether) {
            type = 4;
        } else {
            type = 5;
        }
    }


    /**
     * Execute the reward and give it to the specified player.
     * @param player The player to give the reward to.
     * @return String with the reward name for example '10 Diamond' or '250 Coins' returns empty string if no reward was given.
     */
    public String execute(String player) {
        return "";
    }

    /**
     * Get the value based on the type and the values specified in the constructor.
     * For example if it's mode 2 it will return a random value between the min/max value.
     * Make sure to call this once and save it locally because it most likely will return a diff value each time it's called.
     * @return Specific value for this reward.
     */
    public int getValue() {
        int value = 0;
        if (type == 1) {
            value = val;
        } else if (type == 2) {
            value = CWUtil.random(minVal, maxVal);
        } else if (type == 3) {
            value = CWUtil.random(values);
        } else if (type == 4) {
            value = 0;
            for (int v : valuesPerc.keySet()) {
                if (CWUtil.randomFloat() <= valuesPerc.get(v)) {
                    value += v;
                }
            }
        } else if (type == 5) {
            float totalPerc = 0;
            for (double p : valuesPerc.values()) {
                totalPerc += p;
            }

            double random = Math.random() * totalPerc;
            for (int v : valuesPerc.keySet()) {
                random -= valuesPerc.get(v);
                if (random <= 0.0d) {
                    value = v;
                    break;
                }
            }
        }
        return value;
    }


    /**
     * Get all groups the reward is in.
     * @return Array of groups.
     */
    public String[] getGroups() {
        return groups;
    }

    /**
     * Check if this rewards is in the specified group.
     * @param group The name of the group to check.
     * @return True if it has the group and False if not.
     */
    public boolean inGroup(String group) {
        for (String g : groups) {
            if (g.equalsIgnoreCase(group)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the percentage of this reward.
     * @return Float number between 0.0 and 1.0.
     */
    public float getPercentage() {
        return percentage;
    }



    public int getVal() {
        return val;
    }

    public int getMinVal() {
        return minVal;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public List<Integer> getValues() {
        return values;
    }

    public Map<Integer, Double> getValuesPerc() {
        return valuesPerc;
    }
}