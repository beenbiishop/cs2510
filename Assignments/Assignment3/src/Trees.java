// CS 2510, Assignment 3

// Pooja Gidwani, Ben Bishop

import java.awt.Color;

import javalib.funworld.WorldScene;
import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.CircleImage;
import javalib.worldimages.LineImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RotateImage;
import javalib.worldimages.WorldImage;
import tester.Tester;

//represents a tree
interface ITree {

  // returns a given tree as an image
  WorldImage draw();

  // returns true if a given tree's branch is drooping, else false
  boolean isDrooping();

  // returns a combined image given two trees
  ITree combine(int leftLength, int rightLength, double leftTheta,
      double rightTheta, ITree otherTree);

  // returns the width of a given tree
  double getWidth();

  // returns the width of the left side of a given tree
  double getLeftWidth();

  // returns the width of the right side of a given tree
  double getRightWidth();
}

//represent's a tree's leaf
class Leaf implements ITree {

  /*
TEMPLATE:
---------
Fields:
... this.size ...                   -- int
... this.color ...                  -- color

Methods:
... this.isDrooping() ...           -- boolean
... this.draw() ...                 -- WorldImage
... this.combine(int leftLength,
    int rightLength,
    double leftTheta,
    double rightTheta,
    ITree otherTree) ...            -- ITree
... this.getWidth() ...             -- double
... this.getLeftWidth() ...         -- double
... this.getRightWidth() ...        -- double
   */

  int size; // represents the radius of the leaf
  Color color; // the color to draw it

  // the constructor
  Leaf(int size, Color color) {
    this.size = size;
    this.color = color;
  }

  // returns a given tree as an image
  public WorldImage draw() {
    return new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }

  //returns true if a given tree's branch is drooping, else false
  public boolean isDrooping() {
    return false;
  }

  // returns a combined image given two trees
  public ITree combine(int leftLength, int rightLength, double leftTheta,
      double rightTheta, ITree otherTree) {
    /* TEMPLATE: everything in the template for Leaf, plus
    Methods on parameters:
    ... otherTree.isDrooping() ...           -- boolean
    ... otherTree.draw() ...                 -- WorldImage
    ... otherTree.combine(int leftLength,
        int rightLength,
        double leftTheta,
        double rightTheta,
        ITree otherTree) ...                 -- ITree
    ... otherTree.getWidth() ...             -- double
    ... otherTree.getLeftWidth() ...         -- double
    ... otherTree.getRightWidth() ...        -- double
     */
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this,
        new Stem(0, rightTheta + 30, otherTree));
  }

  // returns the width of a given tree
  public double getWidth() {
    return this.getRightWidth() - this.getLeftWidth();
  }

  // returns the width of the left side of a given tree
  public double getLeftWidth() {
    return -this.size;
  }

  // returns the width of the right side of a given tree
  public double getRightWidth() {
    return this.size;
  }
}


//represent's a tree's stem
class Stem implements ITree {

  /*
TEMPLATE:
---------
Fields:
... this.size ...                   -- int
... this.color ...                  -- color

Methods:
... this.draw() ...                 -- WorldImage
... this.isDrooping() ...           -- boolean
... this.combine(int leftLength,
    int rightLength,
    double leftTheta,
    double rightTheta,
    ITree otherTree) ...            -- ITree
... this.getWidth() ...             -- double
... this.getLeftWidth() ...         -- double
... this.getRightWidth() ...        -- double
   */

  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;

  // the constructor
  Stem(int length, double theta, ITree tree) {
    this.length = length;
    this.theta = theta;
    this.tree = tree;
  }

  // returns a given tree as an image
  public WorldImage draw() {

    if (this.theta > 0 && this.theta < 90 ) {
      this.theta = this.theta;
    }
    else if (this.theta > 90 && this.theta < 180) {
      this.theta = this.theta - 180;
    }
    else if (this.theta >= 270 && this.theta < 360) {
      this.theta = this.theta - 180;
    }
    WorldImage stem = new LineImage(new Posn(0, this.length),
        Color.BLUE).movePinhole(0, - this.length / 2);
    WorldImage overlay = new OverlayImage(this.tree.draw(), stem);
    WorldImage rotated = new RotateImage(overlay, this.theta);
    return rotated;
  }

