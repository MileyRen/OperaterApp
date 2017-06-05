package com.operaterapp.domain;
/**
 * Created by Miley_Ren on 2017/5/21.
 */

public class MachineAdapter {
    public Integer mOperaterId;
    public Integer machineId;
    public String address;
    public String machineName;
    public String machinePannel;
    public MachineAdapter(){
        super();
    }

    public Integer getmOperaterId() {
        return mOperaterId;
    }

    public void setmOperaterId(Integer mOperaterId) {
        this.mOperaterId = mOperaterId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachinePannel() {
        return machinePannel;
    }

    public void setMachinePannel(String machinePannel) {
        this.machinePannel = machinePannel;
    }
}
