package rml.model;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/16.
 */
public class CargoContainer {
    private List<Cargo> cargos;

    private int totalMoney;

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
}
