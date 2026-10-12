package com.xantrix.webapp.dto;


import java.io.Serializable;
public class Dto implements Serializable {

    private long Count;
    private int FamAssortID;
    private String famAssortDesc;

    public Dto(long Count, int FamAssortID, String famAssortDesc){
        this.Count = Count;
        this.FamAssortID = FamAssortID;
        this.famAssortDesc = famAssortDesc;
    }

    public String getFamAssortDesc() {
        return famAssortDesc;
    }
    public void setFamAssortDesc(String famAssortDesc) {
        this.famAssortDesc = famAssortDesc;
    }
    public int getFamAssortID() {
        return FamAssortID;
    }
    public void setFamAssortID(int FamAssortID) {
        this.FamAssortID = FamAssortID;
    }
    public long getCount() {
        return Count;
    }
    public void setCount(long count) {
        Count = count;
    }

}
