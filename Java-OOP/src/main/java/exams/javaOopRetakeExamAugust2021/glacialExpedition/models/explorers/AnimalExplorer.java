package exams.javaOopRetakeExamAugust2021.glacialExpedition.models.explorers;

public class AnimalExplorer extends BaseExplorer{
    private static final double INITIAL_ENERGY = 100;

    public AnimalExplorer(String name) {
        super(name, INITIAL_ENERGY);
    }
}
