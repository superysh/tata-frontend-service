package rml.model;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class UserTypeDesc {

    private String type_uuid;

    private int type;

    private String typeDesc;

    private float money;

    private int stauts;

    public String getType_uuid() {
        return type_uuid;
    }

    public void setType_uuid(String type_uuid) {
        this.type_uuid = type_uuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getStauts() {
        return stauts;
    }

    public void setStauts(int stauts) {
        this.stauts = stauts;
    }
}
