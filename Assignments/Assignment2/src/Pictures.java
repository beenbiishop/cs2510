import tester.Tester;

interface IPicture {
  int getWidth();

  int countShapes();

  int comboDepth();

  IPicture mirror();

  String pictureRecipe(int depth);
}

class Shape implements IPicture {
  String kind;
  int size;

  // the constructor
  Shape(String kind, int size) {
    this.kind = kind;
    this.size = size;
  }

  public int getWidth() {
    return this.size;
  }

  public int countShapes() {
    return 1;
  }

  public int comboDepth() {
    return 0;
  }

  public IPicture mirror() {
    return new Shape(this.kind, this.size);
  }

  public String pictureRecipe(int depth) {
    return this.kind;
  }
}

class Combo implements IPicture {
  String name;
  IOperation operation;

  Combo(String name, IOperation operation) {
    this.name = name;
    this.operation = operation;
  }

  public int getWidth() {
    return this.operation.checkWidth();
  }

  public int countShapes() {
    return this.operation.checkCount();
  }

  public int comboDepth() {
    return this.operation.checkComboDepth();
  }

  public IPicture mirror() {
    return new Combo(this.name, this.operation.mirrorFunction());
  }

  public String pictureRecipe(int depth) {
    if (depth == 0) {
      return this.name;
    }
    else {
      return this.operation.pictureFunction(depth);
    }
  }
}

interface IOperation {
  int checkWidth();

  int checkCount();

  int checkComboDepth();

  IOperation mirrorFunction();

  String pictureFunction(int depth);
}

class Scale implements IOperation {
  IPicture picture;

  // the constructor
  Scale(IPicture picture) {
    this.picture = picture;
  }

  public int checkWidth() {
    return this.picture.getWidth() * 2;
  }

  public int checkCount() {
    return picture.countShapes();
  }

  public int checkComboDepth() {
    return picture.comboDepth() + 1;
  }

  public IOperation mirrorFunction() {
    return new Scale(this.picture.mirror());
  }

  public String pictureFunction(int depth) { 
    return "scale(" + this.picture.pictureRecipe(depth - 1) + ")";
  }
}

class Beside implements IOperation {
  IPicture picture1;
  IPicture picture2;

  // the constructor
  Beside(IPicture picture1, IPicture picture2) {
    this.picture1 = picture1;
    this.picture2 = picture2;
  }

  public int checkWidth() {
    return this.picture1.getWidth() + this.picture2.getWidth();
  }

  public int checkCount() {
    return picture1.countShapes() + picture2.countShapes();
  }

  public int checkComboDepth() {
    if (picture1.comboDepth() <= picture2.comboDepth()) {
      return picture2.comboDepth() + 1;
    }
    else {
      return picture1.comboDepth() + 1;
    }
  }

  public IOperation mirrorFunction() {
    return new Beside(this.picture2.mirror(), this.picture1.mirror());
  }

  public String pictureFunction(int depth) { 
    return "beside(" + this.picture1.pictureRecipe(depth - 1) +
        ", " + this.picture2.pictureRecipe(depth - 1) + ")";
  }
}

class Overlay implements IOperation {
  IPicture topPicture;
  IPicture bottomPicture;

  // the constructor
  Overlay(IPicture topPicture, IPicture bottomPicture) {
    this.topPicture = topPicture;
    this.bottomPicture = bottomPicture;
  }

  public int checkWidth() {
    if (this.topPicture.getWidth() <= this.bottomPicture.getWidth()) {
      return this.bottomPicture.getWidth();
    }
    else {
      return this.topPicture.getWidth();
    }
  }

  public int checkCount() {
    return topPicture.countShapes() + bottomPicture.countShapes();
  }

  public int checkComboDepth() {
    if (topPicture.comboDepth() <= bottomPicture.comboDepth()) {
      return bottomPicture.comboDepth() + 1;
    }
    else {
      return topPicture.comboDepth() + 1;
    }
  }

  public IOperation mirrorFunction() {
    return new Overlay(this.topPicture.mirror(), this.bottomPicture.mirror());
  }

  public String pictureFunction(int depth) { 
    return "overlay(" + this.topPicture.pictureRecipe(depth - 1) +
        ", " + this.bottomPicture.pictureRecipe(depth - 1) + ")";
  }
}


class ExamplesPicture { 
  IPicture circle = new Shape("circle", 20);
  IPicture square = new Shape("square", 30);
  IPicture bigCircle = new Combo("big circle",
      new Scale(this.circle));
  IPicture squareOnCircle = new Combo("square on circle",
      new Overlay(this.square, this.bigCircle));
  IPicture doubledSquareOnCircle = new Combo("doubled square on circle",
      new Beside(this.squareOnCircle, this.squareOnCircle));
  IPicture bigSquare = new Combo("small square",
      new Scale(this.square));
  IPicture squareOnSquare = new Combo("square on square",
      new Overlay(this.square, this.square));
  IPicture circleAndSquare = new Combo("circle and square",
      new Beside(this.circle, this.square));

  // tests
  boolean testWidth(Tester t) {
    return t.checkExpect(this.circle.getWidth(), 20)
        && t.checkExpect(this.doubledSquareOnCircle.getWidth(), 80);
  }

  boolean testCount(Tester t) {
    return t.checkExpect(this.circle.countShapes(), 1)
        && t.checkExpect(this.doubledSquareOnCircle.countShapes(), 4);
  }

  boolean testComboDepth(Tester t) {
    return t.checkExpect(this.circle.comboDepth(), 0)
        && t.checkExpect(this.doubledSquareOnCircle.comboDepth(), 3);
  }

  boolean testMirror(Tester t) {
    return t.checkExpect(this.circle.mirror(), this.circle)
        && t.checkExpect(this.circleAndSquare.mirror(),
            new Combo("circle and square", new Beside(this.square, this.circle)));
  }

  boolean testPictureRecipe(Tester t) {
    return t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(2),
        "beside(overlay(square, big circle), overlay(square, big circle))")
        && t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(3),
            "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))");
  }
}
