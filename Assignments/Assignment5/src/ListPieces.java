import javalib.funworld.WorldScene;
import tester.Tester;

interface ILoPieces extends SetVals {

  // removes pieces that have a posn > screen dimensions from this list
  ILoPieces removePiecesOffScreen(int width, int height);

  // draws all the pieces in this list
  WorldScene drawAll(WorldScene scene);

  // implements the move method on each piece in this list
  ILoPieces moveAll();

  // if collideShipHelper is true, removes the ship from this list
  ILoPieces collideShip(ILoPieces bullets);

  // Does a given ship intersect with this game piece?
  boolean collideShipHelper(IGamePiece ship);

  // recursively generates new bullets after a bullet collides with a ship
  ILoPieces bulletsExplode(ILoPieces ships, ILoPieces newBullets);

  // Has a ship in the list collided with a bullet in the list?
  boolean explodeHelper(IGamePiece first);

  // Removes the old bullet from this list and inserts the new ones.
  ILoPieces updateBullets(ILoPieces newBullets, Bullet bullet);

  // Adds each new bullet to the list.
  ILoPieces updateHelper(ILoPieces newBullets, Bullet bullet, int amt, int bulletPos);

  // Removes a given bullet from this list.
  ILoPieces remove(IGamePiece bullet);
}

class MtLoPieces implements ILoPieces {

  //removes pieces that have a posn > screen dimensions from the list
  public ILoPieces removePiecesOffScreen(int width, int height) {
    return this;
  }

  public WorldScene drawAll(WorldScene scene) {
    return scene;
  }

  public ILoPieces moveAll() {
    return this;
  }

  public ILoPieces collideShip(ILoPieces bullets) {
    return this;
  }

  public boolean collideShipHelper(IGamePiece ship) {
    return false;
  }

  public ILoPieces bulletsExplode(ILoPieces ships, ILoPieces newBullets) {
    return newBullets;
  }

  public boolean explodeHelper(IGamePiece first) {
    return false;
  }

  public ILoPieces updateBullets(ILoPieces newBullets, Bullet bullets) {
    return newBullets;
  }

  public ILoPieces updateHelper(ILoPieces newBullets, Bullet bullet, int amt, int bulletPos) {
    return newBullets;
  }

  public ILoPieces remove(IGamePiece bullet) {
    return this;
  }
}

class ConsLoPieces implements ILoPieces {
  IGamePiece first;
  ILoPieces rest;

