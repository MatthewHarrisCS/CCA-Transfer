package cca.transfer;

import java.time.LocalDate;

public class Resident {

    private int refNo, unitType, sex1, sex2, decline, fso, contract;
    private String last, first, unitNo;
    private LocalDate birthDate1, birthDate2, entryDate1, entryDate2,
        deathDate1, deathDate2, termDate1,  termDate2,
        transfer1to2, transfer1to3, transfer1to4, transfer1to5, 
        transfer2to2, transfer2to3, transfer2to4, transfer2to5;
    private double entryFee1, entryFee2, nonrefFee1, nonrefFee2, 
        refundFee1, refundFee2, comFee1, comFee2, 
        nonrefBal, refBal, comBal;

    public Resident(int refNo, String last, String first, String unitNo, int unitType, int sex1, int sex2,
                    LocalDate birthDate1, LocalDate birthDate2, LocalDate entryDate1, LocalDate entryDate2,
                    double entryFee1, double entryFee2, LocalDate deathDate1, LocalDate deathDate2,
                    LocalDate termDate1, LocalDate termDate2, double nonrefFee1, double nonrefFee2,
                    double refundFee1, double refundFee2, double comFee1, double comFee2, int decline, int fso, int contract, 
                    LocalDate transfer1to2, LocalDate transfer1to3, LocalDate transfer1to4, LocalDate transfer1to5, 
                    LocalDate transfer2to2, LocalDate transfer2to3, LocalDate transfer2to4, LocalDate transfer2to5,
                    double nonrefBal, double refBal, double comBal) {
        this.refNo        = refNo;
        this.last         = last;
        this.first        = first;
        this.unitNo       = unitNo;
        this.unitType     = unitType;
        this.sex1         = sex1;
        this.sex2         = sex2;
        this.birthDate1   = birthDate1;
        this.birthDate2   = birthDate2;
        this.entryDate1   = entryDate1;
        this.entryDate2   = entryDate2;
        this.entryFee1    = entryFee1;
        this.entryFee2    = entryFee2;
        this.deathDate1   = deathDate1;
        this.deathDate2   = deathDate2;
        this.termDate1    = termDate1;
        this.termDate2    = termDate2;
        this.nonrefFee1   = nonrefFee1;
        this.nonrefFee2   = nonrefFee2;
        this.refundFee1   = refundFee1;
        this.refundFee2   = refundFee2;
        this.comFee1      = comFee1;
        this.comFee2      = comFee2;
        this.decline      = decline;
        this.fso          = fso;
        this.contract     = contract;
        this.transfer1to2 = transfer1to2;
        this.transfer1to3 = transfer1to3;
        this.transfer1to4 = transfer1to4;
        this.transfer1to5 = transfer1to5;
        this.transfer2to2 = transfer2to2;
        this.transfer2to3 = transfer2to3;
        this.transfer2to4 = transfer2to4;
        this.transfer2to5 = transfer2to5;
        this.nonrefBal = nonrefBal;
        this.refBal = refBal;
        this.comBal = comBal;
    }

    public Resident() {
        this.refNo        = -1;
        this.last         = "n/a";
        this.first        = "n/a";        
        this.unitNo       = "n/a";
        this.unitType     = -1;
        this.sex1         = -1;
        this.sex2         = -1;
        this.birthDate1   = null;
        this.birthDate2   = null;
        this.entryDate1   = null;
        this.entryDate2   = null;
        this.entryFee1    = -1.0;
        this.entryFee2    = -1.0;
        this.deathDate1   = null;
        this.deathDate2   = null;
        this.termDate1    = null;
        this.termDate2    = null;
        this.nonrefFee1   = -1.0;
        this.nonrefFee2   = -1.0;
        this.refundFee1   = -1.0;
        this.refundFee2   = -1.0;
        this.comFee1      = -1.0;
        this.comFee2      = -1.0;
        this.decline      = -1;
        this.fso          = -1;
        this.contract     = -1;
        this.transfer1to2 = null;
        this.transfer1to3 = null;
        this.transfer1to4 = null;
        this.transfer1to5 = null;
        this.transfer2to2 = null;
        this.transfer2to3 = null;
        this.transfer2to4 = null;
        this.transfer2to5 = null;
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

    public String getUnitNo() {
        return unitNo;
    }

    public int getUnitType() {
        return unitType;
    }

    public int getSex1() {
        return sex1;
    }

    public int getSex2() {
        return sex2;
    }

    public LocalDate getBirthDate1() {
        return birthDate1;
    }
    
    public LocalDate getBirthDate2() {
        return birthDate2;
    }
    
    public LocalDate getEntryDate1() {
        return entryDate1;
    }
    
    public LocalDate getEntryDate2() {
        return entryDate2;
    }

    public double getEntryFee1() {
        return entryFee1;
    }
    
    public double getEntryFee2() {
        return entryFee2;
    }

    public LocalDate getDeathDate1() {
        return deathDate1;
    }
    
    public LocalDate getDeathDate2() {
        return deathDate2;
    }
    
    public LocalDate getTermDate1() {
        return termDate1;
    }
    
    public LocalDate getTermDate2() {
        return termDate2;
    }
    
    public double getNonrefFee1() {
        return nonrefFee1;
    }
    
    public double getNonrefFee2() {
        return nonrefFee2;
    }

    public double getRefundFee1() {
        return refundFee1;
    }
    
    public double getRefundFee2() {
        return refundFee2;
    }

    public double getComFee1() {
        return comFee1;
    }
    
    public double getComFee2() {
        return comFee2;
    }

    public int getDecline() {
        return decline;
    }

    public int getFso() {
        return fso;
    }

    public int getContract() {
        return contract;
    }

    public LocalDate getTransfer1to2() {
        return transfer1to2; 
    }

    public LocalDate getTransfer1to3() {
        return transfer1to3; 
    }

    public LocalDate getTransfer1to4() {
        return transfer1to4; 
    }

    public LocalDate getTransfer1to5() {
        return transfer1to5; 
    }

    public LocalDate getTransfer2to2() {
        return transfer2to2; 
    }
    
    public LocalDate getTransfer2to3() {
        return transfer2to3; 
    }
    
    public LocalDate getTransfer2to4() {
        return transfer2to4; 
    }
    
    public LocalDate getTransfer2to5() {
        return transfer2to5; 
    }

    public double getNonrefBal() {
        return nonrefBal;
    }

    public double getRefBal() {
        return refBal;
    }

    public double getComBal() {
        return comBal;
    }
}
