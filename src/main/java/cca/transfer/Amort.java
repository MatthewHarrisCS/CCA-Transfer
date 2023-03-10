package cca.transfer;

public class Amort {
    
    // The values not filled in by the program (as of now)
    private double lifeEx1 = 0.0, lifeEx2 = 0.0, rNewFees = 0.0, rActRef = 0.0, 
    nrActRef = 0.0, cActRef = 0.0, nrNewSl = 0.0, nrAmorSl = 0.0, 
    nrTermSl = 0.0, cNewSl = 0.0, cAmorSl = 0.0, cTermSl = 0.0, 
    nrNewCC = 0.0, nrAmorCC = 0.0, nrTermCC = 0.0, nrUnamCC = 0.0, 
    cNewCC = 0.0, cAmorCC = 0.0, cTermCC = 0.0, cUnamCC = 0.0;
    // the values filled in by program
    private int ref;
    private double rFeeBal, nrUnamSl, cUnamSl; 

    public Amort(int ref, double rFeeBal, double nrUnamSl, double cUnamSl) {
        this.ref = ref;
        this.rFeeBal = rFeeBal;
        this.nrUnamSl = nrUnamSl;
        this.cUnamSl = cUnamSl;
    }

    public Amort() {
        ref = 0;
        rFeeBal = 0.0;
        nrUnamSl = 0.0;
        cUnamSl = 0.0;
    }

    public double getLifeEx1() {
        return lifeEx1;
    }
    
    public double getLifeEx2() {
        return lifeEx2;
    }

    public double getRActRef() {
        return rActRef;
    }
    
    public double getRNewFees() {
        return rNewFees;
    }
    
    public double getNrActRef() {
        return nrActRef;
    }
    
    public double getCActRef() {
        return cActRef;
    }
    
    public double getNrNewSl() {
        return nrNewSl;
    }
    
    public double getNrAmorSl() {
        return nrAmorSl;
    }
    
    public double getNrTermSl() {
        return nrTermSl;
    }
    
    public double getCNewSl() {
        return cNewSl;
    }
    
    public double getCAmorSl() {
        return cAmorSl;
    }
    
    public double getCTermSl() {
        return cTermSl;
    }
    
    public double getCUnamSl() {
        return cUnamSl;
    }
    
    public double getNrNewCC() {
        return nrNewCC;
    }
    
    public double getNrAmorCC() {
        return nrAmorCC;
    }
    
    public double getNrTermCC() {
        return nrTermCC;
    }
    
    public double getNrUnamCC() {
        return nrUnamCC;
    }
    
    public double getCNewCC() {
        return cNewCC;
    }
    
    public double getCAmorCC() {
        return cAmorCC;
    }
    
    public double getCTermCC() {
        return cTermCC;
    }
    
    public double getCUnamCC() {
        return cUnamCC;
    }
    
    public int getRef() {
        return ref;
    }
    
    public double getRFeeBal() {
        return rFeeBal;
    }
    
    public double getNrUnamSl() {
        return nrUnamSl;
    }
}
