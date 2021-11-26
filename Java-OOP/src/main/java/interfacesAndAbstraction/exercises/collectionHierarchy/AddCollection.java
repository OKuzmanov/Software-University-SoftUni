package interfacesAndAbstraction.exercises.collectionHierarchy;

public class AddCollection extends Collection implements Addable{

    @Override
    public int add(String element) {
        super.getItems().add(element);
        return super.getItems().size() - 1;
    }
}
