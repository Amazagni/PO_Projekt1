package agh.ics.oop;

import com.sun.tools.jconsole.JConsoleContext;

import java.util.*;

public class EarthMap implements IWorldMap {
    Vector2d lowerLeft = new Vector2d(0,0);
    Vector2d upperRight;
    Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    Map<Vector2d,Grass> clumpsOfGrass = new HashMap<>();

    MapVisualizer toVisualize;
    public EarthMap(Vector2d upperRight){

        this.upperRight = upperRight;
        this.toVisualize = new MapVisualizer(this);

    }

    public void place(Animal animal){
        Vector2d position = animal.getPosition();
        //tworze nowy array
        ArrayList<Animal> tmp;
        if(this.animals.get(position) == null){
            tmp = new ArrayList<Animal>();
            tmp.add(animal);
            this.animals.put(position,tmp);
        }
        //dodaje do arraya
        else {
            tmp = new ArrayList<Animal>(this.animals.get(position));
            tmp.add(animal);
            this.animals.remove(position);
            //array zawsze utrzymuje posortowany po aktualnej energii;
            tmp.sort(new AnimalEnergyComparator());
            this.animals.put(position,tmp);

        }
//        System.out.println(position);
//        System.out.println(isOccupied(position));
    }

    @Override
    public Object objectAt(Vector2d position) {
         if(this.animals.get(position) != null){
             return this.animals.get(position);
         }
         return this.clumpsOfGrass.get(position);
    }

    public void placeGrass(Vector2d position){
        this.clumpsOfGrass.put(position,new Grass(position));

    }
    public Vector2d getUpperRight(){
        return this.upperRight;
    }
    public String toString(){
        return this.toVisualize.draw(new Vector2d(0,0),getUpperRight());}

    //isOccupied potrzebne do rysowania mapy
    public boolean isOccupied(Vector2d position){
        if(this.animals.get(position) != null || this.clumpsOfGrass.get(position)!= null)return true;
        return false;

    }
    public Map<Vector2d,ArrayList<Animal>> returnAnimals(){
        return animals;
    }
    public void updateAnimals(Map<Vector2d, ArrayList<Animal>> animals){
        this.animals = animals;
    }
//    public void plantTheGrass(){
//
//    }
}
