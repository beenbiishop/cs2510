// Pooja Gidwani and Ben Bishop
// Assignment 5

import tester.*;

import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

// Holds the constant values of the game
interface SetVals {
  int WIDTH = 500;
  int HEIGHT = 300;

  int bulletRadius = 2;
  int bulletSpeed = 8; 
  //after explosion 2+bulletRadius
  Color bulletColor = Color.pink;

  int shipRadius = (int) Math.round((1 / 30.0) * HEIGHT);
  int shipSpawnFrequency = 4;
  int numShipsToSpawn = (new Random()).nextInt(2) + 1; 
  Color shipColor = Color.cyan;
  int shipSpeed = (int) Math.round(1 / 2.0 * bulletSpeed);
}

// The NBullets game
class Game extends World implements SetVals {
  int numBullets;
  int currentTick; 
  //int finalTick; no set val since game doesnt stop till all bullets are fired
  ILoPieces ships = new MtLoPieces();
  ILoPieces bullets = new MtLoPieces();

  Game(int numBullets) {
    if (numBullets < 0) {
      throw new IllegalArgumentException("Invalid arguments passed to constructor.");
    }
    this.numBullets = numBullets;
    this.currentTick = 0;
  }

  Game(int numBullets, int currentTick, ILoPieces ships, ILoPieces bullets) {
    if (numBullets < 0) {
      throw new IllegalArgumentException("Invalid arguments passed to constructor.");
    }
    this.numBullets = numBullets;
    this.currentTick = currentTick;
    this.ships = ships;
    this.bullets = bullets;
  }

  //For drawing
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(WIDTH, HEIGHT);
    scene = scene.placeImageXY(new TextImage("Num Bullets: " + numBullets, Color.BLACK), 20, 20);
    //scene.placeImageXY(new TextImage("Num Ships: "+ships.numDestroyed(), Color.RED), 20, 20);
    scene = bullets.drawAll(scene);
    scene = ships.drawAll(scene); 
    return scene;
  }

  //Creates a random position for the ship
  public MyPosn randShipPos() {
    if ((new Random()).nextInt(2) == 0) {
      return new MyPosn(0, (new Random()).nextInt(HEIGHT - shipRadius * 2) + shipRadius);
    }
    else {
      return new MyPosn(WIDTH, (new Random()).nextInt(HEIGHT - shipRadius * 2) + shipRadius);
    }
  }

  //Sets the appropriate velocity
  public MyPosn velocityDirection(MyPosn currShip) {
    if (currShip.x == 0) {
      return new MyPosn(shipSpeed, 0);
    }
    else {
      return new MyPosn(-shipSpeed, 0);
    }
  }

  //What we do on every tick, moving
  public Game onTick() {
    MyPosn newShip = randShipPos();
    MyPosn velocity = velocityDirection(newShip);

    this.currentTick = this.currentTick + 1;
    if (currentTick % shipSpawnFrequency == 0) {
      this.ships = new ConsLoPieces(new Ship(newShip, velocity), ships);
    }

    this.bullets = this.bullets.removePiecesOffScreen(WIDTH, HEIGHT).moveAll();
    this.ships = this.ships.removePiecesOffScreen(WIDTH, HEIGHT).moveAll();
    this.bullets = this.bullets.bulletsExplode(this.ships, this.bullets);
    this.ships = this.ships.collideShip(this.bullets);
    return new Game(this.numBullets, this.currentTick, this.ships, this.bullets);
  }

  // Fires a bullet if the user presses space
  public Game onKeyEvent(String key) {
    if (this.numBullets > 0 && key.equals(" ") ) {
      this.bullets = new ConsLoPieces(new Bullet(new MyPosn(WIDTH / 2, HEIGHT),
          bulletRadius, new MyPosn(0, bulletSpeed), 1), bullets);
      return new Game(this.numBullets - 1, this.currentTick, this.ships, this.bullets);
    }
    else {
      return this;
    }
  }

  // Checks whether to end the game
  public WorldEnd worldEnds() {
    if (numBullets <= 0) {
      return new WorldEnd(true, this.makeEndScene());
    } else {
      return new WorldEnd(false, this.makeEndScene());
    }
  }

  // End page of game
  public WorldScene makeEndScene() {
    WorldScene endScene = new WorldScene(WIDTH, HEIGHT);
    return endScene.placeImageXY( new TextImage("Game Over", Color.magenta), 250, 250);
  }
}

// Testing game
class ExamplesWorldProgram implements SetVals {
  Game world = new Game(20);

  boolean testBigBang(Tester t) { 
    //width, height, tickrate = 0.5 means every 0.5 seconds the onTick method will get called.
    return world.bigBang(500, 500, 1.0 / 28.0);
  } 

  boolean testMakeScene(Tester t) {
    WorldScene scene = new WorldScene(500, 300);
    scene = scene.placeImageXY(new TextImage("Num Bullets: " + 20, Color.BLACK), 20, 20);
    return t.checkExpect(this.world.makeScene(), scene);
  }

  //test randShipPos
  //random ship position on left or right at any height in bounds

  //test velocityDirection
  //assigns the appropriate velocity for the ship inputted

  boolean testOnTick(Tester t) {
    return t.checkExpect(this.world.onTick(), 
        new Game(20, 1, new MtLoPieces(), new MtLoPieces()));   
  }

  boolean testOnKeyEvent(Tester t) {
    Bullet bullet = new Bullet(new MyPosn(WIDTH / 2, HEIGHT),
        bulletRadius, new MyPosn(0, bulletSpeed), 1);
    return t.checkExpect(this.world.onKeyEvent(" "), 
        new Game(19, 0, new MtLoPieces(), new ConsLoPieces(bullet, new MtLoPieces())))
        && t.checkExpect(this.world.onKeyEvent("a"), 
            new Game(19, 0, new MtLoPieces(), new MtLoPieces()));   
  }

  // testWorldEnds method
  //this.world.worldEnds() -> new WorldEnd(false, any scene)
}

