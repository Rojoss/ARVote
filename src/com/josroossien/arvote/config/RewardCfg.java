package com.josroossien.arvote.config;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.josroossien.arvote.ARVote;

import java.util.HashMap;
import java.util.Map;

public class RewardCfg extends EasyConfig {

    public HashMap<String, String> REWARDS = new HashMap<String, String>();

    public RewardCfg(String fileName) {
        this.setFile(fileName);

    }

    public Map<String, RewardData> getRewards() {
        Map<String, RewardData> rewards = new HashMap<String, RewardData>();
        for (String key : REWARDS.keySet()) {
            rewards.put(key, ARVote.inst().getGson().fromJson(REWARDS.get(key), RewardData.class));
        }
        return rewards;
    }

    public RewardData getReward(String name) {
        return ARVote.inst().getGson().fromJson(REWARDS.get(name), RewardData.class);
    }

    public void setReward(String name, RewardData pd) {
        REWARDS.put(name, ARVote.inst().getGson().toJson(pd, RewardData.class));
        save();
    }

    public void removeReward(String name) {
        REWARDS.remove(name);
        save();
    }
}
