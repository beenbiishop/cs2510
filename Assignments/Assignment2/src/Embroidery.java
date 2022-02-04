import tester.Tester;

// Pooja Gidwani and Ben Bishop

// Represents an embroidery piece
class EmbroideryPiece {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.name ...                      -- String
  ... this.motif...                      -- IMotif

  Methods:
  ... this.averageDifficulty() ...       -- double
  ... this.embroideryInfo() ...          -- String

  Methods for fields:
  ... this.motif.getDifficulties() ...   -- double
  ... this.motif.getCount() ...          -- int
  ... this.motif.getName() ...           -- String
   */

  String name;
  IMotif motif;

  // constructor
  EmbroideryPiece(String name, IMotif motif) {
    this.name = name;
    this.motif = motif;
  }

  // Produces the average difficulty of the cross and chain stitch motifs
  // in an embroidery piece
  double averageDifficulty() {
    if (this.motif.getCount() == 0) {
      return 0;
    }
    else {
      return this.motif.getDifficulties() / this.motif.getCount();
    }
  }

  // Produces a sentence of all the names of the cross and chain stitch motifs
  // in an embroidery piece, also stating the type of motif it is.
  String embroideryInfo() {
    return this.name + ": " + this.motif.getName() + ".";
  }
}

// Represents a motif
interface IMotif {
  double getDifficulties();

  int getCount();

  String getName();
}

// represents a cross stitch, which is a type of motif
class CrossStitchMotif implements IMotif {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.description ...                       -- String
  ... this.difficulty...                         -- double

  Methods:
  ... this.getDifficulties() ...                 -- double
  ... this.getCount() ...                        -- int
  ... this.getName() ...                         -- String
   */

  String description;
  double difficulty;

  // constructor
  CrossStitchMotif(String description, Double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }

  // Produces the difficulty of this motif
  public double getDifficulties() {
    return this.difficulty;
  }

  // Produces 1 to count this motif
  public int getCount() {
    return 1;
  }

  // Produces the name of this motif with the description
  // of the type of stitch it is
  public String getName() {
    return description + " (cross stitch)";
  }
}

// represents a chain stitch, which is a type of motif
class ChainStitchMotif implements IMotif {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.description ...                       -- String
  ... this.difficulty...                         -- double

  Methods:
  ... this.getDifficulties() ...                 -- double
  ... this.getCount() ...                        -- int
  ... this.getName() ...                         -- String
   */

  String description;
  double difficulty;

  // constructor
  ChainStitchMotif(String description, Double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }

  // Produces the difficulty of this motif
  public double getDifficulties() {
    return this.difficulty;
  }

  // Produces 1 to count this motif
  public int getCount() {
    return 1;
  }

  // Produces the name of this motif with the description
  // of the type of stitch it is
  public String getName() {
    return description + " (chain stitch)";
  }
}

// represents a group of motifs
class GroupMotif implements IMotif {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.description ...                  -- String
  ... this.motifs...                        -- ILoMotif

  Methods:
  ... this.getDifficulties() ...            -- double
  ... this.getCount() ...                   -- int
  ... this.getName()...                     -- String

  Methods for fields:
  ... this.motifs.checkDifficulties() ...   -- double
  ... this.motif.checkCount() ...           -- int
  ... this.motif.checkName() ...            -- String
   */

  String description;
  ILoMotif motifs;

  // constructor
  GroupMotif(String description, ILoMotif motifs) {
    this.description = description;
    this.motifs = motifs;
  }

  // Produces the combined difficulties of the list of
  // motifs, counting the cross and chain stitches
  public double getDifficulties() {
    return this.motifs.checkDifficulties();
  }

  // Produces the number of motifs in the list, counting
  // the cross and chain stitches
  public int getCount() {
    return this.motifs.checkCount();
  }

  // Produces the name of each motif in the list with
  // the description of the type of stitch it is
  public String getName() {
    return this.motifs.checkName();
  }
}

// Represents a list of motifs
interface ILoMotif {
  double checkDifficulties();

  int checkCount();

  String checkName();
}

// Represents an empty list of motifs
class MtLoMotif implements ILoMotif {

  /*
  TEMPLATE:
  ---------

  Methods:
  ... this.checkDifficulties() ...            -- double
  ... this.checkCount() ...                   -- int
  ... this.checkName()...                     -- String
   */

  // Produces 0.0 as there are no difficulties to report in
  // an empty list
  public double checkDifficulties() {
    return 0.0;
  }

  // Produces 0 as there are no motifs to report in an empty
  // list
  public int checkCount() {
    return 0;
  }

  // Produces an empty string as there are no motif names/stitches
  // to report in an empty list
  public String checkName() {
    return "";
  }
}

// Represents a non-empty list of motifs
class ConsLoMotif implements ILoMotif {

  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.first ...                       -- IMotif
  ... this.rest...                         -- ILoMotif

