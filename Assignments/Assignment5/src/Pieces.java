import java.awt.Color;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.WorldImage;
import tester.Tester;

// Represents a game piece
interface IGamePiece {

  // Moves the game piece
  IGamePiece move();

  // Draws the game piece
  WorldImage draw();

  // Checks whether the bullet passed in is the same
  // as this game piece
  boolean isSameBullet(Bullet bullet);
}

// Represents a game piece
abstract class AGamePiece implements IGamePiece, SetVals {
  MyPosn position;
  int radius;
  MyPosn velocity;
  Color color;

  AGamePiece(MyPosn position, int radius, MyPosn velocity, Color color) {
    this.position = position;
    this.radius = radius;
    this.velocity = velocity;
    this.color = color;
  }

  //Moves the game piece
  public abstract IGamePiece move();

  //Draws the game piece
  public WorldImage draw() {
    return new CircleImage(this.radius, OutlineMode.SOLID, this.color);
  }

  //Checks whether the bullet passed in is the same
  // as this game piece
  public abstract boolean isSameBullet(Bullet bullet);
}

// Represents a ship
class Ship extends AGamePiece {

  Ship(MyPosn position, MyPosn velocity) {
    super(position, shipRadius, velocity, shipColor); 
  }

  //Moves the game piece
  public IGamePiece move() {
    return new Ship(new MyPosn(position.x + velocity.x, position.y - velocity.y), 
        this.velocity);
  }

  //Checks whether the bullet passed in is the same
  // as this game piece
  public boolean isSameBullet(Bullet bullet) {
    return false;
  }
}

// Represents a bullet
class Bullet extends AGamePiece {
  int level;

  Bullet(MyPosn position, int bulletRad, MyPosn velocity, int level) {
    super(position, bulletRad, velocity, bulletColor);
    this.level = level;
  }

  //Moves the game piece
  public IGamePiece move() {
    return new Bullet(new MyPosn(position.x + velocity.x, position.y - velocity.y), 
        this.radius, this.velocity, this.level);
  }

  //Checks whether the bullet passed in is the same
  // as this game piece
  public boolean isSameBullet(Bullet bullet) {
    return (this.position.equals(bullet.position)) && this.velocity.equals(bullet.velocity)
        && (this.level == bullet.level);

  }
}

// Examples and tests for game pieces
class ExamplesGamePieces implements SetVals {
  MyPosn pos1 = new MyPosn(WIDTH / 2, HEIGHT);
  MyPosn pos2 = new MyPosn(WIDTH / 2, HEIGHT / 2);
  MyPosn bVelocity = new MyPosn(0, bulletSpeed);
  MyPosn sVelocity = new MyPosn(0, shipSpeed);
  Bullet bullet = new Bullet(pos1, bulletRadius, bVelocity, 1);
  Bullet bullet2 = new Bullet(pos2, bulletRadius, bVelocity, 1);
  Ship ship = new Ship(pos1,sVelocity);

  ExamplesGamePieces() {}

  // tests the move method
  boolean testMove(Tester t) {
    return t.checkExpect(bullet.move(), new Bullet(pos1.add(0, bulletSpeed),
        bulletRadius, bVelocity, 1));
  }

  //tests the draw method
  boolean testDraw(Tester t) {
    return t.checkExpect(bullet.draw(), 
        new CircleImage(bulletRadius, OutlineMode.SOLID, bulletColor));
  }

  //tests the isSameBullet method
  boolean testIsSameBullet(Tester t) {
    return t.checkExpect(bullet.isSameBullet(bullet2), false) 
        && t.checkExpect(bullet.isSameBullet(bullet), true);
  }
}

