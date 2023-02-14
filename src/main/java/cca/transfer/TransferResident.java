package cca.transfer;

import java.time.LocalDate;

public class TransferResident {

    int refNo, res, into;
    String last, first;
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
}
