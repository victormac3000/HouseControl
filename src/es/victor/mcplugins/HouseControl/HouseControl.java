package es.victor.mcplugins.HouseControl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.sql.*;
import java.util.Objects;

public class HouseControl {
    Db db;
    Messages msg;
    Messages climsg;
    Player player;

    public HouseControl(Player player) {
        this.player = player;
        this.msg = new Messages(this.player);
        this.climsg = new Messages();

        try {
            this.db = new Db();
        } catch (DbException dbe) {
            this.msg.serverError("");
            this.climsg.serverError(dbe.getMessage());
        }
    }

    public boolean isPlayerInDb() throws HouseControlException {
        boolean isPlayer = false;
        int count;

        try {
            Statement s = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet r = s.executeQuery("SELECT * FROM Players WHERE ID = '" + player.getDisplayName() +  "'");
            r.last();
            count = r.getRow();
            r.beforeFirst();
        } catch (SQLException e) {
            throw new HouseControlException(e.getMessage());
        }

        if (count > 0) {
            isPlayer = true;
        }
        return isPlayer;
    }

    public void newPlayer(String name) throws SQLException {
        String username = player.getDisplayName();

        String query = "INSERT INTO Players (ID, NAME)" + " VALUES (?, ?)";
        PreparedStatement preparedStmt = this.db.getConnection().prepareStatement(query);
        preparedStmt.setString (1, username);
        preparedStmt.setString (2, name);
        preparedStmt.execute();
    }


    public String newHouse() throws HouseControlException,SQLException {
        Inventory inventory = player.getInventory();
        ItemStack[] items =  inventory.getContents();
        ItemStack book;
        BookMeta bookMeta = null;
        String name,location,boundaries;
        int numberStacks = 0;

        for (ItemStack item : items) {
            if (item != null) {
                numberStacks++;
            }
        }
        if (numberStacks < 1) {
            throw new HouseControlException("Your inventory is empty!");
        }

        for (ItemStack item : items) {
            if (item != null) {
                if (item.getType().toString().equals("WRITTEN_BOOK")) {
                    book = item;
                    bookMeta = (BookMeta) book.getItemMeta();
                    break;
                } else {
                    throw new HouseControlException("You must have the register book with you");
                }
            }
        }

        // Check book name
        assert bookMeta != null;
        if (Objects.equals(bookMeta.getTitle(), "New house")) {
            // Check book pages
            if (bookMeta.getPageCount() == 4) {
                name = bookMeta.getPage(1);
                location = bookMeta.getPage(2);
                boundaries = bookMeta.getPage(3) + "/" + bookMeta.getPage(4);
                double randomNumber = Math.random();
                String id = name + "_" + randomNumber;

                String[] locationArray = location.split(",");
                if (locationArray.length != 3) {
                    throw new HouseControlException("The coordinates of the location field are not numbers");
                }
                for (String s : locationArray) {
                    try {
                        Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        throw new HouseControlException("The coordinates of the location field are not numbers");
                    }
                }

                String[] boundariesArray = boundaries.split("/");
                if (boundariesArray.length != 2) {
                    throw new HouseControlException("The coordinates of the boundaries field are not numbers");
                }
                String[] boundariesTopLeft = boundariesArray[0].split(",");
                String[] boundariesRightBottom = boundariesArray[1].split(",");
                if (boundariesTopLeft.length != 3 || boundariesRightBottom.length != 3) {
                    throw new HouseControlException("The coordinates of the boundaries field are not right");
                }

                for (String s : boundariesTopLeft) {
                    try {
                        Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        throw new HouseControlException("The coordinates of the location field are not numbers");
                    }
                }
                for (String s : boundariesRightBottom) {
                    try {
                        Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        throw new HouseControlException("The coordinates of the location field are not numbers");
                    }
                }
                String queryHouses = "INSERT INTO Houses (ID, NAME, LOCATION, BOUNDARIES)" + " VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStmtHouses = this.db.getConnection().prepareStatement(queryHouses);
                preparedStmtHouses.setString (1, id);
                preparedStmtHouses.setString (2, name);
                preparedStmtHouses.setString (3, location);
                preparedStmtHouses.setString (4, boundaries);
                preparedStmtHouses.execute();

                String queryHave = "INSERT INTO Have (PID, HID, PERMISSION)" + " VALUES (?, ?, ?)";
                PreparedStatement preparedStmtHave = this.db.getConnection().prepareStatement(queryHave);
                preparedStmtHave.setString (1, this.player.getDisplayName());
                preparedStmtHave.setString (2, id);
                preparedStmtHave.setInt (3, 9);
                preparedStmtHave.execute();

                String queryPlayerDefault = "UPDATE Players SET DEFAULT_HOUSE=? WHERE ID=?";
                PreparedStatement preparedStmtPlayerDefault = this.db.getConnection().prepareStatement(queryPlayerDefault);
                preparedStmtPlayerDefault.setString (1, id);
                preparedStmtPlayerDefault.setString (2, this.player.getDisplayName());
                preparedStmtPlayerDefault.execute();
            } else {
                throw new HouseControlException("The book has the wrong number of pages");
            }
        } else {
            throw new HouseControlException("Check the name of your book");
        }
        return name;
    }
}
