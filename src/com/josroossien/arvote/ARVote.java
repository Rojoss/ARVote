package com.josroossien.arvote;

import com.clashwars.cwcore.CWCore;
import com.josroossien.arvote.commands.Commands;
import com.josroossien.arvote.config.RewardCfg;
import com.josroossien.arvote.reward.internal.RewardManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ARVote extends JavaPlugin {

    private static ARVote instance;
    private Gson gson = new Gson();
    private Economy econ;

    private Commands cmds;
    private RewardCfg rewardCfg;

    private RewardManager rm;

    private final Logger log = Logger.getLogger("Minecraft");


    @Override
    public void onDisable() {
        log("disabled");
    }

    @Override
    public void onEnable() {
        instance = this;

        econ = CWCore.inst().GetDM().getEconomy();
        if (econ == null) {
            log("Vault couldn't be loaded! Economy rewards won't work.");
        }

        rewardCfg = new RewardCfg("plugins/ARVote/Rewards.yml");
        rewardCfg.load();

        rm = new RewardManager(this);

        cmds = new Commands(this);

        log("loaded successfully");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return cmds.onCommand(sender, cmd, label, args);
    }

    public static ARVote inst() {
        return instance;
    }

    public Economy getEconomy() {
        return econ;
    }

    public void log(Object msg) {
        log.info("[ARVote " + getDescription().getVersion() + "] " + msg.toString());
    }


    public Gson getGson() {
        return gson;
    }

    public RewardCfg getRewardCfg() {
        return rewardCfg;
    }

    public RewardManager getRM() {
        return rm;
    }

}