  ConsLoPieces(IGamePiece first, ILoPieces rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILoPieces removePiecesOffScreen(int width, int height) {
    if (((AGamePiece)this.first).position.isOffScreen(width, height)) {
      return this.rest.removePiecesOffScreen(width, height);
    }
    else {
      return new ConsLoPieces(this.first, this.rest.removePiecesOffScreen(width, height));
    }
  }

  public WorldScene drawAll(WorldScene scene) {
    return this.rest.drawAll(scene.placeImageXY(this.first.draw(),
        ((AGamePiece) this.first).position.x, ((AGamePiece) this.first).position.y));
  } 

  public ILoPieces moveAll() {
    return new ConsLoPieces(this.first.move(), this.rest.moveAll());
  }

  public ILoPieces collideShip(ILoPieces bullets) {
    if (bullets.collideShipHelper(this.first)) {
      return this.rest.collideShip(bullets);
    }
    else {
      return new ConsLoPieces(this.first, this.rest.collideShip(bullets));
    }
  }

  //Checks whether the ship passed in has the same position
  // as any of the bullets currently on the screen
  public boolean collideShipHelper(IGamePiece ship) {
    MyPosn bulletPos = ((AGamePiece) (this.first)).position;
    MyPosn shipPos = ((AGamePiece) ship).position;
    double dist = Math.hypot(shipPos.x - bulletPos.x,
        shipPos.y - bulletPos.y); //dist between centers 
    int radiusSum = shipRadius + bulletRadius;

    if (dist <= radiusSum) {
      return true;
    }

    else {
      return this.rest.collideShipHelper(ship);
    }
  }

  // Remove bullets that collide with a ship from the list
  public ILoPieces bulletsExplode(ILoPieces ships, ILoPieces newBullets) {
    if (ships.explodeHelper(this.first)) {
      return this.rest.bulletsExplode(ships,
          this.updateBullets(newBullets.remove(this.first), (Bullet) this.first));
    }
    else {
      return new ConsLoPieces(this.first, this.rest.bulletsExplode(ships, newBullets));
    }
  }

  public boolean explodeHelper(IGamePiece bullet) {
    MyPosn shipPos = ((AGamePiece) (this.first)).position;
    MyPosn bulletPos = ((AGamePiece) bullet).position;
    double dist = Math.hypot(shipPos.x - bulletPos.x,
        shipPos.y - bulletPos.y);//dist between centers 
    int radiusSum = shipRadius + bulletRadius;

    if (dist <= radiusSum) {
      return true;
    }

    else {
      return this.rest.explodeHelper(bullet);
    }
  }

  public ILoPieces updateBullets(ILoPieces newBullets, Bullet bullet) {
    return this.updateHelper(newBullets, bullet, bullet.level, 0);
  }

  public ILoPieces updateHelper(ILoPieces newBullets, Bullet bullet, int amt, int bulletPos) {
    double radians = Math.toRadians(bulletPos * (360 / (bullet.level + 1)));
    MyPosn velocity = new MyPosn((int) Math.cos(radians), (int) Math.sin(radians));
    int radius = bullet.radius + 2;
    if (radius >= 8) {
      radius = 8;
    }
    ILoPieces updateNewBullets = 
        new ConsLoPieces(new Bullet(bullet.position, radius,
            velocity, bullet.level + 1), newBullets);
    if (amt >= 0) {
      return this.updateHelper(updateNewBullets, bullet, amt - 1, bulletPos + 1);
    }
    else {
      return this;
    }

  }

  public ILoPieces remove(IGamePiece bullet) {
    if (bullet.isSameBullet((Bullet) this.first)) {
      return this.rest.remove(bullet);
    }
    else {
      return new ConsLoPieces(this.first, this.rest.remove(bullet));
    }
  }
}

class ExamplesListPieces implements SetVals {
  MyPosn pos1 = new MyPosn(WIDTH / 2, HEIGHT);
  MyPosn pos2 = new MyPosn(WIDTH / 2, HEIGHT / 2);
  MyPosn posOff = new MyPosn(WIDTH * 2, HEIGHT * 2);
  MyPosn bVelocity = new MyPosn(0, bulletSpeed);
  MyPosn sVelocity = new MyPosn(0, shipSpeed);
  Bullet bullet = new Bullet(pos1, bulletRadius, bVelocity, 1);
  Bullet bullet2 = new Bullet(pos2, bulletRadius, bVelocity, 1);
  Bullet bulletOff = new Bullet(posOff, bulletRadius, bVelocity, 1);
  Ship ship = new Ship(pos1,sVelocity);
  ILoPieces lop1 = new ConsLoPieces(bullet,
      new ConsLoPieces(bullet2,
          new ConsLoPieces(ship,
              new MtLoPieces())));
  ILoPieces lop2 = new ConsLoPieces(bullet,
      new ConsLoPieces(bulletOff,
          new MtLoPieces()));

  ILoPieces empty = new MtLoPieces();

  ExamplesListPieces() {}

  boolean testRemovePiecesOffScreen(Tester t) {
    return t.checkExpect(this.lop2.removePiecesOffScreen(WIDTH, HEIGHT),
        new ConsLoPieces(this.bullet,
            new MtLoPieces()));
  }

  boolean testMoveAll(Tester t) {
    return t.checkExpect(this.lop1.moveAll(), new ConsLoPieces(this.bullet.move(),
        new ConsLoPieces(this.bulletOff.move(),
            this.empty)));
  }
}