  // returns true if a given tree's branch is drooping, else false
  public boolean isDrooping() {
    return (((this.theta % 360 > 180) && (this.theta % 360 < 360)) || this.tree.isDrooping());
  }

  // returns a combined image given two trees
  public ITree combine(int leftLength, int rightLength, double leftTheta,
      double rightTheta, ITree otherTree) {
    /* TEMPLATE: everything in the template for Stem, plus
    Methods on parameters:
    ... otherTree.isDrooping() ...           -- boolean
    ... otherTree.draw() ...                 -- WorldImage
    ... otherTree.combine(int leftLength,
        int rightLength,
        double leftTheta,
        double rightTheta,
        ITree otherTree) ...                 -- ITree
    ... otherTree.getWidth() ...             -- double
    ... otherTree.getLeftWidth() ...         -- double
    ... otherTree.getRightWidth() ...        -- double
     */
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this,
        new Stem(0, rightTheta + 30, otherTree));
  }

  // returns the width of a given tree
  public double getWidth() {
    return this.getRightWidth() - this.getLeftWidth();
  }

  //returns the width of the left side of a given tree
  public double getLeftWidth() {
    if ( (this.theta % 360 >= 0 && this.theta % 360 < 90)
        || (this.theta % 360 >= 270 && this.theta % 360 <= 360) ) {
      return 0;
    }
    double leftLength = (this.length * Math.cos(Math.toRadians(this.theta)));
    if (leftLength < 0) {
      return leftLength + this.tree.getLeftWidth();
    }
    else {
      return -leftLength + this.tree.getLeftWidth();
    }
  }

  //returns the width of the right side of a given tree
  public double getRightWidth() {
    if ( (this.theta % 360 >= 90 && this.theta % 360 < 270) ) {
      return 0;
    }
    double rightLength = (this.length * Math.cos(Math.toRadians(this.theta)));
    if (rightLength < 0) {
      return -rightLength + this.tree.getRightWidth();
    }
    else {
      return rightLength + this.tree.getRightWidth();
    }
  }
}

//represent's a tree's branch
class Branch implements ITree {

  /*
TEMPLATE:
---------
Fields:
... this.leftLength ...                   -- int
... this.rightLength ...                  -- int
... this.leftTheta ...                    -- double
... this.rightTheta ...                   -- double
... this.left ...                         -- ITree
... this.right ...                        -- ITree

Methods:
... this.draw() ...                       -- WorldImage
... this.isDrooping() ...                 -- boolean
... this.combine(int leftLength,
 int rightLength,
  double leftTheta,
      double rightTheta,
       ITree otherTree) ...               -- ITree
... this.getWidth() ...                   -- double
... this.getLeftWidth() ...               -- double
... this.getRightWidth() ...              -- double

Methods for Fields:
... this.left.draw() ...                  -- boolean
... this.left.isDrooping()...             -- boolean
... this.left.combine(int leftLength,
    int rightLength,
    double leftTheta,
    double rightTheta,
    ITree otherTree) ...                  -- ITree
... this.left.getWidth() ...              -- double
... this.left.getLeftWidth() ...          -- double
... this.left.getRightWidth() ...         -- double
... this.right.draw() ...                 -- boolean
... this.right.isDrooping()...            -- boolean
... this.right.combine(int leftLength,
    int rightLength,
    double leftTheta,
    double rightTheta,
    ITree otherTree) ...                  -- ITree
... this.right.getWidth() ...             -- double
... this.right.getLeftWidth() ...         -- double
... this.right.getRightWidth() ...        -- double

   */

  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the tree
  ITree left;
  ITree right;

  // the constructor
  Branch(int leftLength, int rightLength, double leftTheta,
      double rightTheta, ITree left, ITree right) {
    this.leftLength = leftLength;
    this.rightLength = rightLength;
    this.leftTheta = leftTheta;
    this.rightTheta = rightTheta;
    this.left = left;
    this.right = right;
  }

