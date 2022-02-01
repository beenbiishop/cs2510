import tester.Tester;

// to represent a pet owner
class Person2 {
  String name;
  IPet pet;
  int age;

  Person2(String name, IPet pet, int age) {
    this.name = name;
    this.pet = pet;
    this.age = age;
  }

  //is this Person older than the given Person?
  boolean isOlder(Person2 other) {
    return this.age > other.age;
  }

  Person2 perish() {
    return new Person2(this.name, new NoPet(), this.age);
  }

}
// to represent a pet
interface IPet {

  //does the name of this person's pet match the given name?
  boolean sameNamePet(String name);

}

// to represent a pet cat
class Cat implements IPet {
  String name;
  String kind;
  boolean longhaired;

  Cat(String name, String kind, boolean longhaired) {
    this.name = name;
    this.kind = kind;
    this.longhaired = longhaired;
  }

  public boolean sameNamePet(String name) {
    return this.name.equals(name);
  }
}

// to represent a pet dog
class Dog implements IPet {
  String name;
  String kind;
  boolean male;

  Dog(String name, String kind, boolean male) {
    this.name = name;
    this.kind = kind;
    this.male = male;
  }

  public boolean sameNamePet(String name) {
    return this.name.equals(name);
  }
}

// to represent a deceased or nonexistent pet
class NoPet implements IPet {

  NoPet() {}

  public boolean sameNamePet(String name) {
    return false;
  }

}

class ExamplesPerson2 {
  ExamplesPerson2() {}

  //Pet examples
  Cat murray = new Cat("Murray", "Siamese", false);
  Cat sleezy = new Cat("Sleezy", "Unknown", true);
  Dog pluto = new Dog("Pluto", "Golden Retriever", true);
  Dog jess = new Dog("Jess", "Labrador", false);
  NoPet empty = new NoPet();

  // Pet owner examples
  Person2 ben = new Person2("Ben", this.murray, 18);
  Person2 pooja = new Person2("Pooja", this.sleezy, 18);
  Person2 ash = new Person2("Ash", this.pluto, 22);
  Person2 tallBen = new Person2("Tall Ben", this.jess, 54);
  Person2 sadeem = new Person2("Sadeem", empty, 19);

  //test the method isOlder for the class Person2
  boolean testOlder(Tester t) {
    return t.checkExpect(this.ash.isOlder(this.tallBen), false)
        && t.checkExpect(this.sadeem.isOlder(this.pooja), true);
  }

  // test the method perish for the class Person2
  boolean testPerish(Tester t) {
    return t.checkExpect(this.ben.perish(), new Person2("Ben", this.empty, 18))
        && t.checkExpect(this.sadeem.perish(), new Person2("Sadeem", this.empty, 19));
  }

  //test the method sameNamePet for the class Person2
  boolean testOwner(Tester t) {
    return t.checkExpect(this.murray.sameNamePet("Murray"), true)
        && t.checkExpect(this.pluto.sameNamePet("Jess"), false);
  }

}