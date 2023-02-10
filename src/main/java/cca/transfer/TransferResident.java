package cca.transfer;

import java.time.LocalDate;

public class TransferResident {

    int refNo;
    String last;
    String first;
    int res;
    int into;
    LocalDate date;

    public TransferResident(int refNo, String last, String first, int res, int into, LocalDate date) {
        this.refNo = refNo;
        this.last = last;
        this.first = first;
        this.res = res;
        this.into = into;
        this.date = date;
    }

    public TransferResident() {
        this.refNo = -1;
        this.last = "n/a";
        this.first = "n/a";
        this.res = -1;
        this.into = -1;
        this.date = null;
    }

    public int getRefNo() {
        return refNo;
    }

    public String getLast() {
        return last;
    }

    public String getFirst() {
        return first;
    }

    public int getRes() {
        return res;
    }

    public int getInto() {
        return into;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setRefNo(int refNo) {
        this.refNo = refNo;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public void setInto(int into) {
        this.into = into;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
}
