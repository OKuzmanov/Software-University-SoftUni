package exams.javaOopRetakeExamAugust2021.glacialExpedition.models.suitcases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Carton implements Suitcase{
    private List<String> exhibits;

    public Carton() {
        exhibits = new ArrayList<>();
    }

    @Override
    public Collection<String> getExhibits() {
        return exhibits;
    }
}
