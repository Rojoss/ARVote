package com.josroossien.arvote.reward.internal;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.helpers.CWItem;
import com.clashwars.cwcore.utils.CWUtil;
import com.josroossien.arvote.ARVote;
import com.josroossien.arvote.config.RewardData;
import com.josroossien.arvote.reward.*;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class RewardManager {

    private ARVote arv;
    private Map<String, Reward> rewards = new HashMap<String, Reward>();

    public RewardManager(ARVote arv) {
        this.arv = arv;
        populateRewards();
    }

    public void populateRewards() {
        for (RewardData rd : arv.getRewardCfg().getRewards().values()) {
            if (rd == null || rd.type == null || rd.mode < 0) {
                arv.log("Skipped reward: " + (rd != null && rd.name != null && !rd.name.isEmpty() ? rd.name : "unknown") + ". It is not configured properly.");
                continue;
            }
            if (rd.type.equalsIgnoreCase("money") || rd.type.equalsIgnoreCase("economy") || rd.type.equalsIgnoreCase("eco")) {
                switch (rd.mode) {
                    case 0:
                    case 1:
                        rewards.put(rd.name, new MoneyReward(rd.groups, rd.percentage, rd.value));
                        break;
                    case 2:
                        rewards.put(rd.name, new MoneyReward(rd.groups, rd.percentage, rd.minValue, rd.maxValue));
                        break;
                    case 3:
                        rewards.put(rd.name, new MoneyReward(rd.groups, rd.percentage, rd.values));
                        break;
                    case 4:
                    case 5:
                        boolean add = rd.mode == 4;
                        rewards.put(rd.name, new MoneyReward(rd.groups, rd.percentage, rd.valuesPerc, add));
                        break;
                }
            } else if (rd.type.equalsIgnoreCase("xp") || rd.type.equalsIgnoreCase("exp") || rd.type.equalsIgnoreCase("experience")) {
                switch (rd.mode) {
                    case 0:
                    case 1:
                        rewards.put(rd.name, new ExpReward(rd.groups, rd.percentage, rd.value));
                        break;
                    case 2:
                        rewards.put(rd.name, new ExpReward(rd.groups, rd.percentage, rd.minValue, rd.maxValue));
                        break;
                    case 3:
                        rewards.put(rd.name, new ExpReward(rd.groups, rd.percentage, rd.values));
                        break;
                    case 4:
                    case 5:
                        boolean add = rd.mode == 4;
                        rewards.put(rd.name, new ExpReward(rd.groups, rd.percentage, rd.valuesPerc, add));
                        break;
                }
            } else if (rd.type.equalsIgnoreCase("cmd") || rd.type.equalsIgnoreCase("command")) {
                switch (rd.mode) {
                    case 0:
                        rewards.put(rd.name, new CmdReward(rd.groups, rd.percentage, rd.cmd, rd.resultMsg));
                        break;
                    case 1:
                        rewards.put(rd.name, new CmdReward(rd.groups, rd.percentage, rd.value, rd.cmd, rd.resultMsg));
                        break;
                    case 2:
                        rewards.put(rd.name, new CmdReward(rd.groups, rd.percentage, rd.minValue, rd.maxValue, rd.cmd, rd.resultMsg));
                        break;
                    case 3:
                        rewards.put(rd.name, new CmdReward(rd.groups, rd.percentage, rd.values, rd.cmd, rd.resultMsg));
                        break;
                    case 4:
                    case 5:
                        boolean add = rd.mode == 4;
                        rewards.put(rd.name, new CmdReward(rd.groups, rd.percentage, rd.valuesPerc, add, rd.cmd, rd.resultMsg));
                        break;
                }
            } else if (rd.type.equalsIgnoreCase("item")) {
                switch (rd.mode) {
                    case 0:
                    case 1:
                        rewards.put(rd.name, new ItemReward(rd.groups, rd.percentage, new CWItem(rd.item)));
                        break;
                    case 2:
                        rewards.put(rd.name, new ItemReward(rd.groups, rd.percentage, rd.minValue, rd.maxValue, new CWItem(rd.item)));
                        break;
                    case 3:
                        rewards.put(rd.name, new ItemReward(rd.groups, rd.percentage, rd.values, new CWItem(rd.item)));
                        break;
                    case 4:
                    case 5:
                        boolean add = rd.mode == 4;
                        rewards.put(rd.name, new ItemReward(rd.groups, rd.percentage, rd.valuesPerc, add, new CWItem(rd.item)));
                        break;
                }
            } else if (rd.type.equalsIgnoreCase("effect") || rd.type.equalsIgnoreCase("potioneffect") || rd.type.equalsIgnoreCase("potion")) {
                PotionEffectType type = CWCore.inst().getPotionEffects().getEffect(rd.effectType);
                switch (rd.mode) {
                    case 0:
                    case 1:
                        rewards.put(rd.name, new EffectReward(rd.groups, rd.percentage, rd.value, type, rd.amplifier));
                        break;
                    case 2:
                        rewards.put(rd.name, new EffectReward(rd.groups, rd.percentage, rd.minValue, rd.maxValue, type, rd.amplifier));
                        break;
                    case 3:
                        rewards.put(rd.name, new EffectReward(rd.groups, rd.percentage, rd.values, type, rd.amplifier));
                        break;
                    case 4:
                    case 5:
                        boolean add = rd.mode == 4;
                        rewards.put(rd.name, new EffectReward(rd.groups, rd.percentage, rd.valuesPerc, add, type, rd.amplifier));
                        break;
                }
            } else {
                arv.log("Skipped reward: " + (rd.name != null && !rd.name.isEmpty() ? rd.name : "unknown") + ". Invalid reward type configured.");
            }
        }

        //Add example rewards
        if (rewards.isEmpty() || rewards.size() < 1) {
            // 25 money
            Reward reward = new MoneyReward(new String[] {"*", "example"}, 0.5f, 25);
            rewards.put("example-1", reward);
            arv.getRewardCfg().setReward("example-1", new RewardData("example-1", "money", 1, reward));

            // 10,20,30,40 or 50 money
            reward = new MoneyReward(new String[] {"*", "example"}, 0.5f, Arrays.asList(10, 20, 30, 40, 50));
            rewards.put("example-2", reward);
            arv.getRewardCfg().setReward("example-2", new RewardData("example-2", "money", 3, reward));

            // 4<>12 emeralds.
            reward = new ItemReward(new String[] {"*", "example"}, 0.25f, 4, 12, new CWItem(Material.EMERALD));
            rewards.put("example-3", reward);
            RewardData data = new RewardData("example-3", "item", 2, reward);
            data.item = "emerald";
            arv.getRewardCfg().setReward("example-3", data);

            // Player skull
            reward = new CmdReward(new String[] {"*", "example"}, 0.2f, "give PLAYER head:3 1 player:PLAYER", "your head");
            rewards.put("example-4", reward);
            data = new RewardData("example-4", "cmd", 0, reward);
            data.cmd = "give PLAYER head:3 1 player:PLAYER";
            data.resultMsg = "your head";
            arv.getRewardCfg().setReward("example-4", data);

            // Haste effect
            Map<Integer, Double> valuesPerc = new HashMap<Integer, Double>();
            valuesPerc.put(200, 1.0);
            valuesPerc.put(600, 0.5);
            valuesPerc.put(1200, 0.1);
            reward = new EffectReward(new String[] {"*", "example"}, 0.2f, valuesPerc, false, PotionEffectType.FAST_DIGGING, 4);
            rewards.put("example-5", reward);
            data = new RewardData("example-5", "effect", 5, reward);
            data.amplifier = 4;
            data.effectType = "FAST_DIGGING";
            arv.getRewardCfg().setReward("example-5", data);

            arv.getRewardCfg().save();
        }
    }

    public String giveSpecificReward(String playerName, String name) {
        if (rewards.containsKey(name)) {
            return rewards.get(name).execute(playerName);
        }
        return "";
    }

    public String giveRandomReward(String playerName) {
        return giveRandomReward(playerName, "*");
    }

    public String giveRandomReward(String playerName, String category) {
        Map<String, Reward> categorizedRewards = getRewardsByCategory(category);

        double totalPerc = 0;
        for (Reward reward : categorizedRewards.values()) {
            totalPerc += reward.getPercentage();
        }

        double random = Math.random() * totalPerc;
        for (Reward reward : categorizedRewards.values()) {
            random -= reward.getPercentage();
            if (random <= 0.0d) {
                return reward.execute(playerName);
            }
        }
        return "";
    }

    public List<String> giveRewards(String playerName, String category, boolean forceReward) {
        List<String> rewardsGiven = new ArrayList<String>();
        Map<String, Reward> categorizedRewards = getRewardsByCategory(category);
        if (forceReward) {
            int count = 0;
            while (rewardsGiven.size() <= 0 && count <= 5) {
                for (Reward reward : categorizedRewards.values()) {
                    if (CWUtil.randomFloat() <= reward.getPercentage()) {
                        rewardsGiven.add(reward.execute(playerName));
                    }
                }
                count++;
            }
        } else {
            for (Reward reward : categorizedRewards.values()) {
                if (CWUtil.randomFloat() <= reward.getPercentage()) {
                    rewardsGiven.add(reward.execute(playerName));
                }
            }
        }
        return rewardsGiven;
    }


    public Map<String, Reward> getRewardsByCategory(String category) {
        Map<String, Reward> categorizedRewards = new HashMap<String, Reward>();
        for (String name : rewards.keySet()) {
            Reward r = rewards.get(name);
            if (r.inGroup(category)) {
                categorizedRewards.put(name, r);
            }
        }
        return categorizedRewards;
    }

    public Map<String, Reward> getRewards() {
        return rewards;
    }

}