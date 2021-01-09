package es.victor.mcplugins.HouseControl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.security.spec.ECField;
import java.sql.SQLException;

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
            // System.out.println();
            if (args.length > 0) {
                if (args[0].equals("register")) {
                    // Check that name exists
                    StringBuffer nameBuffer = new StringBuffer();
                    String name;
                    try {
                        for (int i=1; i < args.length; i++) {
                            if (i>1) {
                                nameBuffer.append(" " + args[i]);
                            } else {
                                nameBuffer.append(args[i]);
                            }
                        }
                        name = nameBuffer.toString();
                        house.newPlayer(name);
                    } catch (IndexOutOfBoundsException e) {
                        playerMsg.inputError();
                        return false;
                    } catch (HouseControlException e) {
                        cliMsg.serverError(e.getMessage());
                        playerMsg.serverErrorForPlayer(e.getMessage());
                        return false;
                    } catch (Exception e) {
                        cliMsg.serverError(e.getMessage());
                        playerMsg.serverError("");
                    }
                    playerMsg.sendMessage("You now must select a new name for your home");
                    return true;
                }
            }
        } catch (Exception e) {
            // Is from cli
        }

        return true;
    }
}
