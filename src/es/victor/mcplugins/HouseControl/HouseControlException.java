package es.victor.mcplugins.HouseControl;

public class HouseControlException extends Throwable {
    public HouseControlException(String msg) {
        super(msg);
        System.out.println(msg);
    }
}
