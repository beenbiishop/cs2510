import tester.Tester;

//represents an ancestor tree
interface IAT {
  // returns the number of generations away from this IAT the person
  // with the given name is
  int numGensAway(String name);

  // returns the largest secret number of any person in this IAT
  int largestSecretNumber();
}

//represents a leaf on an ancestor tree
class Unknown implements IAT {
  // returns the number of generations away from this IAT the person
  // with the given name is
  public int numGensAway(String name) {
    return -1;
  }

  // returns the largest secret number of any person in this IAT
  int largestSecretNumber() {
    throw new IllegalStateException("Unknown people do not have secret numbers");
  }
}

//represents a person in an ancestor tree
class Person implements IAT { 
  String name;
  int secretNumber;
  IAT dad; 
  IAT mom;

  Person(String name, int secretNumber, IAT dad, IAT mom) {
    this.name = name; 
    this.secretNumber = secretNumber;
    this.dad = dad; 
    this.mom = mom;
  }

  // returns the number of generations away from this IAT the person
  // with the given name is
  public int numGensAway(String name) {
    if (this.name.equals(name)) {
      return 0;
    }
    else {
      return 1 + Math.abs(this.mom.numGensAway(name) - this.dad.numGensAway(name));
    }

    // returns the largest secret number of any person in this IAT
    public int largestSecretNumber() {
      return this.secretNumber;
    }
  }
}

class ExamplesIAT {
  ExamplesIAT(){}

  IAT davisSr = new Person("DavisSr", -23, new Unknown(), new Unknown()); 
  IAT edna = new Person("Edna", 67, new Unknown(), new Unknown());
  IAT davisJr = new Person("DavisJr", 23, davisSr, edna);
  IAT carl = new Person("Carl", 0, new Unknown(), new Unknown());
  IAT candace = new Person("Candace",  2, davisJr, new Unknown());
  IAT claire = new Person("Claire", 99, new Unknown(), new Unknown()); 
  IAT bill = new Person("Bill", 0, carl, candace);
  IAT bree = new Person("Bree", 10, new Unknown(), claire);
  IAT anthony = new Person("Anthony", -12, bill, bree);

  boolean testNumGensAway(Tester t) {
    return
        t.checkExpect(this.anthony.numGensAway("Candace"), 2) &&
        t.checkExpect(this.anthony.numGensAway("DavisJr"), 3) &&
        t.checkExpect(this.anthony.numGensAway("Anthony"), 0);
  }
}