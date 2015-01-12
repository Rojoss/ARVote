package com.josroossien.arvote.reward;

import com.clashwars.cwcore.CWCore;
import com.josroossien.arvote.reward.internal.Reward;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class EffectReward extends Reward {

    private PotionEffectType type;
    private int amplifier;

    public EffectReward(String[] categories, float percentage, int value, PotionEffectType type, int amplifier) {
        super(categories, percentage, value);
        this.type = type;
        this.amplifier = amplifier;
    }

    public EffectReward(String[] categories, float percentage, int minVal, int maxVal, PotionEffectType type, int amplifier) {
        super(categories, percentage, minVal, maxVal);
        this.type = type;
        this.amplifier = amplifier;
    }

    public EffectReward(String[] categories, float percentage, List<Integer> values, PotionEffectType type, int amplifier) {
        super(categories, percentage, values);
        this.type = type;
        this.amplifier = amplifier;
    }

    public EffectReward(String[] categories, float percentage, Map<Integer, Double> valuesPerc, boolean addTogether, PotionEffectType type, int amplifier) {
        super(categories, percentage, valuesPerc, addTogether);
        this.type = type;
        this.amplifier = amplifier;
    }

    @Override
    public String execute(String player) {
        int duration = getValue();

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        if (offlinePlayer != null && offlinePlayer.isOnline()) {
            Player p = offlinePlayer.getPlayer();
            if (p.hasPotionEffect(type)) {
                for (PotionEffect effect : p.getActivePotionEffects()) {
                    if (effect.getType() == type) {
                        p.addPotionEffect(new PotionEffect(type, duration + effect.getDuration(), amplifier), true);
                    }
                }
            } else {
                p.addPotionEffect(new PotionEffect(type, duration, amplifier), true);
            }
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "queue " + player + " effect " + type.getName() + " " + duration + " " + amplifier);
        }
        return (CWCore.inst().getPotionEffects().getDisplayName(type)) + "" + amplifier + " for " + duration/20 + "s";
    }

}