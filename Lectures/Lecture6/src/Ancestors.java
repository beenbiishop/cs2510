import tester.*;

interface IAT {
  //To compute the number of known ancestors of this ancestor tree
  //(excluding this ancestor tree itself)
  int count();

  //To compute the number of known ancestors of this ancestor tree (*including* this ancestor tree itself)
  int countHelp();

  //To compute how many ancestors of this ancestor tree (excluding this ancestor tree itself)
  //are women older than 40 (in the current year).
  int femaleAncOver40();

  //To compute how many ancestors of this ancestor tree (*including* this ancestor tree itself)
  //are women older than 40 (in the current year).
  int femaleAncOver40Help();

  int numTotalGens();

  int numTotalGensHelp();


}

class Unknown implements IAT {
  Unknown() {}

  //To compute the number of known ancestors of this Unknown (excluding this Unknown itself)
  public int count() {
    return 0;
  }

  //To compute the number of known ancestors of this Unknown (*including* this Unknown itself)
  public int countHelp() {
    return 0;
  }

  //To compute how many ancestors of this Unknown (excluding this Unknown itself)
  //are women older than 40 (in the current year).
  public int femaleAncOver40() {
    return 0;
  }

  //To compute how many ancestors of this Unknown (excluding this Unknown itself)
  //are women older than 40 (in the current year).
  public int femaleAncOver40Help() {
    return 0;
  }

  public int numTotalGens() {
    return 0;
  }

  public int numTotalGensHelp() {
    return 0;
  }
}

class Person implements IAT {
  String name;
  int yob;
  boolean isMale;
  IAT mom;
  IAT dad;
  Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
    this.name = name;
    this.yob = yob;
    this.isMale = isMale;
    this.mom = mom;
    this.dad = dad;
  }

  //To compute the number of known ancestors of this Person (excluding this Person itself)
  public int count() {
    return this.mom.countHelp() + this.dad.countHelp();
  }

  //To compute the number of known ancestors of this Person (*including* this Person itself)
  public int countHelp() {
    return 1 + this.mom.countHelp() + this.dad.countHelp();
  }

  //To compute how many ancestors of this Person (excluding this Person itself)
  //are women older than 40 (in the current year).
  public int femaleAncOver40() {
    return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
  }

  public int femaleAncOver40Help() {
    if ((2015 - this.yob > 40) && (this.isMale == false)) {
      return 1 + this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
    }
    else {
      return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
    }
  }


  public int numTotalGens() {
    if (this.mom.numTotalGensHelp() > this.dad.numTotalGensHelp()) {
      return this.dad.numTotalGensHelp();
    }
    else {
      return this.mom.numTotalGensHelp();
    }
  }

  public int numTotalGensHelp() {
    return 1 + this.mom.numTotalGensHelp() + this.dad.numTotalGensHelp();
  }

}

interface ILoString {

}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
}

class MtLoString implements ILoString {
  MtLoString() { }
}

class ExamplesIAT {
  IAT enid = new Person("Enid", 1904, false, new Unknown(), new Unknown());
  IAT edward = new Person("Edward", 1902, true, new Unknown(), new Unknown());
  IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
  IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());

  IAT david = new Person("David", 1925, true, new Unknown(), this.edward);
  IAT daisy = new Person("Daisy", 1927, false, new Unknown(), new Unknown());
  IAT dana = new Person("Dana", 1933, false, new Unknown(), new Unknown());
  IAT darcy = new Person("Darcy", 1930, false, this.emma, this.eustace);
  IAT darren = new Person("Darren", 1935, true, this.enid, new Unknown());
  IAT dixon = new Person("Dixon", 1936, true, new Unknown(), new Unknown());

  IAT clyde = new Person("Clyde", 1955, true, this.daisy, this.david);
  IAT candace = new Person("Candace", 1960, false, this.dana, this.darren);
  IAT cameron = new Person("Cameron", 1959, true, new Unknown(), this.dixon);
  IAT claire = new Person("Claire", 1956, false, this.darcy, new Unknown());

  IAT bill = new Person("Bill", 1980, true, this.candace, this.clyde);
  IAT bree = new Person("Bree", 1981, false, this.claire, this.cameron);

  IAT andrew = new Person("Andrew", 2001, true, this.bree, this.bill);

  boolean testCount(Tester t) {
    return
        t.checkExpect(this.andrew.count(), 16) &&
        t.checkExpect(this.david.count(), 1) &&
        t.checkExpect(this.enid.count(), 0) &&
        t.checkExpect(new Unknown().count(), 0);
  }
  boolean testFemaleAncOver40(Tester t) {
    return
        t.checkExpect(this.andrew.femaleAncOver40(), 7) &&
        t.checkExpect(this.bree.femaleAncOver40(), 3) &&
        t.checkExpect(this.darcy.femaleAncOver40(), 1) &&
        t.checkExpect(this.enid.femaleAncOver40(), 0) &&
        t.checkExpect(new Unknown().femaleAncOver40(), 0);
  }
  boolean testNumGens(Tester t) {
    return
        t.checkExpect(this.andrew.numTotalGens(), 3) &&
        t.checkExpect(this.enid.numTotalGens(), 1) &&
        t.checkExpect(new Unknown().numTotalGens(), 0);
  }

}