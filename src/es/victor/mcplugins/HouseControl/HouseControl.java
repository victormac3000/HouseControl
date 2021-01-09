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

    public void newPlayer(String name) throws HouseControlException,SQLException {
        String username = player.getDisplayName();

        try {
            String query = "INSERT INTO Players (ID, USERNAME, NAME)" + " VALUES (?, ?, ?)";
            PreparedStatement preparedStmt = this.db.getConnection().prepareStatement(query);
            preparedStmt.setString (1, null);
            preparedStmt.setString (2, username);
            preparedStmt.setString   (3, name);
            preparedStmt.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new HouseControlException("You have already registered in the house system! :)");
        }
    }
}
