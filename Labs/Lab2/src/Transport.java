import tester.Tester;

// Represents a mode of transportation
interface IMOT {

  //returns true if this mode of transportation is at least
  //as efficient as the given mpg, false otherwise
  boolean isMoreFuelEfficientThan(int mpg);

}

// Represents a bicycle as a mode of transportation
class Bicycle implements IMOT {
  String brand;

  Bicycle(String brand) {
    this.brand = brand;
  }

  public boolean isMoreFuelEfficientThan(int mpg) {
    return true;
  }

}

// Represents a car as a mode of transportation
class Car implements IMOT {
  String make;
  int mpg; // represents the fuel efficiency in miles per gallon

  Car(String make, int mpg) {
    this.make = make;
    this.mpg = mpg;
  }

  public boolean isMoreFuelEfficientThan(int mpg) {
    return this.mpg >= mpg;
  }
}

// Keeps track of how a person is transported
class Person {
  String name;
  IMOT mot;

  Person(String name, IMOT mot) {
    this.name = name;
    this.mot = mot;
  }

  //Does this person's mode of transportation meet the given fuel
  //efficiency target (in miles per gallon)?

  //In the Person class:
  boolean motMeetsFuelEfficiency(int mpg) {
    return this.mot.isMoreFuelEfficientThan(mpg);
  }


}

class ExamplesPerson {
  IMOT diamondback = new Bicycle("Diamondback");
  IMOT toyota = new Car("Toyota", 30);
  IMOT lamborghini = new Car("Lamborghini", 17);

  Person bob = new Person("Bob", diamondback);
  Person ben = new Person("Ben", toyota);
  Person becca = new Person("Becca", lamborghini);

  //test the method salePrice for the class Book
  boolean testFuelEfficiency(Tester t) {
    return t.checkExpect(this.bob.motMeetsFuelEfficiency(8), true)
        && t.checkExpect(this.becca.motMeetsFuelEfficiency(100), false);
  }
}