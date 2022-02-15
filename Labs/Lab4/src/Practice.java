// CS 2510, Lab 4

// Pooja Gidwani, Ben Bishop

// represents the interface for a maybeInt
interface IMaybeInt {}

// represents a non-empty value
class IsInt implements IMaybeInt {

  int value;
  // the constructor
  IsInt(int value) {
    this.value = value;
  }
}

// represents an empty value
class EmptyInt implements IMaybeInt {

  // the constructor
  EmptyInt() {}
}

//represents a list of integers
interface ILoInt {
  int max(int runningMax);
}

// represents an empty list of integers
class MtLoInt implements ILoInt {

  // the constructor
  MtLoInt() {}

  public int max(int runningMax) {
    return runningMax;
  }
}

// represents a list of integers
class ConsLoInt implements ILoInt {
  int first;
  ILoInt rest;

  // the constructor
  ConsLoInt(int first, ILoInt rest) {
    this.first = first;
    this.rest = rest;
  }

  public int max(int runningMax) {
    if(this.first > runningMax) {
      return this.rest.max(this.first);
    }
    else {
      return this.rest.max(runningMax);
    }
  }
}

// represents a task
class Task {
  IMaybeInt id;
  ILoInt prerequisites;

  Task(IMaybeInt id, ILoInt prerequisites) {
    this.id = id;
    this.prerequisites = prerequisites;
  }
}



