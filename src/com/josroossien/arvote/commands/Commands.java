package com.josroossien.arvote.commands;

import com.clashwars.cwcore.utils.CWUtil;
import com.josroossien.arvote.ARVote;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Commands {

    private ARVote arv;

    public Commands(ARVote arv) {
        this.arv = arv;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //##########################################################################################################################
        //############################################### /reward {player} {reward} ################################################
        //##########################################################################################################################
        if (label.equalsIgnoreCase("reward")) {
            if (!sender.isOp() && !sender.hasPermission("cw.reward")) {
                sender.sendMessage(CWUtil.formatARMsg("&cInsufficient permissions."));
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(CWUtil.formatARMsg("&cInvalid command usage. &4/" + label + " {player} {reward}"));
                sender.sendMessage(CWUtil.formatARMsg("&cIf force is true it will make sure there is at least 1 reward."));
                return true;
            }

            OfflinePlayer oPlayer = arv.getServer().getOfflinePlayer(args[0]);
            if (oPlayer == null || !oPlayer.hasPlayedBefore()) {
                sender.sendMessage(CWUtil.formatARMsg("&cInvalid player."));
                return true;
            }

            String result = arv.getRM().giveSpecificReward(oPlayer.getName(), args[1]);
            if (result != "") {
                sender.sendMessage(CWUtil.formatARMsg("&6Given &a" + result + " &6as reward to &5" + oPlayer.getName() + "&6."));
                if (oPlayer.isOnline()) {
                    ((Player)oPlayer).sendMessage(CWUtil.formatARMsg("&6You have been rewarded &a" + result + "&6!"));
                }
            } else {
                sender.sendMessage(CWUtil.formatARMsg("&cNo reward found with this name."));
            }
            return true;
        }



        //##########################################################################################################################
        //############################################ /randomreward {player} {reward} #############################################
        //##########################################################################################################################
        if (label.equalsIgnoreCase("randomreward")) {
            if (!sender.isOp() && !sender.hasPermission("cw.reward")) {
                sender.sendMessage(CWUtil.formatARMsg("&cInsufficient permissions."));
                return true;
            }

            if (args.length < 1) {
                sender.sendMessage(CWUtil.formatARMsg("&cInvalid command usage. &4/" + label + " {player} [group=*]"));
                return true;
            }

            OfflinePlayer oPlayer = arv.getServer().getOfflinePlayer(args[0]);
            if (oPlayer == null || !oPlayer.hasPlayedBefore()) {
                sender.sendMessage(CWUtil.formatARMsg("&cInvalid player."));
                return true;
            }

            if (args.length > 1 && arv.getRM().getRewardsByCategory(args[1]).size() <= 0) {
                sender.sendMessage(CWUtil.formatARMsg("&cNo group found with this name."));
                return true;
            }

            String result = arv.getRM().giveRandomReward(oPlayer.getName(), args.length > 1 ? args[1] : "*");
            if (result != "") {
                sender.sendMessage(CWUtil.formatARMsg("&6Given &a" + result + " &6as reward to &5" + oPlayer.getName() + "&6."));
                if (oPlayer.isOnline()) {
                    ((Player)oPlayer).sendMessage(CWUtil.formatARMsg("&6You have been rewarded &a" + result + "&6!"));
                }
            } else {
                sender.sendMessage(CWUtil.formatARMsg("&cNo reward given."));
            }
            return true;
        }



        //##########################################################################################################################
        //########################################### /rewards {player} [group] [force] ############################################
        //##########################################################################################################################
        if (label.equalsIgnoreCase("rewards")) {
            if (!sender.isOp() && !sender.hasPermission("cw.reward")) {
                sender.sendMessage(CWUtil.formatARMsg("&cInsufficient permissions."));
                return true;
            }

            if (args.length < 1) {
                sender.sendMessage(CWUtil.formatARMsg("&cInvalid command usage. &4/" + label + " {player} [group=*] [force=false]"));
                return true;
            }

            OfflinePlayer oPlayer = arv.getServer().getOfflinePlayer(args[0]);
            if (oPlayer == null || !oPlayer.hasPlayedBefore()) {
                sender.sendMessage(CWUtil.formatARMsg("&cInvalid player."));
                return true;
            }

            if (args.length > 1 && arv.getRM().getRewardsByCategory(args[1]).size() <= 0) {
                sender.sendMessage(CWUtil.formatARMsg("&cNo group found with this name."));
                return true;
            }

            boolean force = false;
            if (args.length > 2 && (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("yes") || args[2].equalsIgnoreCase("force"))) {
                force = true;
            }

            List<String> result = arv.getRM().giveRewards(oPlayer.getName(), args.length > 1 ? args[1] : "*", force);
            if (result.size() > 0) {
                sender.sendMessage(CWUtil.formatARMsg("&6Given the following rewards to &5" + oPlayer.getName() + "&8: &a" + CWUtil.implode(result, "&8, &a")));
                if (oPlayer.isOnline()) {
                    ((Player)oPlayer).sendMessage(CWUtil.formatARMsg("&6You received the following rewards&8: &a" + CWUtil.implode(result, "&8, &a")));
                }
            } else {
                sender.sendMessage(CWUtil.formatARMsg("&cNo rewards given."));
                if (oPlayer.isOnline()) {
                    if (!force) {
                        ((Player)oPlayer).sendMessage(CWUtil.formatARMsg("&cYou didn't get any rewards. :("));
                    }
                }
            }
            return true;
        }

        if (label.equalsIgnoreCase("arvote")) {
            sender.sendMessage(CWUtil.integrateColor("&8===== &4&lArchaicRealms vote/reward plugin &8====="));
            sender.sendMessage(CWUtil.integrateColor("&6Author&8: &5worstboy32(jos)"));
            sender.sendMessage(CWUtil.integrateColor("&7Plugin to give random rewards for voting!"));
            sender.sendMessage(CWUtil.integrateColor("&7Use &9/vote &7for a list of vote sites!"));
            sender.sendMessage(CWUtil.integrateColor("&6More info&8: &9http://archaicrealms.com/Vote"));
            return true;
        }

        if (label.equalsIgnoreCase("vote")) {
            sender.sendMessage(CWUtil.integrateColor("&8======== &4&lVoting Sites &8========"));
            sender.sendMessage(CWUtil.integrateColor("&7&oYou can vote every day on the sites below."));
            sender.sendMessage(CWUtil.integrateColor("&7&oYou will get a random reward for every site you vote on."));
            sender.sendMessage(CWUtil.integrateColor("&6&lVoting page&8&l:&9&l http://archaicrealms.com/Vote"));
            sender.sendMessage(CWUtil.integrateColor("&8★ &6Site 1 (PMC)&8:&3 http://goo.gl/zT8H90"));
            sender.sendMessage(CWUtil.integrateColor("&8★ &6Site 2 (MS)&8:&3 http://goo.gl/lU8JO2"));
            sender.sendMessage(CWUtil.integrateColor("&8★ &6Site 3 (MC-MP)&8:&3 http://goo.gl/FrHcfd"));
            sender.sendMessage(CWUtil.integrateColor("&7&oplease also &4&ofavorite❤ &7&oand give a &b&odiamond✦ &7&oon pmc. &e:)"));
            return true;
        }

        return false;
    }
}
