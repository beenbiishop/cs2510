import tester.*;

class Book {
  String title;
  String author;
  int year;
  double price;

  Book(String title, String author, int year, double price) {
    this.title = title;
    this.author = author;
    this.year = year;
    this.price = price;
  }

  // what is the sale price of this book?
  double salePrice(int discount) {
    return this.price - (discount * this.price);
  }

  //was this book published before the given year?
  boolean publishedBefore(int year) {
    return this.year < year;
  }

  //is the price of this book cheaper than the price of the given book?
  boolean cheaperThan(Book that) {
    return this.price < that.price;
  }

  // is the title of this book lexicographically before the title of the given book?
  boolean titleBefore(Book that) {
    return (this.title.compareTo(that.title) < 0);
  }
}

interface ILoBook {
  //count the books in this list
  int count();

  //produce a list of all books published before the given date
  //from this list of books
  ILoBook allBefore(int year);

  //calculate the total sale price of all books in this list for a given discount
  double salePrice(int discount);

  //produce a list of all books in this list, sorted by their price
  ILoBook sortByPrice();

  // produce a list of all books in this list, sorted by their title
  ILoBook sortByTitle();

  //insert the given book into this list of books
  //already sorted by price
  ILoBook insert(Book b);

  ILoBook insertByTitle(Book b);
}

class MtLoBook implements ILoBook {
  MtLoBook() {}

  //count the books in this list
  public int count() { return 0; }

  //produce a list of all books published before the given date
  //from this empty list of books
  public ILoBook allBefore(int year) { return this; }

  //calculate the total sale price of all books in this list for a given discount
  public double salePrice(int discount) { return 0; }

  //produce a list of all books in this list, sorted by their price
  public ILoBook sortByPrice() {
    return this;
  }

  //produce a list of all books in this list, sorted by their title
  public ILoBook sortByTitle() {
    return this;
  }

  //insert the given book into this empty list of books
  //already sorted by price
  public ILoBook insert(Book b) {
    return new ConsLoBook(b, this);
  }

  public ILoBook insertByTitle(Book b) {
    return new ConsLoBook(b, this);
  }
}

class ConsLoBook implements ILoBook {
  Book first;
  ILoBook rest;

  ConsLoBook(Book first, ILoBook rest) {
    this.first = first;
    this.rest = rest;
  }

  // count the books in this list
  public int count() {
    return 1 + this.rest.count();
  }

  //calculate the total sale price of all books in this list for the given discout
  public double salePrice(int discount) {
    return this.first.salePrice(discount) + this.rest.salePrice(discount);
  }

  public ILoBook allBefore( int year ) {
    if (this.first.publishedBefore(year)) {
      return new ConsLoBook(this.first, this.rest.allBefore(year));
    }
    else {
      return this.rest.allBefore(year);
    }
  }

  //insert the given book into this list of books
  //already sorted by price
  public ILoBook insert(Book b) {
    if (this.first.cheaperThan(b)) {
      return new ConsLoBook(this.first, this.rest.insert(b));
    }
    else {
      return new ConsLoBook(b, this);
    }
  }

  public ILoBook insertByTitle(Book b) {
    if (this.first.titleBefore(b)) {
      return new ConsLoBook(this.first, this.rest.insertByTitle(b));
    }
    else {
      return new ConsLoBook(b, this);
    }
  }

  //produces a list of the books in this non-empty list, sorted by price
  public ILoBook sortByPrice() {
    return this.rest.sortByPrice().insert(this.first);
  }

  //produce a list of all books in this list, sorted by their title
  public ILoBook sortByTitle() {
    return this.rest.sortByTitle().insertByTitle(this.first);
  }
}

class ExamplesBooks {
  //Books
  Book htdp = new Book("HtDP", "MF", 2001, 60);
  Book lpp = new Book("LPP", "STX", 1942, 25);
  Book ll = new Book("LL", "FF", 1986, 10);

  // lists of Books
  ILoBook mtlist = new MtLoBook();
  ILoBook lista = new ConsLoBook(this.lpp, this.mtlist);
  ILoBook listb = new ConsLoBook(this.htdp, this.mtlist);
  ILoBook listc = new ConsLoBook(this.lpp,
      new ConsLoBook(this.ll, this.listb));
  ILoBook listd = new ConsLoBook(this.ll,
      new ConsLoBook(this.lpp,
          new ConsLoBook(this.htdp, this.mtlist)));

  //tests for the method count
  boolean testCount(Tester t) {
    return
        t.checkExpect(this.mtlist.count(), 0) &&
        t.checkExpect(this.lista.count(), 1) &&
        t.checkExpect(this.listd.count(), 3);
  }

  //tests for the method salePrice
  boolean testSalePrice(Tester t) {
    return
        // no discount -- full price
        t.checkInexact(this.mtlist.salePrice(0), 0.0, 0.001) &&
        t.checkInexact(this.lista.salePrice(0), 10.0, 0.001) &&
        t.checkInexact(this.listc.salePrice(0), 95.0, 0.001) &&
        t.checkInexact(this.listd.salePrice(0), 95.0, 0.001) &&
        // 50% off sale -- half price
        t.checkInexact(this.mtlist.salePrice(50), 0.0, 0.001) &&
        t.checkInexact(this.lista.salePrice(50), 5.0, 0.001) &&
        t.checkInexact(this.listc.salePrice(50), 47.5, 0.001) &&
        t.checkInexact(this.listd.salePrice(50), 47.5, 0.001);
  }

  //tests for the method allBefore
  boolean testAllBefore(Tester t) {
    return
        t.checkExpect(this.mtlist.allBefore(2001), this.mtlist) &&
        t.checkExpect(this.lista.allBefore(2001), this.lista) &&
        t.checkExpect(this.listb.allBefore(2001), this.mtlist) &&
        t.checkExpect(this.listc.allBefore(2001),
            new ConsLoBook(this.lpp, new ConsLoBook(this.ll, this.mtlist))) &&
        t.checkExpect(this.listd.allBefore(2001),
            new ConsLoBook(this.ll, new ConsLoBook(this.lpp, this.mtlist)));
  }

  //test the method sort for the lists of books
  boolean testSort(Tester t) {
    return
        t.checkExpect(this.listc.sortByPrice(), this.listd) &&
        t.checkExpect(this.listd.sortByPrice(), this.listd);
  }

}