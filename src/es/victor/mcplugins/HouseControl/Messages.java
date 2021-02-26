package es.victor.mcplugins.HouseControl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {
    Player player;
    boolean cli = false;

    public Messages() {
        this.cli = true;
    }

    public Messages(Player player) {
        this.player = player;
    }

    public void sendMessage(String msg) {
        if (this.cli) {
            System.out.println(msg);
        } else {
            this.player.sendMessage(msg);
        }
    }

    public void sendMessage(String msg, String type) {
        switch (type) {
            case "warn":
                this.player.sendMessage(ChatColor.RED + msg);
                break;
            case "warn_friendly":
                this.player.sendMessage(ChatColor.YELLOW + msg);
                break;
            case "success":
                this.player.sendMessage(ChatColor.GREEN + msg);
            case "info":
                this.player.sendMessage(ChatColor.DARK_BLUE + msg);
                break;
            case "data":
                this.player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + msg);
                break;
            case "data_example":
                this.player.sendMessage(ChatColor.DARK_AQUA + msg);
                break;
            default:
                this.player.sendMessage(msg);
        }
    }

    public void greetings() {
        if (!this.cli) {
            this.player.sendMessage("Welcome: " + this.player.getDisplayName());
        }
    }

    public void introduce() {
        if (!this.cli) {
            this.sendMessage("Hello, let me introduce ourselves, " + this.player.getDisplayName());
            this.sendMessage("To get started in the house control plugin, type /house help");
        } else {
            System.out.println("New user: " + player.getDisplayName());
        }
    }

    public void help() {
        this.sendMessage("Welcome to the house plugin");
        this.sendMessage("\n\n");
        this.sendMessage("/house register: Register in the system");
        this.sendMessage("Example: /house register username");
        this.sendMessage("Replace username with wathever you want!");
        this.sendMessage("\n");
        this.sendMessage("/house new: Create a new house");
        this.sendMessage("Example: /house new HouseName 5");
        this.sendMessage("YOU MUST BE IN THE CENTER OF YOUR HOUSE");
        this.sendMessage("Replace HouseName with wathever you want!");
        this.sendMessage("Replace 5 with the number of blocks around you that is the house boundaries");
        this.sendMessage("\n");
        this.sendMessage("/house status: Register in the system");
        this.sendMessage("Example: /house status");
        this.sendMessage("This will show the status of your default house");
        this.sendMessage("Example: /house status HouseName");
        this.sendMessage("This will show the status of the house specified");
    }

    public void serverError(String serverError) {
        if (!this.cli) {
            this.sendMessage("There was an internal error, contact with the Administrator of the server","warn");
        } else {
            System.out.println(serverError);
        }
    }

    public void serverErrorForPlayer(String serverError) {
        if (!this.cli) {
            this.sendMessage(serverError);
        } else {
            System.out.println(serverError);
        }
    }

    public void inputError() {
        this.sendMessage("Check your spelling, that command is not good", "warn_friendly");
    }


}
