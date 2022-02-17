// CS 2510, Assignment 4

// Pooja Gidwani and Ben Bishop

import tester.Tester;

// Represents a type of entertainment
interface IEntertainment {

  //compute the total price of this Entertainment
  double totalPrice();

  //computes the minutes of entertainment of this IEntertainment
  int duration();

  //is this IEntertainment the same as that one?
  boolean sameEntertainment(IEntertainment that);

  //is this IEntertainment the same as that Magazine?
  boolean sameMagazine(Magazine thisObj);

  //is this IEntertainment the same as that TVSeries?
  boolean sameTVSeries(TVSeries thisObj);

  //is this IEntertainment the same as that Podcast?
  boolean samePodcast(Podcast thisObj);

  //produce a String that shows the name and price of this IEntertainment
  String format();
}

// Represents a type of entertainment
abstract class AEntertainment implements IEntertainment {

  /*
  TEMPLATE
  FIELDS:
  ... this.name ...          -- String
  ... this.price ...         -- double
  ... this.installments...   -- int

  METHODS
  ... this.totalPrice() ...                             -- Double
  ... this.duration() ...                               -- int
  ... this.sameEntertainment(IEntertainment that)...    -- boolean
  ... this.sameMagazine(Magazine thisObj)...            -- boolean
  ... this.sameTVSeries(TVSeries thisObj)...            -- boolean
  ... this.samePodcast(Podcast thisObj)...              -- boolean
  ... this.format()...                                  -- boolean
   */

  String name; //name of entertainment
  double price; //represents price per issue
  int installments; //number of issues per year

  //constructor
  AEntertainment(String name, double price, int installments) {
    this.name = name;
    this.price = price;
    this.installments = installments;
  }

  //computes the price of a yearly subscription to this IEntertainment
  public double totalPrice() {
    return this.price * this.installments;
  }

  //computes the minutes of entertainment of this IEntertainment
  public int duration() {
    return 50 * installments;
  }

  //is this IEntertainment the same as that IEntertainment?
  public abstract boolean sameEntertainment(IEntertainment that);

  //is this IEntertainment the same as that Magazine
  public boolean sameMagazine(Magazine thisObj) {
    return false;
  }

  //is this IEntertainment the same as that TVSeries?
  public boolean sameTVSeries(TVSeries thisObj) {
    return false;
  }

  //is this IEntertainment the same as that Podcast?
  public boolean samePodcast(Podcast thisObj) {
    return false;
  }

  //produce a String that shows the name and price of this IEntertainment
  public String format() {
    return name + ", " + price + ".";
  }
}

// Represents a magazine
class Magazine extends AEntertainment {

  /* TEMPLATE
  Everything in the AEntertainment abstract class, plus/overrided 
  FIELDS:
  ... this.genre ...         -- String
  ... this.pages ...         -- int

  METHODS
  ... this.duration() ...                               -- int
  ... this.sameEntertainment(IEntertainment that)...    -- boolean
  ... this.sameMagazine(Magazine thisObj)...            -- boolean
   */

  String genre; //genre of this magazine
  int pages; //number of pages in this magazine

  //constructor 
  Magazine(String name, double price, String genre, int pages, int installments) {
    super(name, price, installments);
    this.genre = genre;
    this.pages = pages;
  }

  //computes the minutes of entertainment of this Magazine, (includes all installments)
  @Override
  public int duration() {
    return 5 * pages * installments;
  }

  //is this Magazine the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameMagazine(this);
  }

  // is this IEntertainment the same as that Magazine?
  @Override
  public boolean sameMagazine(Magazine thisObj) {
    return (this.name.equals(thisObj.name)) && (this.price == thisObj.price)
        && (this.genre.equals(thisObj.genre)) && (this.pages == thisObj.pages)
        && (this.installments == thisObj.installments);
  }
}

// Represents a tv series
class TVSeries extends AEntertainment {

  /* TEMPLATE
  Everything in the AEntertainment abstract class, plus/overrided 
  FIELDS:
  ... this.corporation ...         -- String

  METHODS
  ... this.sameEntertainment(IEntertainment that)...    -- boolean
  ... this.sameTVSeries(TVSeries thisObj)...            -- boolean
   */

  String corporation; //the company who produces the series

  // constructor
  TVSeries(String name, double price, int installments, String corporation) {
    super(name, price, installments);
    this.corporation = corporation;
  }

  //is this TVSeries the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameTVSeries(this);
  }

  // is this IEntertainment the same as that TVSeries?
  @Override
  public boolean sameTVSeries(TVSeries thisObj) {
    return (this.name.equals(thisObj.name)) && (this.price == thisObj.price)
        && (this.installments == thisObj.installments) 
        && (this.corporation == thisObj.corporation);
  }
}

// Represents a podcast
class Podcast extends AEntertainment {

  /* TEMPLATE
  Everything in the AEntertainment abstract class, plus/overrided 

  METHODS
  ... this.sameEntertainment(IEntertainment that)...    -- boolean
  ... this.samePodcast(Podcast thisObj)...              -- boolean
   */

