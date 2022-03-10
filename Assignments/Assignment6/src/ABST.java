// Assignment 6
// Pooja Gidwani and Ben Bishop

import java.util.Comparator;

import tester.Tester;

// Represents a binary search tree
abstract class ABST<T> {
  Comparator<T> order;

  ABST(Comparator<T> order) {
    this.order = order;
  }

  // Inserts data into the tree
  abstract ABST<T> insert(T item);

  // Checks whether data is present in the tree
  abstract boolean present(T item);

  // Gets the smallest data/leftmost data in the tree
  abstract T getLeftmost();

  //Tracks the smallest data/leftmost data in the tree
  abstract T getLeftmostHelper(T data);

  // Returns the tree minus the leftmost data
  abstract ABST<T> getRight();

  // Returns the this tree without the leftmost item,
  // comparing at each step
  abstract ABST<T> getRightHelper(T smallest);

  // Returns true if this tree has the same order and
  // data as the given tree
  abstract boolean sameTree(ABST<T> given);

  // Returns true if this data is the same as the given
  // leaf
  abstract boolean sameLeaf(Leaf<T> given);

  //Returns true if this data is the same as the given
  // node
  abstract boolean sameNode(Node<T> given);

  // Returns true if this tree has the same data as
  // the given tree (or vice versa)
  abstract boolean sameData(ABST<T> given);

  // Returns true if this tree and the given tree
  // hold the same data
  abstract boolean sameDataHelper(ABST<T> given);

  // Returns this tree as a sorted list
  abstract IList<T> buildList();
}

// Represents a book
class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

// Represents a comparator that sorts books lexicographically by title
class BooksByTitle implements Comparator<Book> {

  // Compares the title of two books and returns 0 if they're the same,
  // a negative value if the first is alphabetically before the second,
  // and a positive value if vice versa
  public int compare(Book o1, Book o2) {
    return  o1.title.compareTo(o2.title);
  }
}

// Represents a comparator that sorts books lexicographically by author
class BooksByAuthor implements Comparator<Book> {

  //Compares the author of two books and returns 0 if they're the same,
  // a negative value if the first is alphabetically before the second, 
  // and a positive value if vice versa
  public int compare(Book o1, Book o2) {
    return o1.author.compareTo(o2.author);
  }
}

// Represents a comparator that sorts books in order by price
class BooksByPrice implements Comparator<Book> {

  //Compares the price of two books and returns 0 if they're the same,
  // a negative value if the first is cheaper than the second, and 
  // a positive value if vice versa
  public int compare(Book o1, Book o2) {
    return o1.price - o2.price;
  }
}

// Represents an empty data
class Leaf<T> extends ABST<T> {

  Leaf(Comparator<T> order) {
    super(order);
  }

  ABST<T> insert(T item) {
    return new Node<T>(order, item, new Leaf<T>(order), new Leaf<T>(order));
  }

  boolean present(T item) {
    return false;
  }

  T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  T getLeftmostHelper(T data) {
    return data;
  }

  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  public ABST<T> getRightHelper(T smallest) {
    return this;
  }

  public boolean sameTree(ABST<T> given) {
    return given.sameLeaf(this);
  }

  public boolean sameLeaf(Leaf<T> given) {
    return true;
  }

  public boolean sameNode(Node<T> given) {
    return false;
  }

  boolean sameData(ABST<T> given) {
    return given.sameDataHelper(this);
  }

  boolean sameDataHelper(ABST<T> given) {
    return true;
  }

  public IList<T> buildList() {
    return new MtList<T>();
  }
}

// represents the data and sub-data for a tree
class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }

  ABST<T> insert(T item) {
    if (order.compare(this.data, item) < 0) {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(item));
    }
    else {
      return new Node<T>(this.order, this.data, this.left.insert(item), this.right);
    }
  }

  boolean present(T item) {
    return order.compare(this.data, item) == 0 || left.present(item) || right.present(item);
  }

  public T getLeftmost() {
    return this.getLeftmostHelper(this.data); 
  } 

  public T getLeftmostHelper(T data) {
    return this.left.getLeftmostHelper(this.data);
  }

  public ABST<T> getRight() {
    return this.getRightHelper(this.getLeftmost());
  }

  public ABST<T> getRightHelper(T smallest) {
    if (order.compare(this.data, smallest) == 0) {
      return this.right;
    }
    else {
      return new Node<T>(this.order, this.data, 
          this.left.getRightHelper(smallest), this.right.getRightHelper(smallest));
    }
  }

  public boolean sameTree(ABST<T> given) {
    return given.sameNode(this);
  }

  public boolean sameLeaf(Leaf<T> given) {
    return false;
  }

  public boolean sameNode(Node<T> given) {
    return order.compare(this.data, given.data) == 0 
        && this.left.sameTree(given.left)
        && this.right.sameTree(given.right);
  }

  boolean sameData(ABST<T> given) {
    return this.sameDataHelper(given) && given.sameDataHelper(this);
  }

  boolean sameDataHelper(ABST<T> given) {
    return given.present(this.data)
        && this.left.sameDataHelper(given)
        && this.right.sameDataHelper(given);
  }

  IList<T> buildList() {
    return new ConsList<T>(this.getLeftmost(), this.getRight().buildList());
  }
}

