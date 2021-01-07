package es.victor.mcplugins.HouseControl;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.Statement;
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

    public void newPlayer(String name) throws HouseControlException {
        String username = player.getDisplayName();

        String sql = "INSERT (ID,USERNAME,NAME) INTO Players VALUES (null, " + username + "," + name + ")";
        ResultSet rs;
        try {
            Statement stmt = this.db.getConnection().createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println(sql);
        } catch (Exception e) {
            throw new HouseControlException(e.getMessage());
        }
    }
}
