package es.victor.mcplugins.HouseControl;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;

public class HouseControl {
    Db db;
    Messages msg;
    Messages climsg;
    Player player;

    public HouseControl(Player player) {
        this.player = player;
        this.msg = new Messages(this.player);
        this.climsg = new Messages(true);

        try {
            this.db = new Db();
        } catch (DbException dbe) {
            this.msg.serverError("");
            this.climsg.serverError(dbe.getMessage());
        }
    }

    public boolean isPlayerInDb() throws HouseControlException {
        boolean isPlayer = false;

        String sql = "SELECT ID FROM Players";
        ResultSet rs;
        ArrayList<String> id_col = new ArrayList<String>();

        try {
            Statement stmt = this.db.getConnection().createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                id_col.add(rs.getString("ID"));
            }
        } catch (Exception e) {
            throw new HouseControlException(e.getMessage());
        }

        String[] id_col_arr = new String[id_col.size()];
        id_col_arr = id_col.toArray(id_col_arr);

        if (id_col_arr.length > 1) {
            isPlayer = true;
        }
        return isPlayer;
    }

    public void newPlayer(String name) throws SQLException {
        String username = player.getDisplayName();

        String query = "INSERT INTO Players (ID, USERNAME, NAME)" + " VALUES (?, ?, ?)";
        PreparedStatement preparedStmt = this.db.getConnection().prepareStatement(query);
        preparedStmt.setString (1, null);
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, name);
        preparedStmt.execute();
    }

    public void newHouse(String name) throws SQLException {
        String username = player.getDisplayName();

        // Get last ID of house
        Statement statementIdHouse = this.db.getConnection().createStatement();
        ResultSet resultSetIdHouse = statementIdHouse.executeQuery("SELECT ID FROM House ORDER BY ID DESC LIMIT 1");
        resultSetIdHouse.next();
        int count = resultSetIdHouse.getInt("ID") + 1;
        resultSetIdHouse.close();

        // Get last ID of the player
        Statement statementIdPlayer = this.db.getConnection().createStatement();
        ResultSet resultSetIdPlayer = statementIdPlayer.executeQuery("SELECT ID FROM Players WHERE USERNAME = '" + username + "'");
        resultSetIdPlayer.next();
        int id_player = resultSetIdPlayer.getInt("ID") + 1;
        resultSetIdPlayer.close();


        String houseName = username + "_House_" + count;
        String location = player.getLocation().getX() + "," + player.getLocation().getY() + "," + player.getLocation().getZ();
        String owners = "" + count;

        String query = "INSERT INTO House (ID, HOUSENAME, NAME, LOCATION, OWNERS)" + " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = this.db.getConnection().prepareStatement(query);
        preparedStmt.setString (1, null);
        preparedStmt.setString (2, houseName);
        preparedStmt.setString (3, name);
        preparedStmt.setString (4, location);
        preparedStmt.setString (5, owners);
        preparedStmt.execute();


        String queryDefault = "UPDATE Players SET DEFAULT = ? WHERE USERNAME = ?)";
        PreparedStatement preparedStmtDefault = this.db.getConnection().prepareStatement(queryDefault);
        preparedStmt.setInt (1, id_player);
        preparedStmt.setString (2, "'" + username + "'");
        preparedStmt.execute();
    }
}
