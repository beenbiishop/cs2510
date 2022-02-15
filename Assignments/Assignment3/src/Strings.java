// CS 2510, Assignment 3

// Pooja Gidwani, Ben Bishop

import tester.*;

// represents a list of strings
interface ILoString {

  // combine all Strings in this list into one
  String combine();

  // sort all strings in this list alphabetically
  ILoString sort();

  // insert a string into this list
  ILoString insert(String s);

  // returns true if this list is sorted alphabetically,
  // else false
  boolean isSorted();

  // returns true if the first and next elements of a list
  // are in alphabetical order, else false
  boolean checkSort(String s);

  // return the elements of this list alternating
  // with the elements of a given list
  ILoString interleave(ILoString strings);

  // return the elements of this list merged with
  // the elements of a given list, including duplicates
  ILoString merge(ILoString strings);

  // inserts the items of one sorted list into another
  // resulting in a combined sorted list
  ILoString mergeHelp(ILoString merged);

  // returns the elements of this list reversed
  ILoString reverse();

  // returns a reversed list by placing the elements
  // of this list at the beginning each time
  ILoString reverseHelp(ILoString strings);

  // returns true if this list contains pairs of identical
  // strings, else false
  boolean isDoubledList();

  // returns true if a pair of elements are equal throughout
  // the list, else false
  boolean pairHelp(String s);

  // returns true if the elements of this list can be read
  // the same forwards or backwards, else false
  boolean isPalindromeList();

}

// to represent an empty list of Strings
class MtLoString implements ILoString {

  /*
  TEMPLATE:
  ---------

  Methods:
  ... this.combine() ...              -- String
  ... this.sort() ...                 -- ILoString
  ... this.insert()...                -- ILoString
  ... this.isSorted()...              -- boolean
  ... this.checkSort()...             -- boolean
  ... this.interleave()...            -- ILoString
  ... this.merge()...                 -- ILoString
  ... this.reverse()...               -- ILoString
  ... this.reverseHelp()...           -- ILoString
  ... this.isDoubledList()...         -- boolean
  ... this.pairHelp()...              -- boolean
  ... this.isPalindromeList()...      -- boolean
   */

  // the constructor
  MtLoString(){}

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // insert a string into this list
  public ILoString insert(String s) {
    return new ConsLoString(s, this);
  }

  // sort all strings in this list alphabetically
  public ILoString sort() {
    return this;
  }

  // returns true if this list is sorted alphabetically,
  // else false
  public boolean isSorted() {
    return true;
  }

  // returns true if the first and next elements of a list
  // are in alphabetical order, else false
  public boolean checkSort(String s) {
    return true;
  }

  // return the elements of this list alternating
  // with the elements of a given list
  public ILoString interleave(ILoString strings) {
    return strings;
  }

  // return the elements of this list merged with
  // the elements of a given list, including duplicates
  public ILoString merge(ILoString strings) {
    return strings;
  }

  // inserts the items of one sorted list into another
  // resulting in a combined sorted list
  public ILoString mergeHelp(ILoString merged) {
    return merged;
  }

  // returns the elements of this list reversed
  public ILoString reverse() {
    return this;
  }

  // returns a reversed list by placing the elements
  // of this list at the beginning each time
  public ILoString reverseHelp(ILoString strings) {
    return strings;
  }

  // returns true if this list contains pairs of identical
  // strings, else false
  public boolean isDoubledList() {
    return true;
  }

  // returns true if a pair of elements are equal throughout
  // the list, else false
  public boolean pairHelp(String s) {
    return false;
  }

