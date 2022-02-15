// CS 2510, Lab 4

// Pooja Gidwani, Ben Bishop

// represents a book in a library
interface IBook {

}

abstract class ABook implements IBook {
  String title;
  int dayTaken;

  ABook(String title, int dayTaken) {
    this.title = title;
    this.dayTaken = dayTaken;
  }

  public boolean isOverdue(int day) {
    return (day - this.dayTaken) > 14;
  }

  public abstract double computeFine(int day);
}

// represents a regular book - 2 weeks
class Book extends ABook {
  String author;

  // the constructor
  Book(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }

  public double computeFine(int day) {
    if(this.isOverdue(day)) {
      return 0.10 * (day - this.dayTaken - 14);
    }
    else {
      return 0.0;
    }
  }
}

// represents a reference book - 2 days
class RefBook extends ABook {

  // the constructor
  RefBook(String title, int dayTaken) {
    super(title, dayTaken);
  }

  @Override
  public boolean isOverdue(int day) {
    return (day - this.dayTaken) > 2;
  }

  public double computeFine(int day) {
    if(this.isOverdue(day)) {
      return 0.10 * (day - this.dayTaken - 2);
    }
    else {
      return 0.0;
    }
  }
}

// represents an audio book - 2 weeks
class AudioBook extends ABook {
  String author;

  // the constructor
  AudioBook(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }

  public double computeFine(int day) {
    if(this.isOverdue(day)) {
      return 0.20 * (day - this.dayTaken - 14);
    }
    else {
      return 0.0;
    }
  }
}

