import tester.Tester;

//represents an ancestor tree
interface IAT { 

}

//represents a leaf on an ancestor tree
class Unknown implements IAT { 

}

//represents a person in an ancestor tree
class Person implements IAT {
  String name;
  int yob;
  IAT dad; 
  IAT mom;

  Person(String name, int yob, IAT dad, IAT mom) {
    this.name = name; 
    this.yob = yob;
    this.dad = dad; 
    this.mom = mom;
  } 
}

class ExamplesIAT {
  IAT unknown = new Unknown();
  IAT enid = new Person("Enid", 1904, unknown, unknown);
  IAT edward = new Person("Edward", 1902, unknown, unknown);
  IAT emma = new Person("Emma", 1906, unknown, unknown);
  IAT eustace = new Person("Andrew", 1907, unknown, unknown);

  IAT david = new Person("David", 1925, unknown, this.edward);
  IAT daisy = new Person("Daisy", 1927, unknown, unknown);
  IAT dana = new Person("Dana", 1933, unknown, unknown);
  IAT darcy = new Person("Darcy", 1930, this.emma, this.eustace);
  IAT darren = new Person("Darren", 1935, this.enid, unknown);
  IAT dixon = new Person("Dixon", 1936, unknown, unknown);

  IAT clyde = new Person("Clyde", 1955, this.daisy, this.david);
  IAT candace = new Person("Candace", 1960, this.dana, this.darren);
  IAT cameron = new Person("David", 1959, unknown, this.dixon);
  IAT claire = new Person("Enid", 1956, this.darcy, unknown);

  IAT bill = new Person("Bill", 1980, this.candace, this.clyde);
  IAT bree = new Person("Bree", 1981, this.claire, this.cameron);

  IAT andrew = new Person("Andrew", 2001, this.bree, this.bill);
}












