package instruments.commands;

import instruments.Instrument;
import instruments.InstrumentType;
import instruments.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InstrumentsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("instruments")) {
            if(args.length == 0) {
                sendHelpMessage(p);
                return true;
            }

            if (args[0].equalsIgnoreCase("list")) {
                String instrumentString = "";
                for (InstrumentType instrumentType : InstrumentType.values()) {
                    instrumentString += instrumentType.toString().toLowerCase() + ", ";
                }

                p.sendMessage(ChatColor.RED + "Supported instruments:");
                p.sendMessage(ChatColor.RED + instrumentString);
                return true;
            }

            if(args.length == 2 && args[0].equalsIgnoreCase("give")) {
                InstrumentType foundType = null;
                for (InstrumentType instrumentType : InstrumentType.values()) {
                    if (instrumentType.toString().toLowerCase().equals(args[1].toLowerCase())) {
                        foundType = instrumentType;
                        break;
                    }
                }

                if (foundType == null) {
                    p.sendMessage(ChatColor.RED + "Could not find instrument " + args[0]);
                    p.sendMessage(ChatColor.RED + "For a list of available instruments type /instruments list");
                    return true;
                }

                p.getInventory().addItem(foundType.getItemStack());
                return true;
            }


            sendHelpMessage(p);
            return false;
        }

        return false;
    }

    private void sendHelpMessage(Player p) {
        p.sendMessage(ChatColor.RED + "Please provide an instrument. Ex: /instruments give guitar");
        p.sendMessage(ChatColor.RED + "For a list of available instruments type /instruments list");
    }

}
