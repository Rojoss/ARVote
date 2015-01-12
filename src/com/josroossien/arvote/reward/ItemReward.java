package com.josroossien.arvote.reward;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.helpers.CWItem;
import com.josroossien.arvote.reward.internal.Reward;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ItemReward extends Reward {

    CWItem item;

    //Item rewards work for offline players with the queue system but it wont keep meta data.
    //So it's best to only give item rewards to online players.

    public ItemReward(String[] categories, float percentage, CWItem item) {
        super(categories, percentage, item.getAmount());
    }

    public ItemReward(String[] categories, float percentage, int minAmt, int maxAmt, CWItem item) {
        super(categories, percentage, minAmt, maxAmt);
        this.item = item;
    }

    public ItemReward(String[] categories, float percentage, List<Integer> amounts, CWItem item) {
        super(categories, percentage, amounts);
        this.item = item;
    }

    public ItemReward(String[] categories, float percentage, Map<Integer, Double> amounts, boolean addTogether, CWItem item) {
        super(categories, percentage, amounts, addTogether);
        this.item = item;
    }

    @Override
    public String execute(String player) {
        int amount = getValue();
        item.setAmount(Math.max(amount, 1));

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        if (offlinePlayer != null && offlinePlayer.isOnline()) {
            item.giveToPlayer((Player)offlinePlayer);
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "queue " + player + " cmd give {PLAYER} " + item.getType().name() + ":" + item.getDurability() + " " + item.getAmount());
        }
        return "" + item.getAmount() + " " + CWCore.inst().getMaterials().getDisplayName(item.getType(), item.getDurability());
    }
}