  // returns a given tree as an image
  public WorldImage draw() {

    int xLeft = (int) (Math.round(this.leftLength * Math.cos(Math.toRadians(this.leftTheta))));
    int yLeft = (int) (Math.round(this.leftLength
        * Math.sin(Math.toRadians(360 - this.leftTheta))));
    WorldImage leftStem = new LineImage(new Posn(xLeft, yLeft),
        Color.BLACK).movePinhole(xLeft / 2, yLeft / 2);
    WorldImage leftBranch = new OverlayImage(left.draw(), leftStem).movePinhole(-xLeft, -yLeft);


    int xRight = (int) (Math.round(this.rightLength * Math.cos(Math.toRadians(this.rightTheta))));
    int yRight = (int) (Math.round(this.rightLength
        * Math.sin(Math.toRadians(360 - this.rightTheta))));
    WorldImage rightStem = new LineImage(new Posn(xRight, yRight),
        Color.BLACK).movePinhole(xRight / 2, yRight / 2);
    WorldImage rightBranch =
        new OverlayImage(right.draw(), rightStem).movePinhole(-xRight, -yRight);

    return new OverlayImage(leftBranch, rightBranch);
  }

  // returns true if a given tree's branch is drooping, else false
  public boolean isDrooping() {
    return ((this.leftTheta % 360 > 180) && (this.leftTheta % 360 < 360))
        || ((this.rightTheta % 360 > 180) && (this.rightTheta % 360 < 360))
        || (this.left.isDrooping()) || (this.right.isDrooping());
  }

  // returns a combined image given two trees
  public ITree combine(int leftLength, int rightLength, double leftTheta,
      double rightTheta, ITree otherTree) {
    /* TEMPLATE: everything in the template for Branch, plus
    Methods on parameters:
    ... otherTree.isDrooping() ...           -- boolean
    ... otherTree.draw() ...                 -- WorldImage
    ... otherTree.combine(int leftLength,
        int rightLength,
        double leftTheta,
        double rightTheta,
        ITree otherTree) ...                 -- ITree
    ... otherTree.getWidth() ...             -- double
    ... otherTree.getLeftWidth() ...         -- double
    ... otherTree.getRightWidth() ...        -- double
     */
    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        new Stem(0, leftTheta - 30, this), new Stem(0, rightTheta + 30, otherTree));
  }

  // returns the width of a given tree
  public double getWidth() {
    return this.getRightWidth() - this.getLeftWidth();
  }

  // returns the width of the left side of a given tree
  public double getLeftWidth() {
    double leftLength = (this.leftLength * Math.cos(Math.toRadians(this.leftTheta)));
    if (leftLength > 0) {
      leftLength = -leftLength;
    }

    if (this.left.getLeftWidth() < this.right.getLeftWidth()) {
      return leftLength + this.right.getLeftWidth();
    }
    else {
      return leftLength + this.left.getLeftWidth();
    }
  }

  // returns the width of the right side of a given tree
  public double getRightWidth() {
    double rightLength = (this.rightLength * Math.cos(Math.toRadians(this.rightTheta)));
    if (rightLength < 0) {
      rightLength = -rightLength;
    }

    if (this.left.getRightWidth() < this.right.getRightWidth()) {
      return rightLength + this.right.getRightWidth();
    }
    else {
      return rightLength + this.left.getRightWidth();
    }

  }
}

// examples and tests for the classes that represent trees, branches, stems, and leaves
class ExamplesTree {

  // examples of trees and their branches, stems, and leaves
  ITree leaf = new Leaf(20, Color.GREEN);
  ITree stem = new Stem(100, 285, leaf);
  ITree stemAndLeafAndBranch = new Branch(50, 50, 90, 250, leaf, leaf);
  ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
  ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));
  ITree tree3 = new Stem(40, 45, tree1);
  ITree tree4 = new Branch(40, 50, 150, 30, tree1, tree2);
  ITree combined = tree1.combine(40, 50, 150, 30, tree2);
  ITree combined2 = tree4.combine(40, 50, 150, 30, tree3);

  // test the method draw for the trees
  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(tree4.draw(), 250, 250)) && c.show();
  }

  // test the method isDrooping for the trees
  boolean testIsDrooping(Tester t) {
    return t.checkExpect(stem.isDrooping(), true)
        && t.checkExpect(tree4.isDrooping(), false);
  }

  // test the method combine for the trees
  boolean testCombine(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(combined.draw(), 250, 250)) && c.show();
  }

  // test the method getWidth for the trees, and its' helper methods getLeftWidth
  // and getRightWidth
  boolean testGetWidth(Tester t) {
    return t.checkInexact(leaf.getWidth(), 40.0, 0.01);

  }
}
