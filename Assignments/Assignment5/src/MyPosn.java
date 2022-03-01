import javalib.worldimages.Posn;
import tester.Tester;

// Represents a position
class MyPosn extends Posn {

  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }

  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }

  // Adds the given x and y value to this posn
  MyPosn add(int xChange, int yChange) {
    return new MyPosn((this.x + xChange), (this.y + yChange));
  }

  // Checks whether the position is off screen
  boolean isOffScreen(int width, int height) {
    return (((this.x > width) || (this.y > height)) || ((this.x < 0) || (this.y < 0)));
  }
}

// Examples and tests for MyPosns
class ExamplesMyPosn {
  ExamplesMyPosn(){}

  MyPosn pos1 = new MyPosn(5, 5);
  MyPosn pos2 = new MyPosn(new Posn(3, 3));

  boolean testAdd(Tester t) {
    return t.checkExpect(pos1.add(3, 3), new MyPosn(8, 8))
        && t.checkExpect(pos1.add(1, 1), new MyPosn(6, 6));
  }

  boolean testisOffScreen(Tester t) {
    return t.checkExpect(pos1.isOffScreen(2, 2), true)
        && t.checkExpect(pos1.isOffScreen(30, 30), false);
  }
}




