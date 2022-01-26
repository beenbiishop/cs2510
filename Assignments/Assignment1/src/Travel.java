/*
                         +----------+
                         | IHousing |
                         +----------+
                         +----------+
                               |
                              / \
                              ---
                               |
         ---------------------------------------------
         |                     |                     |
+----------------+    +----------------+    +-------------------+
| Hut            |    | Inn            |    | Castle            |
+----------------+    +----------------+    +-------------------+
| int capacity   |    | String name    |    | String name       |
| int population |    | int capacity   |    | String familyName |
+----------------+    | int population |    | int population    |
                      | int stalls     |    | int carriageHouse |
                      +----------------+    +-------------------+


                     +---------------------+
                     |   ITransportation   |
                     +---------------------+
                     +---------------------+
                                |
                               / \
                               ---
                                |
                     -----------------------
                     |                     |
             +---------------+     +---------------+
             | Horse         |     | Carriage      |
             +---------------+     +---------------+
             | IHousing from |     | IHousing from |
             | IHousing to   |     | IHousing to   |
             | String name   |     | int tonnage   |
             | String color  |     +---------------+
             +---------------+
 */

// to represent a type of housing
interface IHousing {}


// to represent a hut, which is a type of housing
class Hut implements IHousing {
  int capacity;
  int population;

  // the constructor
  Hut(int capacity, int population) {
    this.capacity = capacity;
    this.population = population;
  }
}


// to represent an inn, which is a type of housing
class Inn implements IHousing {
  String name;
  int capacity;
  int population;
  int stalls;

  // the constructor
  Inn(String name, int capacity, int population, int stalls) {
    this.name = name;
    this.capacity = capacity;
    this.population = population;
    this.stalls = stalls;
  }
}


// to represent a castle, which is a type of housing
class Castle implements IHousing {
  String name;
  String familyName;
  int population;
  int carriageHouse;

  // the constructor
  Castle(String name, String familyName, int population, int carriageHouse) {
    this.name = name;
    this.familyName = familyName;
    this.population = population;
    this.carriageHouse = carriageHouse;
  }
}


// to represent a type of transportation
interface ITransportation {}


// to represent a horse, which is a type of transportation
class Horse implements ITransportation {
  IHousing from;
  IHousing to;
  String name;
  String color;

  // the constructor
  Horse(IHousing from, IHousing to, String name, String color) {
    this.from = from;
    this.to = to;
    this.name = name;
    this.color = color;
  }
}


// to represent a carriage, which is a type of transportation
class Carriage implements ITransportation {
  IHousing from;
  IHousing to;
  int tonnage;

  // the constructor
  Carriage(IHousing from, IHousing to, int tonnage) {
    this.from = from;
    this.to = to;
    this.tonnage = tonnage;
  }
}


// examples for the classes that represent housing and transportation
class ExamplesTravel {
  ExamplesTravel() {}
  
  // Housing examples
  IHousing hovel = new Hut(5, 1);
  IHousing winterfell = new Castle("Winterfell", "Stark", 500, 6);
  IHousing crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);
  IHousing super8 = new Hut(30, 26);
  IHousing disney = new Castle("Disneyland Castle", "Disney", 5000, 200);
  IHousing slummy = new Inn("Slummy's Inn", 58, 2, 12);
  
  // Transportation examples
  ITransportation horse1 = new Horse(this.crossroads, this.disney, "Buster", "Light Green");
  ITransportation horse2 = new Horse(this.slummy, this.hovel, "Ginny", "Lilac");
  ITransportation carriage1 = new Carriage(this.winterfell, this.crossroads, 30);
  ITransportation carriage2 = new Carriage(this.disney, this.slummy, 12);
}

