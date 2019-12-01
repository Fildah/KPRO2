package model;

import java.util.ArrayList;
import java.util.List;

public class Spis {
    private List<Dokument> dokumenty = new ArrayList<>();

    public void zarad(Dokument dokument){
        dokumenty.add(dokument);
    }

    public void odeber(String cj){
        najdiDokument(cj).setAktivni(false);
    }

    public int velikostSpisu(){
        return dokumenty.size();
    }

    public Dokument najdiDokument(String cj){
        Dokument dokument = dokumenty.get(0);
        for(int i = 0; i < velikostSpisu(); i++){
            if (dokumenty.get(i).getCj() == cj) {
                dokument = dokumenty.get(i);
            }
        }
        return dokument;
    }

    public Dokument najdiDokument(int i) {
        return dokumenty.get(i);
    }
}
