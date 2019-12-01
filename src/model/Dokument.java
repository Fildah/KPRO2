package model;

public class Dokument {
    private String cj;
    private String vec;
    private String detail;
    private boolean aktivni;


    public Dokument(String cj, String vec, String detail){
        this.cj = cj;
        this.vec = vec;
        this.detail = detail;
        aktivni = true;
    }

    public String getCj() {
        return cj;
    }

    public String getDetail() {
        return detail;
    }

    public String getVec() {
        return vec;
    }

    public boolean getAktivni() {
        return aktivni;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setVec(String vec) {
        this.vec = vec;
    }

    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
    }
}