  // returns true if the elements of this list can be read
  // the same forwards or backwards, else false
  public boolean isPalindromeList() {
    return true;
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {

  /*
  TEMPLATE
  FIELDS:
  ... this.first ...         -- String
  ... this.rest ...          -- ILoString

  METHODS
  ... this.combine() ...              -- String
  ... this.sort() ...                 -- ILoString
  ... this.insert()...                -- ILoString
  ... this.isSorted()...              -- boolean
  ... this.checkSort()...             -- boolean
  ... this.interleave()...            -- ILoString
  ... this.merge()...                 -- ILoString
  ... this.reverse()...               -- ILoString
  ... this.reverseHelp()...           -- ILoString
  ... this.isDoubledList()...         -- boolean
  ... this.pairHelp()...              -- boolean
  ... this.isPalindromeList()...      -- boolean

  METHODS FOR FIELDS
  ... this.first.concat(String) ...        -- String
  ... this.first.compareTo(String) ...     -- int
  ... this.rest.combine() ...              -- String
  ... this.rest.sort() ...                 -- ILoString
  ... this.rest.insert()...                -- ILoString
  ... this.rest.isSorted()...              -- boolean
  ... this.rest.checkSort()...             -- boolean
  ... this.rest.interleave()...            -- ILoString
  ... this.rest.merge()...                 -- ILoString
  ... this.rest.reverse()...               -- ILoString
  ... this.rest.reverseHelp()...           -- ILoString
  ... this.rest.isDoubledList()...         -- boolean
  ... this.rest.pairHelp()...              -- boolean
  ... this.rest.isPalindromeList()...      -- boolean
   */

  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // sort all strings in this list alphabetically
  public ILoString sort() {
    return this.rest.sort().insert(this.first.toLowerCase());
  }

  // insert a string into this list
  public ILoString insert(String s) {
    if (s.compareTo(this.first.toLowerCase()) < 0) {
      return new ConsLoString(s, this);
    } else {
      return new ConsLoString(this.first, this.rest.insert(s));
    }
  }

  // returns true if this list is sorted alphabetically,
  // else false
  public boolean isSorted() {
    return (this.rest.isSorted())
        && (this.rest.checkSort(this.first.toLowerCase()));
  }

  // returns true if the first and next elements of a list
  // are in alphabetical order, else false
  public boolean checkSort(String s) {
    return (s.compareTo(this.first.toLowerCase()) <= 0)
        && (this.rest.checkSort(s));
  }

  // return the elements of this list alternating
  // with the elements of a given list
  public ILoString interleave(ILoString strings) {
    return new ConsLoString(this.first, strings.interleave(this.rest));
  }

  // return the elements of this list merged with
  // the elements of a given list, including duplicates
  public ILoString merge(ILoString strings) {
    //return new ConsLoString(this.first, strings.merge(this.rest));
    return this.mergeHelp(strings);
  }

  // inserts the items of one sorted list into another
  // resulting in a combined sorted list
  public ILoString mergeHelp(ILoString merged) {
    return this.rest.mergeHelp(merged.insert(this.first));
  }

  // returns the elements of this list reversed
  public ILoString reverse() {
    return this.reverseHelp(new MtLoString());
  }

  // returns a reversed list by placing the elements
  // of this list at the beginning each time
  public ILoString reverseHelp(ILoString reversed) {
    return this.rest.reverseHelp(new ConsLoString(this.first, reversed));
  }

  // returns true if this list contains pairs of identical
  // strings, else false
  public boolean isDoubledList() {
    return this.rest.pairHelp(this.first);
  }

  // returns true if a pair of elements are equal throughout
  // the list, else false
  public boolean pairHelp(String s) {
    return this.rest.isDoubledList()
        && this.first.equals(s);
  }

  // returns true if the elements of this list can be read
  // the same forwards or backwards, else false
  public boolean isPalindromeList() {
    return this.combine().equals(this.reverse().combine());
  }
}

// examples and tests for the classes that represent lists of strings
class ExamplesStrings {
  ExamplesStrings() {}

  // examples of lists of strings, with their sorted counterparts
  ILoString mary = new ConsLoString("Mary ",
      new ConsLoString("had ",
          new ConsLoString("a ",
              new ConsLoString("little ",
                  new ConsLoString("lamb.", new MtLoString())))));

  ILoString exSorted = new ConsLoString("a ",
      new ConsLoString("had ",
          new ConsLoString("lamb.",
              new ConsLoString("little ",
                  new ConsLoString("mary ", new MtLoString())))));

  ILoString colors = new ConsLoString("Purple ",
      new ConsLoString("GOLD ",
          new ConsLoString("white!", new MtLoString())));

  ILoString colorsSorted = new ConsLoString("gold ",
      new ConsLoString("purple ",
          new ConsLoString("white!", new MtLoString())));

  ILoString pets = new ConsLoString("Dog",
      new ConsLoString("cat",
          new ConsLoString("Dog", new MtLoString())));

  ILoString petsSorted = new ConsLoString("cat",
      new ConsLoString("dog",
          new ConsLoString("dog", new MtLoString())));

  ILoString pairedList = new ConsLoString("bye",
      new ConsLoString("bye",
          new ConsLoString("hi",
              new ConsLoString("hi", new MtLoString()))));
  ILoString alphabet = new ConsLoString("a",
      new ConsLoString("b",
          new ConsLoString("c",
              new ConsLoString("d",
                  new ConsLoString("e",
                      new ConsLoString("d",
                          new ConsLoString("c",
                              new ConsLoString("b",
                                  new ConsLoString("a",
                                      new MtLoString())))))))));
  ILoString alphabetSorted = new ConsLoString("a",
      new ConsLoString("a",
          new ConsLoString("b",
              new ConsLoString("b",
                  new ConsLoString("c",
                      new ConsLoString("c",
                          new ConsLoString("d",
                              new ConsLoString("d",
                                  new ConsLoString("e",
                                      new MtLoString())))))))));
  ILoString emptyMt = new MtLoString();
  ILoString emptyCons = new ConsLoString("", new MtLoString());

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return
        t.checkExpect(this.mary.combine(), "Mary had a little lamb.")
        && t.checkExpect(this.emptyCons.combine(), "")
        && t.checkExpect(this.emptyMt.combine(), "")
        && t.checkExpect(this.colors.combine(), "Purple GOLD white!");
  }

  // test the method sort for the lists of Strings
  boolean testSort(Tester t) {
    return
        t.checkExpect(this.mary.sort(), this.exSorted)
        && t.checkExpect(this.emptyCons.sort(), this.emptyCons)
        && t.checkExpect(this.emptyMt.sort(), this.emptyMt)
        && t.checkExpect(this.alphabet.sort(), this.alphabetSorted)
        && t.checkExpect(this.colors.sort(), this.colorsSorted);
  }

  //test the method insert for the lists of Strings
  boolean testInsert(Tester t) {
    return t.checkExpect(this.mary.insert("Ben"), new ConsLoString("Ben",
        new ConsLoString("Mary ",
            new ConsLoString("had ",
                new ConsLoString("a ",
                    new ConsLoString("little ",
                        new ConsLoString("lamb.",
                            new MtLoString())))))))
        && t.checkExpect(this.alphabet.insert("Pooja"), new ConsLoString("Pooja",
            new ConsLoString("a",
                new ConsLoString("b",
                    new ConsLoString("c",
                        new ConsLoString("d",
                            new ConsLoString("e",
                                new ConsLoString("d",
                                    new ConsLoString("c",
                                        new ConsLoString("b",
                                            new ConsLoString("a",
                                                new MtLoString())))))))))))
        && t.checkExpect(this.emptyCons.insert(""), new ConsLoString("",
            new ConsLoString("",
                new MtLoString())))
        && t.checkExpect(this.emptyMt.insert(""), new ConsLoString("",
            new MtLoString()));
  }

  // test the method isSorted for the lists of Strings, and its' helper
  // method checkSort
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.mary.isSorted(), false)
        && t.checkExpect(this.emptyCons.isSorted(), true)
        && t.checkExpect(this.emptyMt.isSorted(), true)
        && t.checkExpect(this.exSorted.isSorted(), true)
        && t.checkExpect(this.alphabet.checkSort("Ben"), true)
        && t.checkExpect(this.mary.checkSort("Pooja"), true)
        && t.checkExpect(this.emptyMt.checkSort(""), true)
        && t.checkExpect(this.emptyCons.checkSort(""), true);
  }

  // test the method interleave for the lists of Strings
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.mary.interleave(this.exSorted),
        new ConsLoString("Mary ",new ConsLoString("a ",
            new ConsLoString("had ",new ConsLoString("had ",
                new ConsLoString("a ", new ConsLoString("lamb.",
                    new ConsLoString("little ",new ConsLoString("little ",
                        new ConsLoString("lamb.", new ConsLoString("mary ",
                            new MtLoString())))))))))))
        && t.checkExpect(this.exSorted.interleave(this.colorsSorted),
            new ConsLoString("a ",new ConsLoString("gold ",
                new ConsLoString("had ",new ConsLoString("purple ",
                    new ConsLoString("lamb.", new ConsLoString("white!",
                        new ConsLoString("little ", new ConsLoString("mary ",
                            new MtLoString())))))))))
        && t.checkExpect(this.emptyCons.interleave(this.emptyCons), new ConsLoString("",
            new ConsLoString("",
                new MtLoString())))
        && t.checkExpect(this.emptyMt.interleave(this.emptyMt), new MtLoString());
  }

  // test the method merge for the lists of Strings, and its' helper
  // method mergeHelp
  boolean testMerge(Tester t) {
    return t.checkExpect(this.exSorted.merge(this.colorsSorted),
        new ConsLoString("a ",new ConsLoString("gold ",
            new ConsLoString("had ",new ConsLoString("lamb.",
                new ConsLoString("little ", new ConsLoString("mary ",
                    new ConsLoString("purple ",new ConsLoString("white!",
                        new MtLoString())))))))))
        && t.checkExpect(this.petsSorted.merge(this.pairedList),
            new ConsLoString("bye",new ConsLoString("bye",
                new ConsLoString("cat",new ConsLoString("dog",
                    new ConsLoString("dog", new ConsLoString("hi",
                        new ConsLoString("hi",
                            new MtLoString()))))))))
        && t.checkExpect(this.emptyCons.merge(this.emptyCons), new ConsLoString("",
            new ConsLoString("",
                new MtLoString())))
        && t.checkExpect(this.emptyMt.merge(this.emptyMt), new MtLoString())
        && t.checkExpect(this.mary.mergeHelp(this.mary), new ConsLoString("a ",
            new ConsLoString("had ",
                new ConsLoString("lamb.",
                    new ConsLoString("little ",
                        new ConsLoString("Mary ",
                            new ConsLoString("Mary ",
                                new ConsLoString("had ",
                                    new ConsLoString("a ",
                                        new ConsLoString("little ",
                                            new ConsLoString("lamb.",
                                                new MtLoString())))))))))))
        && t.checkExpect(this.alphabet.mergeHelp(this.colors), new ConsLoString("a",
            new ConsLoString("a",
                new ConsLoString("b",
                    new ConsLoString("b",
                        new ConsLoString("c",
                            new ConsLoString("c",
                                new ConsLoString("d",
                                    new ConsLoString("d",
                                        new ConsLoString("e",
                                            new ConsLoString("Purple ",
                                                new ConsLoString("GOLD ",
                                                    new ConsLoString("white!",
                                                        new MtLoString())))))))))))))
        && t.checkExpect(this.pets.mergeHelp(this.colors), new ConsLoString("Dog",
            new ConsLoString("cat",
                new ConsLoString("Dog",
                    new ConsLoString("Purple ",
                        new ConsLoString("GOLD ",
                            new ConsLoString("white!",
                                new MtLoString())))))))
        && t.checkExpect(this.emptyCons.mergeHelp(this.emptyCons), new ConsLoString("",
            new ConsLoString("",
                new MtLoString())))
        && t.checkExpect(this.emptyMt.mergeHelp(this.emptyMt), new MtLoString());
  }

  // test the method reverse for the lists of Strings, and its' helper
  // method reverseHelp
  boolean testReverse(Tester t) {
    return t.checkExpect(this.mary.reverse(),
        new ConsLoString("lamb.", new ConsLoString("little ",
            new ConsLoString("a ", new ConsLoString("had ",
                new ConsLoString("Mary ", new MtLoString()))))))
        && t.checkExpect(this.colors.reverse(),
            new ConsLoString("white!",
                new ConsLoString("GOLD ",
                    new ConsLoString("Purple ",
                        new MtLoString()))))
        && t.checkExpect(this.emptyCons.reverse(),
            new ConsLoString("",
                new MtLoString()))
        && t.checkExpect(this.emptyMt.reverse(), new MtLoString())
        && t.checkExpect(this.pets.reverseHelp(this.pets), new ConsLoString("Dog",
            new ConsLoString("cat",
                new ConsLoString("Dog",
                    new ConsLoString("Dog",
                        new ConsLoString("cat",
                            new ConsLoString("Dog",
                                new MtLoString())))))))
        && t.checkExpect(this.colors.reverseHelp(this.colors), new ConsLoString("white!",
            new ConsLoString("GOLD ",
                new ConsLoString("Purple ",
                    new ConsLoString("Purple ",
                        new ConsLoString("GOLD ",
                            new ConsLoString("white!",
                                new MtLoString())))))))
        && t.checkExpect(this.emptyCons.reverseHelp(this.emptyCons), new ConsLoString("",
            new ConsLoString("",
                new MtLoString())))
        && t.checkExpect(this.emptyMt.reverseHelp(this.emptyMt), new MtLoString());
  }

  // test the method isDoubledList for the lists of Strings, and its' helper
  // method pairHelp
  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(this.colors.isDoubledList(), false)
        && t.checkExpect(this.pairedList.isDoubledList(), true)
        && t.checkExpect(this.emptyCons.isDoubledList(), false)
        && t.checkExpect(this.emptyMt.isDoubledList(), true)
        && t.checkExpect(this.colors.pairHelp("Ben"), false)
        && t.checkExpect(this.pairedList.pairHelp("Pooja"), false)
        && t.checkExpect(this.emptyCons.pairHelp(""), true)
        && t.checkExpect(this.emptyMt.pairHelp(""), false);
  }

  // test the method isPalindromeList for the lists of Strings
  boolean testIsPalindromeList(Tester t) {
    return t.checkExpect(this.mary.isPalindromeList(), false)
        && t.checkExpect(this.pets.isPalindromeList(), true)
        && t.checkExpect(this.emptyCons.isPalindromeList(), true)
        && t.checkExpect(this.emptyMt.isPalindromeList(), true);
  }
}
