package es.victor.mcplugins.HouseControl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.security.spec.ECField;

public class HouseRouter implements CommandExecutor {
    Messages cliMsg = new Messages(true);
    Messages playerMsg;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = (Player) sender;
            // Is a player
            playerMsg = new Messages(player);
            HouseControl house = new HouseControl(player);

            // No args
            if (args.length == 0) {
                // Check if the player has a house
                boolean isPlayerInDb;
                try {
                    isPlayerInDb = house.isPlayerInDb();
                } catch (HouseControlException e) {
                    cliMsg.serverError(e.getMessage());
                    playerMsg.serverError("");
                    return false;
                }
                if (isPlayerInDb) {
                    // The player is new to the plugin
                    playerMsg.houseInfo();
                } else {
                    // Open HouseControl menu
                    playerMsg.regiserInfo();
                }

            }

            if (args.length == 1) {
                if (args[0].equals("register")) {
                    // Check that name exists
                    String name;
                    try {
                        name = args[1];
                    } catch (Exception e) {
                        playerMsg.inputError();
                        return false;
                    }
                    try {
                        house.newPlayer(name);
                    } catch (HouseControlException e) {
                        cliMsg.serverError(e.getMessage());
                        playerMsg.serverError("");
                        return false;
                    }
                    playerMsg.sendMessage("You now must select a new name for your home");
                }
            }
        } catch (Exception e) {
            // Is from cli
        }

        return true;
    }
}