  //constructor
  Podcast(String name, double price, int installments) {
    super(name, price, installments);
  }

  //is this Podcast the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.samePodcast(this);
  }

  //is this IEntertainment the same as that Podcast?
  @Override
  public boolean samePodcast(Podcast thisObj) {
    return (this.name.equals(thisObj.name)) && (this.price == thisObj.price)
        && (this.installments == thisObj.installments);
  }
}

// Examples and tests that represent entertainment
class ExamplesEntertainment {
  IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment vogue = new Magazine("Vogue", 2.5, "Fashion", 50, 12);
  IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  IEntertainment lookingForAlaska = new TVSeries("Looking For Alaska", 5.5, 1, "Hulu");
  IEntertainment serial = new Podcast("Serial", 0.0, 8);
  IEntertainment everythingGoes = new Podcast("Everything Goes", 2.5, 20);


  //testing total price method
  boolean testTotalPrice(Tester t) {
    return t.checkInexact(this.rollingStone.totalPrice(), 2.55 * 12, .0001) 
        && t.checkInexact(this.vogue.totalPrice(), 2.50 * 12, 0.0001)
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25 * 13, .0001)
        && t.checkInexact(this.lookingForAlaska.totalPrice(), 5.5 * 1, 0.0001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
        && t.checkInexact(this.everythingGoes.totalPrice(), 2.50 * 20, 0.0001);
  }

  //testing duration method
  boolean testDuration(Tester t) {
    return t.checkExpect(this.rollingStone.duration(), 3600)
        && t.checkExpect(this.vogue.duration(), 3000)
        && t.checkExpect(this.houseOfCards.duration(), 650)
        && t.checkExpect(this.lookingForAlaska.duration(), 50)
        && t.checkExpect(this.serial.duration(), 400)
        && t.checkExpect(this.everythingGoes.duration(), 1000);
  }

  //testing sameEntertainment method
  boolean testSameEntertainment(Tester t) {
    return t.checkExpect(this.rollingStone.sameEntertainment(this.rollingStone), true)
        && t.checkExpect(this.vogue.sameEntertainment(this.rollingStone), false)
        && t.checkExpect(this.houseOfCards.sameEntertainment(this.houseOfCards), true)
        && t.checkExpect(this.lookingForAlaska.sameEntertainment(this.houseOfCards), false)
        && t.checkExpect(this.serial.sameEntertainment(this.serial), true)
        && t.checkExpect(this.everythingGoes.sameEntertainment(this.rollingStone), false);
  }

  //testing sameMagazine method
  boolean testSameMagazine(Tester t) {
    return t.checkExpect(this.rollingStone.sameMagazine((Magazine) this.rollingStone), true)
        && t.checkExpect(this.vogue.sameMagazine((Magazine) this.rollingStone), false)
        && t.checkExpect(this.houseOfCards.sameMagazine((Magazine) this.vogue), false)
        && t.checkExpect(this.lookingForAlaska.sameEntertainment((Magazine)
            this.rollingStone), false)
        && t.checkExpect(this.serial.sameEntertainment((Magazine) this.vogue), false)
        && t.checkExpect(this.everythingGoes.sameEntertainment((Magazine)
            this.rollingStone), false);
  }

  //testing sameTVSeries method
  boolean testSameTVSeries(Tester t) {
    return t.checkExpect(this.rollingStone.sameTVSeries((TVSeries) this.houseOfCards), false)
        && t.checkExpect(this.vogue.sameTVSeries((TVSeries) this.lookingForAlaska), false)
        && t.checkExpect(this.houseOfCards.sameTVSeries((TVSeries) this.houseOfCards), true)
        && t.checkExpect(this.lookingForAlaska.sameTVSeries((TVSeries) this.houseOfCards), false)
        && t.checkExpect(this.serial.sameTVSeries((TVSeries) this.lookingForAlaska), false)
        && t.checkExpect(this.everythingGoes.sameTVSeries((TVSeries) this.houseOfCards), false);
  }

  //testing samePodcast method
  boolean testSamePodcast(Tester t) {
    return t.checkExpect(this.rollingStone.samePodcast((Podcast) this.serial), false)
        && t.checkExpect(this.vogue.samePodcast((Podcast) this.everythingGoes), false)
        && t.checkExpect(this.houseOfCards.samePodcast((Podcast) this.serial), false)
        && t.checkExpect(this.lookingForAlaska.samePodcast((Podcast) this.everythingGoes), false)
        && t.checkExpect(this.serial.samePodcast((Podcast) this.serial), true)
        && t.checkExpect(this.everythingGoes.samePodcast((Podcast) this.serial), false);
  }

  //testing format method
  boolean testFormat(Tester t) {
    return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.")
        && t.checkExpect(this.vogue.format(), "Vogue, 2.5.")
        && t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25.")
        && t.checkExpect(this.lookingForAlaska.format(), "Looking For Alaska, 5.5.")
        && t.checkExpect(this.serial.format(), "Serial, 0.0.")
        && t.checkExpect(this.everythingGoes.format(), "Everything Goes, 2.5.");
  }

}