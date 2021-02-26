package es.victor.mcplugins.HouseControl;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.sql.SQLIntegrityConstraintViolationException;

public class HouseRouter implements CommandExecutor {
    Messages cliMsg = new Messages();
    Messages playerMsg;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        try {
            player = (Player) sender;
            player.getDisplayName();
        } catch (Exception e) {
            cliMsg.serverError("You cannot create houses from the server console at this time");
        }
        player = (Player) sender;
        // Is a player
        playerMsg = new Messages(player);
        HouseControl house = new HouseControl(player);

        // No args
        if (args.length == 0) {
            // Make a book
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
            BookMeta bm = (BookMeta)book.getItemMeta();
            if (bm == null) {
                cliMsg.serverError("Book Meta Null pointer exception");
                playerMsg.serverError("");
            }
            assert bm != null;
            bm.setAuthor("House Control");

            bm.setTitle("Help");
            String[] pages = new String[2];
            pages[0] = "Welcome to the house plugin";
            pages[1] = "Register in the system";
            bm.setPages(pages);
            book.setItemMeta(bm);
            player.getInventory().addItem(book);
            //Help
            playerMsg.help();
        }
        if (args.length > 0) {
            if (args[0].equals("register")) {
                // Check that name exists
                StringBuilder nameBuffer = new StringBuilder();
                String name;
                for (int i = 1; i < args.length; i++) {
                    if (i > 1) {
                        nameBuffer.append(" ").append(args[i]);
                    } else {
                        nameBuffer.append(args[i]);
                    }
                }
                if (nameBuffer.length() < 1) {
                    playerMsg.inputError();
                    return false;
                }
                name = nameBuffer.toString();
                try {
                    if (house.isPlayerInDb()) {
                        playerMsg.sendMessage("You already have registered in the house system");
                        return true;
                    } else {
                        house.newPlayer(name);
                    }
                } catch (IndexOutOfBoundsException e) {
                    playerMsg.inputError();
                    return false;
                } catch (SQLIntegrityConstraintViolationException e) {
                    cliMsg.serverError(e.getMessage());
                    playerMsg.serverErrorForPlayer("There was an problem adding you to the server");
                    return false;
                } catch (Exception | HouseControlException e) {
                    cliMsg.serverError(e.getMessage());
                    playerMsg.serverError("There was an problem adding you to the server");
                }
                playerMsg.sendMessage("¡Congratulations! You are now registered in the house control system");
                return true;
            }
            if (args[0].equals("new")) {
                String name = "";
                try {
                    name = house.newHouse();
                } catch (HouseControlException e) {
                    cliMsg.serverError(e.getMessage());
                    playerMsg.serverErrorForPlayer(e.getMessage());
                } catch (Exception e){
                    cliMsg.serverError(e.getMessage());
                    playerMsg.serverError("");
                }

                playerMsg.sendMessage("The house " + name + " has been created succesfully", "success");
                playerMsg.sendMessage(name + " has been set as your default home");
                return true;
            }
        }

        return true;
    }
}
