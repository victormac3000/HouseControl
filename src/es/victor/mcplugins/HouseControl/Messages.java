package es.victor.mcplugins.HouseControl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {
    Player player;
    boolean cli = false;

    public Messages(Boolean noPlayer) {
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
            this.player.sendMessage("Hello, let me introduce ourselves, " + this.player.getDisplayName());
        } else {
            System.out.println("A brand new player has connected to the server");
        }
    }

    public void houseInfo() {
        this.sendMessage("To create a house write the command below", "info");
        this.sendMessage("/house houseUsername houseName Xcoordinate Ycoordinate Zcoordinate", "data");
        this.sendMessage("Example: /house victor356 Victor's House -85 58 157", "data_example");
    }

    public void regiserInfo() {
        this.sendMessage("Welcome to the house plugin!", "info");
        this.sendMessage("To get started type the following command:", "info");
        this.sendMessage("/house register yourName", "data");
        this.sendMessage("Example: /house register Víctor", "data_example");
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
        this.sendMessage("Check your spelling, that command is not good","warn_friendly");
    }


}
