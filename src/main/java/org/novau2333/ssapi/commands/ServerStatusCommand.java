package org.novau2333.ssapi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.novau2333.ssapi.Server;

public class ServerStatusCommand extends Command {
    public ServerStatusCommand() {
        super("skstatus");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage(ChatColor.BLUE+Server.getStatus());
        return true;
    }
}
