// CS 2510, Lab 5

// Pooja Gidwani, Ben Bishop

import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
// and predefined colors (Color.RED, Color.GRAY, etc.)

// represents an extended posn
class MyPosn extends Posn {

  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }

  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }

  MyPosn add(int xChange, int yChange) {
    return new MyPosn ((this.x + xChange), (this.y + yChange));
  }

  boolean isOffScreen(int width, int height) {
    return (((this.x > width) || (this.y > height)) || ((this.x < 0) || (this.y < 0)));
  }
}

// represents a circle
class Circle {
  MyPosn position; // in pixels
  MyPosn velocity; // in pixels/tick
  int radius;
  Color color;

  Circle(MyPosn position, MyPosn velocity, int radius, Color color) {
    this.position = position;
    this.velocity = velocity;
    this.radius = radius;
    this.color = color;
  }

  Circle move() {
    return new Circle(new MyPosn((this.position.x + this.velocity.x),
        (this.position.y + this.velocity.y)), this.velocity, this.radius, this.color);
  }

  boolean isOffScreen(int width, int height) {
    return position.isOffScreen(width, height);
  }

  WorldImage draw() {
    return new CircleImage(this.radius, OutlineMode.SOLID, this.color);
  }
}

// the interface for lists of circles
interface ILoCircle {
  ILoCircle moveAll();

  ILoCircle removeOffScreen(int width, int height);
}

class MtLoCircle implements ILoCircle {
  MtLoCircle() {}

  public ILoCircle moveAll() {
    return this;
  }

  public ILoCircle removeOffScreen(int width, int height) {
    return this;
  }
}

class ConsLoCircle implements ILoCircle {
  Circle first;
  ILoCircle rest;

  ConsLoCircle(Circle first, ILoCircle rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILoCircle moveAll() {
    return new ConsLoCircle(this.first.move(), this.rest.moveAll());
  }

  public ILoCircle removeOffScreen(int width, int height) {
    if (this.first.isOffScreen(width, height)) {
      return this.rest.removeOffScreen(width, height);
    }
    else {
      return new ConsLoCircle(this.first, this.rest.removeOffScreen(width, height));
    }
  }
}














