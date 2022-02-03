import tester.Tester;

// Represents a picture with shapes and operations
interface IPicture {
  int getWidth();

  int countShapes();

  int comboDepth();

  IPicture mirror();

  String pictureRecipe(int depth);
}

// Represents a shape in a picture
class Shape implements IPicture {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.kind ...                      -- String
  ... this.size...                       -- int

  Methods:
  ... this.getWidth() ...                -- int
  ... this.countShapes() ...             -- int
  ... this.comboDepth() ...              -- int
  ... this.mirror() ...                  -- IPicture
  ... this.pictureRecipe(int depth) ...  -- String
   */

  String kind;
  int size;

  // the constructor
  Shape(String kind, int size) {
    this.kind = kind;
    this.size = size;
  }

  // Produces the width of this shape
  public int getWidth() {
    return this.size;
  }

  // Produces 1 to count this shape
  public int countShapes() {
    return 1;
  }

  // Produces 0 to count the fact that there are 0
  // layers of combinations on a simple shape
  public int comboDepth() {
    return 0;
  }

  // Produces a Shape with the same information as this shape
  public IPicture mirror() {
    return new Shape(this.kind, this.size);
  }

  // Produces the kind of this shape 
  public String pictureRecipe(int depth) {
    return this.kind;
  }
}

// Represents a shape combined with changes made by specific operations
// in a picture
class Combo implements IPicture {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.name ...                                 -- String
  ... this.operation...                             -- IOperation

  Methods:
  ... this.getWidth() ...                           -- int
  ... this.countShapes() ...                        -- int
  ... this.comboDepth() ...                         -- int
  ... this.mirror() ...                             -- IPicture
  ... this.pictureRecipe(int depth) ...             -- String

  Methods for fields:
  ... this.operation.checkWidth() ...               -- int
  ... this.operation.checkCount() ...               -- int
  ... this.operation.checkComboDepth() ...          -- int
  ... this.operation.mirrorFunction() ...           -- IPicture
  ... this.operation.pictureFunction(int depth) ... -- String
   */

  String name;
  IOperation operation;

  // constructor
  Combo(String name, IOperation operation) {
    this.name = name;
    this.operation = operation;
  }

  // Produces the width from the operations
  public int getWidth() {
    return this.operation.checkWidth();
  }

  // Produces the count of the shapes in the operation
  public int countShapes() {
    return this.operation.checkCount();
  }

  // Produces the levels of operations
  public int comboDepth() {
    return this.operation.checkComboDepth();
  }

  // Produces the combination with the operation mirrored
  public IPicture mirror() {
    return new Combo(this.name, this.operation.mirrorFunction());
  }

  // Produces the recipe with all the operations made to the
  // depth specified
  public String pictureRecipe(int depth) {
    if (depth == 0) {
      return this.name;
    }
    else {
      return this.operation.pictureFunction(depth);
    }
  }
}

// Represents an operation
interface IOperation {

  int checkWidth();

  int checkCount();

  int checkComboDepth();

  IOperation mirrorFunction();

  String pictureFunction(int depth);
}

// Represents a scaled picture by a factor of 2, which is an operation
class Scale implements IOperation {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.picture ...                                 -- IPicture

  Methods:
  ... this.checkWidth() ...                           -- int
  ... this.checkCount() ...                           -- int
  ... this.checkComboDepth() ...                      -- int
  ... this.mirrorFunction() ...                       -- IPicture
  ... this.pictureFunction(int depth) ...             -- String

  Methods for fields:
  ... this.picture.getWidth() ...                     -- int
  ... this.picture.countShapes() ...                  -- int
  ... this.picture.comboDepth() ...                   -- int
  ... this.picture.mirror() ...                       -- IPicture
  ... this.picture.pictureRecipe(int depth) ...       -- String
   */

  IPicture picture;

  // the constructor
  Scale(IPicture picture) {
    this.picture = picture;
  }

  // Produces the width of the shape considering the
  // operation performed
  public int checkWidth() {
    return this.picture.getWidth() * 2;
  }

  // Produces the count of the shapes present in 
  // picture used in this operation
  public int checkCount() {
    return picture.countShapes();
  }

  // Produces the levels of operations, counting the 
  // scale happening at this point
  public int checkComboDepth() {
    return picture.comboDepth() + 1;
  }