// generic list
interface IList<T> {}

//empty generic list
class MtList<T> implements IList<T> {}

//non-empty generic list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
}

// Represents the examples and tests for trees, books, and their methods
class ExamplesTree {

  // Examples of books
  Book book1 = new Book("Paper Towns", "John Green", 15);
  Book book2 = new Book("The Perks Of Being A Wallflower", "Steven Chobsky", 16);
  Book book3 = new Book("A Good Girl's Guide to Murder", "Holly Jackson", 17); 
  Book book4 = new Book("Radio Silence", "Alice Oseman", 20);
  Book book5 = new Book("All The Bright Places", "Jennifer Niven", 19); 
  Book book6 = new Book("We Are The Ants", "Shaun David Hutchinson", 12); 

  // Examples of comparators
  Comparator<Book> comparePrice = new BooksByPrice();
  Comparator<Book> compareAuthor = new BooksByAuthor();
  Comparator<Book> compareTitle = new BooksByTitle();

  // Examples of nodes using comparePrice
  ABST<Book> cpLeaf = new Leaf<Book>(this.comparePrice);
  ABST<Book> cp1Node = new Node<Book>(this.comparePrice, this.book1, this.cpLeaf, this.cpLeaf);
  ABST<Book> cp2Node = new Node<Book>(this.comparePrice, this.book2, this.cpLeaf, this.cpLeaf);
  ABST<Book> cp3Node = new Node<Book>(this.comparePrice, this.book6, this.cpLeaf, this.cpLeaf);
  ABST<Book> cp4Node = new Node<Book>(this.comparePrice, this.book1, this.cp3Node, this.cp2Node);
  ABST<Book> cp5Node = new Node<Book>(this.comparePrice, this.book4, this.cpLeaf, this.cpLeaf);
  ABST<Book> cp6Node = new Node<Book>(this.comparePrice, this.book5, this.cpLeaf, this.cp5Node);
  ABST<Book> cp7Node = new Node<Book>(this.comparePrice, this.book3, this.cpLeaf, this.cpLeaf);
  ABST<Book> tree1 = new Node<Book>(this.comparePrice, this.book1, this.cpLeaf, this.cp2Node);
  ABST<Book> tree2 = new Node<Book>(this.comparePrice, this.book2, this.cp1Node, this.cpLeaf);
  ABST<Book> tree3 = new Node<Book>(this.comparePrice, this.book2, this.cp1Node, this.cp7Node);
  ABST<Book> tree4 = new Node<Book>(this.comparePrice, this.book3, this.cp4Node, this.cp6Node);

  // Examples of nodes using compareAuthor
  ABST<Book> aLeaf = new Leaf<Book>(this.compareAuthor);
  ABST<Book> a1Node = new Node<Book>(this.compareAuthor, this.book2, this.aLeaf, this.aLeaf);
  ABST<Book> a2Node = new Node<Book>(this.compareAuthor, this.book3, this.aLeaf, this.aLeaf);
  ABST<Book> tree5 = new Node<Book>(this.compareAuthor, this.book1, this.a1Node, this.a2Node);

  // Examples of nodes using compareTitle
  ABST<Book> tLeaf = new Leaf<Book>(this.compareTitle);
  ABST<Book> t1Node = new Node<Book>(this.compareTitle, this.book5, this.tLeaf, this.tLeaf);
  ABST<Book> t2Node = new Node<Book>(this.compareTitle, this.book6, this.tLeaf, this.tLeaf);
  ABST<Book> tree6 = new Node<Book>(this.compareTitle, this.book1, this.t1Node, this.t2Node);

  // Examples of lists
  IList<Book> emptyList = new MtList<Book>();
  IList<Book> tree1List = new ConsList<Book>(this.book1,
      new ConsList<Book>(this.book2,
          this.emptyList));
  IList<Book> tree4List = new ConsList<Book>(this.book6,
      new ConsList<Book>(this.book1,
          new ConsList<Book>(this.book2,
              new ConsList<Book>(this.book3,
                  new ConsList<Book>(this.book5,
                      new ConsList<Book>(this.book4,
                          this.emptyList))))));

