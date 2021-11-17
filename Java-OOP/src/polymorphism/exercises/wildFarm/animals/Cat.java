package polymorphism.exercises.wildFarm.animals;

import polymorphism.exercises.wildFarm.abstractClasses.Felime;
import polymorphism.exercises.wildFarm.abstractClasses.Food;

import java.text.DecimalFormat;

public class Cat extends Felime {
    String breed;

    public Cat(String animalName, String animalType, Double animalWeight, String livingRegion, String breed) {
        super(animalName, animalType, animalWeight, livingRegion);
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        System.out.println("Meowwww");
    }

    @Override
    public void eat(Food food) {
        Integer currFood = super.getFoodEaten();
        super.setFoodEaten(currFood + food.getQuantity());
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return String.format("%s[%s, %s, %s, %s, %d]",
                super.getAnimalType(),
                super.getAnimalName(),
                this.breed,
                formatter.format(super.getAnimalWeight()),
                super.getLivingRegion(),
                super.getFoodEaten());
    }
}
