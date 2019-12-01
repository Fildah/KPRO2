package model;

public class Cj {
    private long pocitadlo = 1;
    private String cjTemplate = "UHK\\";

    public String getCj(){
        String poradi = Long.toString(pocitadlo);
        String cj = cjTemplate;
        for(int i = 1; i != (6-poradi.length()); i++){
            cj = cj + "0";
        }
        cj = cj + poradi;
        pocitadlo += 1;
        return cj;
    }

    public long getPocitadlo() {
        return pocitadlo;
    }

    public String getCjTemplate() {
        return cjTemplate;
    }

    public void setPocitadlo(long pocitadlo){
        this.pocitadlo = pocitadlo;
    }

    public void setCjTemplate(String cjTemplate) {
        this.cjTemplate = cjTemplate;
    }
}