  // Produces a mirrored image
  public IOperation mirrorFunction() {
    return new Scale(this.picture.mirror());
  }

  // Produces a phrase explaining the operation performed
  // at this step
  public String pictureFunction(int depth) { 
    return "scale(" + this.picture.pictureRecipe(depth - 1) + ")";
  }
}

// Represents two images placed next to each other, which is an operation
class Beside implements IOperation {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.picture1 ...                                -- IPicture
  ... this.picture2 ...                                -- IPicture

  Methods:
  ... this.checkWidth() ...                           -- int
  ... this.checkCount() ...                           -- int
  ... this.checkComboDepth() ...                      -- int
  ... this.mirrorFunction() ...                       -- IPicture
  ... this.pictureFunction(int depth) ...             -- String

  Methods for fields:
  ... this.picture1.getWidth() ...                    -- int
  ... this.picture1.countShapes() ...                 -- int
  ... this.picture1.comboDepth() ...                  -- int
  ... this.picture1.mirror() ...                      -- IPicture
  ... this.picture1.pictureRecipe(int depth) ...      -- String
  ... this.picture2.getWidth() ...                    -- int
  ... this.picture2.countShapes() ...                 -- int
  ... this.picture2.comboDepth() ...                  -- int
  ... this.picture2.mirror() ...                      -- IPicture
  ... this.picture2.pictureRecipe(int depth) ...      -- String
   */

  IPicture picture1;
  IPicture picture2;

  // the constructor
  Beside(IPicture picture1, IPicture picture2) {
    this.picture1 = picture1;
    this.picture2 = picture2;
  }

  // Produces the width of the shape considering the
  // operation performed
  public int checkWidth() {
    return this.picture1.getWidth() + this.picture2.getWidth();
  }

  // Produces the count of the shapes present in 
  // picture used in this operation
  public int checkCount() {
    return picture1.countShapes() + picture2.countShapes();
  }

  // Produces the levels of operations, counting the 
  // beside happening at this point
  public int checkComboDepth() {
    if (picture1.comboDepth() <= picture2.comboDepth()) {
      return picture2.comboDepth() + 1;
    }
    else {
      return picture1.comboDepth() + 1;
    }
  }

  // Produces a mirrored image
  public IOperation mirrorFunction() {
    return new Beside(this.picture2.mirror(), this.picture1.mirror());
  }

  // Produces a phrase explaining the operation performed
  // at this step
  public String pictureFunction(int depth) { 
    return "beside(" + this.picture1.pictureRecipe(depth - 1) +
        ", " + this.picture2.pictureRecipe(depth - 1) + ")";
  }
}

// Represents an image put on top of another, which is an operation
class Overlay implements IOperation {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.topPicture ...                                -- IPicture
  ... this.bottomPicture ...                             -- IPicture

  Methods:
  ... this.checkWidth() ...                              -- int
  ... this.checkCount() ...                              -- int
  ... this.checkComboDepth() ...                         -- int
  ... this.mirrorFunction() ...                          -- IPicture
  ... this.pictureFunction(int depth) ...                -- String

  Methods for fields:
  ... this.topPicture.getWidth() ...                     -- int
  ... this.topPicture.countShapes() ...                  -- int
  ... this.topPicture.comboDepth() ...                   -- int
  ... this.topPicture.mirror() ...                       -- IPicture
  ... this.topPicture.pictureRecipe(int depth) ...       -- String
  ... this.bottomPicture.getWidth() ...                  -- int
  ... this.bottomPicture.countShapes() ...               -- int
  ... this.bottomPicture.comboDepth() ...                -- int
  ... this.bottomPicture.mirror() ...                    -- IPicture
  ... this.bottomPicture.pictureRecipe(int depth) ...    -- String
   */

  IPicture topPicture;
  IPicture bottomPicture;

  // the constructor
  Overlay(IPicture topPicture, IPicture bottomPicture) {
    this.topPicture = topPicture;
    this.bottomPicture = bottomPicture;
  }

  // Produces the width of the shape considering the
  // operation performed
  public int checkWidth() {
    if (this.topPicture.getWidth() <= this.bottomPicture.getWidth()) {
      return this.bottomPicture.getWidth();
    }
    else {
      return this.topPicture.getWidth();
    }
  }

