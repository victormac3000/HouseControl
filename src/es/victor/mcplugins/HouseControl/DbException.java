package es.victor.mcplugins.HouseControl;

public class DbException extends Throwable {
    public DbException(String msg) {
        super(msg);
        System.out.println(msg);
    }
}
