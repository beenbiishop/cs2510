import tester.Tester;

//represents a list of strings
interface ILoString {

  // returns a new list with only the elements in this list after
  // the given start position
  ILoString remainingElements(int start);

  // Are all the elements of a given list contained in this list?
  boolean allContainedIn(ILoString other);

  // Is this list of strings an empty list?
  boolean isMtList();

  // Is a given string in this list?
  boolean containsString(String string);

}

//represents an empty list of strings
class MtLoString implements ILoString {

  // returns a new list with only the elements in this list after
  // the given start position
  public ILoString remainingElements(int start) {
    return this;
  }

  // Are all the elements of a given list contained in this list?
  public boolean allContainedIn(ILoString other) {
    return other.isMtList();
  }

  // Is this list of strings an empty list?
  public boolean isMtList() {
    return true;
  }

  //Is a given string in this list?
  public boolean containsString(String string) {
    return string.equals("");
  }
}

//represents a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  // returns a new list with only the elements in this list after
  // the given start position
  public ILoString remainingElements(int start) {
    if (start > 0) {
      return this.rest.remainingElements(start - 1);
    }
    else {
      return this;
    }
  }

  // Are all the elements of a given list contained in this list?
  public boolean allContainedIn(ILoString other) {
    if (other.isMtList()) {
      return true;
    }
    else {
      return other.containsString(this.first) &&
          this.rest.allContainedIn(other);
    }
  }

  // Is this list of strings an empty list?
  public boolean isMtList() {
    return false;
  }

  // Is a given string in this list?
  public boolean containsString(String string) {
    if (this.first.equals(string)) {
      return true;
    }
    else {
      return this.rest.containsString(string);
    }
  }
}

class ExamplesLists {
  ExamplesLists(){}

  // examples of ILoStrings
  ILoString greeting = new ConsLoString("Hello",
      new ConsLoString("there",
          new ConsLoString("how",
              new ConsLoString("are",
                  new ConsLoString("you",
                      new MtLoString())))));
  ILoString partialGreetingYou = new ConsLoString("how",
      new ConsLoString("you",
          new MtLoString()));
  ILoString partialGreetingToday = new ConsLoString("how",
      new ConsLoString("today",
          new MtLoString()));
  ILoString empty = new MtLoString();

  // tests of the remainingElements method
  boolean testRemainingElements(Tester t) {
    return
        t.checkExpect(this.greeting.remainingElements(1),
            new ConsLoString("there",
                new ConsLoString("how",
                    new ConsLoString("are",
                        new ConsLoString("you",
                            new MtLoString()))))) &&
        t.checkExpect(this.greeting.remainingElements(-1),
            this.greeting) &&
        t.checkExpect(this.greeting.remainingElements(5), this.empty) &&
        t.checkExpect(this.empty.remainingElements(3), this.empty);
  }

  // tests of the allContainedIn function and it's helpers
  boolean testAllContainedIn(Tester t) {
    return
        t.checkExpect(this.greeting.containsString("how"), true) &&
        t.checkExpect(this.greeting.containsString("ben"), false) &&
        t.checkExpect(this.greeting.isMtList(), false) &&
        t.checkExpect(this.empty.isMtList(), true) &&
        t.checkExpect(this.greeting.allContainedIn(this.partialGreetingYou), true) &&
        t.checkExpect(this.greeting.allContainedIn(this.partialGreetingToday), false);
  }
}