  // Produces the count of the shapes present in 
  // picture used in this operation
  public int checkCount() {
    return topPicture.countShapes() + bottomPicture.countShapes();
  }

  // Produces the levels of operations, counting the 
  // overlay happening at this point
  public int checkComboDepth() {
    if (topPicture.comboDepth() <= bottomPicture.comboDepth()) {
      return bottomPicture.comboDepth() + 1;
    }
    else {
      return topPicture.comboDepth() + 1;
    }
  }

  // Produces a mirrored image
  public IOperation mirrorFunction() {
    return new Overlay(this.topPicture.mirror(), this.bottomPicture.mirror());
  }

  // Produces a phrase explaining the operation performed
  // at this step
  public String pictureFunction(int depth) { 
    return "overlay(" + this.topPicture.pictureRecipe(depth - 1) +
        ", " + this.bottomPicture.pictureRecipe(depth - 1) + ")";
  }
}


//examples and tests for the classes and interfaces that represent pictures
// and operations
class ExamplesPicture {
  ExamplesPicture() {}

  // examples of pictures and operations
  IPicture circle = new Shape("circle", 20);
  IPicture square = new Shape("square", 30);
  IOperation scaledCircle = new Scale(this.circle);
  IPicture bigCircle = new Combo("big circle", this.scaledCircle);
  IOperation overlaySquareBigCircle = new Overlay(this.square, this.bigCircle);
  IPicture squareOnCircle = new Combo("square on circle", this.overlaySquareBigCircle);
  IOperation besideSquareOnCircle = new Beside(this.squareOnCircle, this.squareOnCircle);
  IPicture doubledSquareOnCircle = new Combo("doubled square on circle",
      this.besideSquareOnCircle);
  IOperation scaleSquare = new Scale(this.square);
  IPicture bigSquare = new Combo("big square", this.scaleSquare);
  IOperation overlaySquares = new Overlay(this.square, this.square);
  IPicture squareOnSquare = new Combo("square on square", this.overlaySquares);
  IOperation besideCircleSquare = new Beside(this.circle, this.square);
  IPicture circleAndSquare = new Combo("circle and square", this.besideCircleSquare);

  // tests the method getWidth on the picture interface, and tests the method
  // checkWidth on the operation interface
  boolean testWidth(Tester t) {
    return t.checkExpect(this.circle.getWidth(), 20)
        && t.checkExpect(this.doubledSquareOnCircle.getWidth(), 80)
        && t.checkExpect(this.besideSquareOnCircle.checkWidth(), 80);
  }

  // tests the method countShapes on the picture interface, and tests the method
  // checkCount on the operation interface
  boolean testCount(Tester t) {
    return t.checkExpect(this.circle.countShapes(), 1)
        && t.checkExpect(this.doubledSquareOnCircle.countShapes(), 4)
        && t.checkExpect(this.besideSquareOnCircle.checkCount(), 4);
  }

  // tests the method comboDepth on the picture interface, and tests the method
  // checkComboDepth on the operation interface
  boolean testComboDepth(Tester t) {
    return t.checkExpect(this.circle.comboDepth(), 0)
        && t.checkExpect(this.doubledSquareOnCircle.comboDepth(), 3)
        && t.checkExpect(this.besideSquareOnCircle.checkComboDepth(), 3);
  }

  // tests the method mirror on the picture interface, and tests the method
  // mirrorFunction on the operation interface
  boolean testMirror(Tester t) {
    return t.checkExpect(this.circle.mirror(), this.circle)
        && t.checkExpect(this.circleAndSquare.mirror(),
            new Combo("circle and square", new Beside(this.square, this.circle)))
        && t.checkExpect(this.besideSquareOnCircle.mirrorFunction(),
            new Beside(this.squareOnCircle, this.squareOnCircle));
  }

  // tests the method pictureRecipe on the picture interface, and tests the method
  // pictureFunction on the operation interface
  boolean testPictureRecipe(Tester t) {
    return t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(2),
        "beside(overlay(square, big circle), overlay(square, big circle))")
        && t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(3),
            "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))")
        && t.checkExpect(this.besideSquareOnCircle.pictureFunction(3),
            "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))");
  }
}
