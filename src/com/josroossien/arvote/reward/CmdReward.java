package com.josroossien.arvote.reward;

import com.josroossien.arvote.reward.internal.Reward;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Map;

public class CmdReward extends Reward {

    private String cmd;
    private String resultMsg;

    //{PLAYER} will be replaced with the player that gets the reward.
    //{VAL} will be replaced with a value if specified in the constructor.

    public CmdReward(String[] categories, float percentage, String cmd, String resultMsg) {
        super(categories, percentage);
        this.cmd = cmd;
        this.resultMsg = resultMsg;
    }

    public CmdReward(String[] categories, float percentage, int val, String cmd, String resultMsg) {
        super(categories, percentage, val);
        this.cmd = cmd;
        this.resultMsg = resultMsg;
    }

    public CmdReward(String[] categories, float percentage, int minVal, int maxVal, String cmd, String resultMsg) {
        super(categories, percentage, minVal, maxVal);
        this.cmd = cmd;
        this.resultMsg = resultMsg;
    }

    public CmdReward(String[] categories, float percentage, List<Integer> values, String cmd, String resultMsg) {
        super(categories, percentage, values);
        this.cmd = cmd;
        this.resultMsg = resultMsg;
    }

    public CmdReward(String[] categories, float percentage, Map<Integer, Double> valuesPerc, boolean addTogether, String cmd, String resultMsg) {
        super(categories, percentage, valuesPerc, addTogether);
        this.cmd = cmd;
        this.resultMsg = resultMsg;
    }




    @Override
    public String execute(String player) {
        int value = getValue();
        cmd = cmd.replace("VAL", "" + value);
        resultMsg = resultMsg.replace("VAL", "" + value);

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        if (offlinePlayer != null && offlinePlayer.isOnline()) {
            cmd = cmd.replace("PLAYER", player);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "queue " + player + " cmd " + cmd);
        }

        return resultMsg;
    }

}