  // Tests the compare methods
  boolean testCompare(Tester t) {
    return t.checkExpect(this.comparePrice.compare(this.book1, this.book1), 0)
        && t.checkExpect(this.comparePrice.compare(this.book1, this.book2), -1)
        && t.checkExpect(this.comparePrice.compare(this.book2, this.book1), 1)
        && t.checkExpect(this.compareAuthor.compare(this.book6, this.book6), 0)
        && t.checkExpect(this.compareAuthor.compare(this.book2, this.book1), 9)
        && t.checkExpect(this.compareAuthor.compare(this.book3, this.book6), -11)
        && t.checkExpect(this.compareTitle.compare(this.book2, this.book2), 0)
        && t.checkExpect(this.compareTitle.compare(this.book2, this.book1), 4)
        && t.checkExpect(this.compareTitle.compare(this.book5, this.book4), -17);  
  }

  // Tests the insert method
  boolean testInsert(Tester t) {
    return t.checkExpect(this.cp1Node.insert(this.book2), this.tree1)
        && t.checkExpect(this.tree2.insert(this.book3), this.tree3)
        && t.checkExpect(this.cpLeaf.insert(this.book1), this.cp1Node);
  }

  // Tests the present method
  boolean testPresent(Tester t) {
    return t.checkExpect(this.tree1.present(this.book1), true)
        && t.checkExpect(this.cp1Node.present(this.book2), false)
        && t.checkExpect(this.tree5.present(this.book2), true)
        && t.checkExpect(this.aLeaf.present(this.book3), false);
  }

  // Tests the getLeftmost method and helper
  boolean testGetLeftmost(Tester t) {
    return t.checkExpect(this.tree2.getLeftmost(), this.book1)
        && t.checkExpect(this.tree4.getLeftmost(), this.book6)
        && t.checkExpect(this.tree2.getLeftmostHelper(this.book2), this.book1) 
        && t.checkExpect(this.tree1.getLeftmostHelper(this.book1), this.book1) 
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            aLeaf, "getLeftmost");
  }

  // Tests the getRight method and helper
  boolean testGetRight(Tester t) {
    return t.checkExpect(this.tree1.getRight(), this.cp2Node)
        && t.checkExpect(this.tree2.getRight(), this.cp2Node)
        && t.checkExpect(this.tree1.getRightHelper(this.book1), this.cp2Node)
        && t.checkExpect(this.tree2.getRightHelper(this.book1), this.cp2Node)
        && t.checkException(new RuntimeException("No right of an empty tree"), aLeaf, "getRight");
  }

  // Tests the sameTree method
  boolean testSameTree(Tester t) {
    return t.checkExpect(this.tree1.sameTree(this.tree1), true)
        && t.checkExpect(this.tree2.sameTree(this.tree1), false)
        && t.checkExpect(this.cpLeaf.sameTree(this.cpLeaf), true);
  }

  // Tests sameLeaf and sameNode methods
  boolean testSameLeafAndNode(Tester t) {
    return t.checkExpect(this.tree1.sameLeaf((Leaf<Book>)this.aLeaf), false)
        && t.checkExpect(this.tLeaf.sameLeaf((Leaf<Book>)this.tLeaf), true)
        && t.checkExpect(this.aLeaf.sameNode((Node<Book>) this.tree2), false)
        && t.checkExpect(this.tree5.sameNode((Node<Book>) this.tree5), true);
  }

  // Tests the sameData method and helper
  boolean testSameData(Tester t) {
    return t.checkExpect(this.tree1.sameData(this.tree1), true)
        && t.checkExpect(this.tree2.sameData(this.tree1), true)
        && t.checkExpect(this.tree4.sameData(this.tree1), false)
        && t.checkExpect(this.cpLeaf.sameData(this.tree1), false)
        && t.checkExpect(this.aLeaf.sameData(this.tree3), false)
        && t.checkExpect(this.aLeaf.sameDataHelper(this.aLeaf), true)
        && t.checkExpect(this.tree1.sameDataHelper(this.tree1), true);
  } 

  // Tests the buildList method
  boolean testBuildList(Tester t) {
    return t.checkExpect(this.tree1.buildList(), this.tree1List)
        && t.checkExpect(this.tree4.buildList(), this.tree4List)
        && t.checkExpect(this.tLeaf.buildList(), this.emptyList);
  }
}


