package instruments.commands;

import instruments.Instrument;
import instruments.InstrumentType;
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
            if(args.length != 1) {
                sendHelpMessage(p);
                return true;
            }

            if (args[0].equalsIgnoreCase("list")) {
                String instrumentString = "";
                for (InstrumentType instrumentType : InstrumentType.values()) {
                    instrumentString += instrumentType.toString() + ",";
                }

                p.sendMessage("Supported instruments:");
                p.sendMessage(instrumentString);
                return true;
            }

            InstrumentType foundType = null;
            for (InstrumentType instrumentType : InstrumentType.values()) {
                if (instrumentType.toString().toLowerCase().equals(args[0].toLowerCase())) {
                    foundType = instrumentType;
                    break;
                }
            }

            if (foundType == null) {
                p.sendMessage("Could not find instrument " + args[0]);
                p.sendMessage("For a list of available instruments type /instruments list");
                return true;
            }

            new Instrument(foundType, p).play();
            return true;

        }

        return false;
    }

    private void sendHelpMessage(Player p) {
        p.sendMessage("Please provide an instrument. Ex: /instruments guitar");
        p.sendMessage("For a list of available instruments type /instruments list");
    }

}