  Methods:
  ... this.checkDifficulties() ...         -- double
  ... this.checkCount() ...                -- int
  ... this.checkName()...                  -- String

  Methods for fields:
  ... this.first.getDifficulties() ...    -- double
  ... this.motif.getCount() ...           -- int
  ... this.motif.getName() ...            -- String
  ... this.rest.checkDifficulties() ...   -- double
  ... this.rest.checkCount() ...          -- int
  ... this.rest.checkName()...            -- String
   */

  IMotif first;
  ILoMotif rest;

  // constructor
  ConsLoMotif(IMotif first, ILoMotif rest) {
    this.first = first;
    this.rest = rest;
  }

  // Produces the difficulties of the cross and chain stitch
  // motifs in the list
  public double checkDifficulties() {
    return this.first.getDifficulties() + this.rest.checkDifficulties();
  }

  // Produces the count of the cross and chain stitch
  // motifs in the list
  public int checkCount() {
    return this.first.getCount() + this.rest.checkCount();
  }

  // Produces the name of the cross and chain stitch motifs in the
  // list, specifying the type of stitch it is
  public String checkName() {
    if (this.rest.checkName().equals("")) {
      return this.first.getName();
    }
    else {
      return this.first.getName() + ", " + this.rest.checkName();
    }
  }
}

// examples and tests for the classes that represent embroidery pieces
class ExamplesEmbroidery {
  ExamplesEmbroidery() {}

  // examples of motifs in an embroidery piece
  ILoMotif flowers = new ConsLoMotif(new CrossStitchMotif("rose", 5.0),
      new ConsLoMotif(new ChainStitchMotif("poppy", 4.75),
          new ConsLoMotif(new CrossStitchMotif("daisy", 3.2), new MtLoMotif())));

  ILoMotif elements = new ConsLoMotif(new CrossStitchMotif("bird", 4.5),
      new ConsLoMotif(new ChainStitchMotif("tree", 3.0),
          new ConsLoMotif(new GroupMotif("flowers", this.flowers), new MtLoMotif())));

  GroupMotif nature = new GroupMotif("nature", this.elements);
  EmbroideryPiece pillowCover = new EmbroideryPiece("Pillow Cover", this.nature);

  ILoMotif aspects = new ConsLoMotif(new ChainStitchMotif("pooja", 4.5),
      new ConsLoMotif(new CrossStitchMotif("sonia", 5.0),
          new ConsLoMotif(new ChainStitchMotif("will", 5.0), new MtLoMotif())));

  GroupMotif names = new GroupMotif("names", this.aspects);
  EmbroideryPiece blanket = new EmbroideryPiece("Blanket", this.names); 

  // test the method averageDifficulty in the class EmbroideryPiece, test
  // the methods getCount and getDifficulties in the interface IMotif, 
  // and test the methods checkCount and checkDifficulties in the interface 
  // ILoMotif
  boolean testAverage(Tester t) {
    return t.checkInexact(this.pillowCover.averageDifficulty(), 4.09, 0.01)
        && t.checkInexact(this.blanket.averageDifficulty(), 4.83, 0.01)
        && t.checkExpect(this.nature.getCount(), 5)
        && t.checkExpect(this.names.getCount(), 3)
        && t.checkInexact(this.nature.getDifficulties(), 20.45, 0.01)
        && t.checkInexact(this.names.getDifficulties(), 14.5, 0.01)
        && t.checkExpect(this.elements.checkCount(), 5)
        && t.checkExpect(this.aspects.checkCount(), 3)
        && t.checkInexact(this.elements.checkDifficulties(), 20.45, 0.01)
        && t.checkInexact(this.aspects.checkDifficulties(), 14.5, 0.01);
  }

  // test the method embroideryInfo in the class EmbroideryPiece, test
  // the method getName in the interface IMotif, and test the method checkName
  // in the interface ILoMotif
  boolean testInfo(Tester t) {
    return t.checkExpect(this.pillowCover.embroideryInfo(), "Pillow Cover:"
        + " bird (cross stitch), tree (chain stitch), rose (cross stitch), "
        + "poppy (chain stitch), daisy (cross stitch).")
        && t.checkExpect(this.blanket.embroideryInfo(), "Blanket: pooja (chain stitch), "
            + "sonia (cross stitch), will (chain stitch).")
        && t.checkExpect(this.nature.getName(), "bird (cross stitch), tree (chain stitch),"
            + " rose (cross stitch), poppy (chain stitch), daisy (cross stitch)")
        && t.checkExpect(this.names.getName(), "pooja (chain stitch), "
            + "sonia (cross stitch), will (chain stitch)")
        && t.checkExpect(this.elements.checkName(), "bird (cross stitch), tree (chain stitch), "
            + "rose (cross stitch), poppy (chain stitch), daisy (cross stitch)")
        && t.checkExpect(this.aspects.checkName(), "pooja (chain stitch), sonia (cross stitch), "
            + "will (chain stitch)");
  }
}

