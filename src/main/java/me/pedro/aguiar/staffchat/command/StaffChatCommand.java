package me.pedro.aguiar.staffchat.command;

import me.pedro.aguiar.staffchat.main.GlobalStaffChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    private final String format;

    public StaffChatCommand(String name, GlobalStaffChat instance, String format) {
        super(name, "staffchat.admin", "sc");

        this.format = format;

        ProxyServer.getInstance().getPluginManager().registerCommand(instance, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;

        final ProxiedPlayer player = (ProxiedPlayer) sender;

        if(args.length == 0) {
            player.sendMessage("§c/sc <message>");
            return;
        }

        final StringBuilder message = new StringBuilder();

        for(String arg : args) message.append(arg);

        ProxyServer.getInstance().getPlayers()
                .stream()
                .filter(staff -> staff.hasPermission("staffchat.admin"))
                .forEach(staff -> sendStaffChatMessage(staff, message.toString()));
    }

    private void sendStaffChatMessage(ProxiedPlayer player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', format)
                .replace("%message%", message)
                .replace("%player%", player.getName()));
    }
}
