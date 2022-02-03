import tester.Tester;
//add templates and comments

class EmbroideryPiece {
  String name;
  IMotif motif;

  EmbroideryPiece(String name, IMotif motif) {
    this.name = name;
    this.motif = motif;
  }

  double averageDifficulty() {
    if (this.motif.getCount() == 0) {
      return 0;
    }
    else {
      return this.motif.getDifficulties() / this.motif.getCount();
    }
  }

  String embroideryInfo() {
    return this.name + ": " + this.motif.getName() + ".";
  }
}

interface IMotif {
  double getDifficulties();

  int getCount();

  String getName();
}

class CrossStitchMotif implements IMotif {
  String description;
  double difficulty;

  CrossStitchMotif(String description, Double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }

  public double getDifficulties() {
    return this.difficulty;
  }

  public int getCount() {
    return 1;
  }

  public String getName() {
    return description + " (cross stitch)";
  }
}

class ChainStitchMotif implements IMotif {
  String description;
  double difficulty;

  ChainStitchMotif(String description, Double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }

  public double getDifficulties() {
    return this.difficulty;
  }

  public int getCount() {
    return 1;
  }

  public String getName() {
    return description + " (chain stitch)";
  }
}

class GroupMotif implements IMotif {
  String description;
  ILoMotif motifs;

  GroupMotif(String description, ILoMotif motifs) {
    this.description = description;
    this.motifs = motifs;
  }

  public double getDifficulties() {
    return this.motifs.checkDifficulties();
  }

  public int getCount() {
    return this.motifs.checkCount();
  }

  public String getName() {
    return this.motifs.checkName();
  }
}

interface ILoMotif {
  double checkDifficulties();

  int checkCount();

  String checkName();
}

class MtLoMotif implements ILoMotif {
  public double checkDifficulties() {
    return 0.0;
  }

  public int checkCount() {
    return 0;
  }

  public String checkName() {
    return "";
  }
}

class ConsLoMotif implements ILoMotif {
  IMotif first;
  ILoMotif rest;

  ConsLoMotif(IMotif first, ILoMotif rest) {
    this.first = first;
    this.rest = rest;
  } 

  public double checkDifficulties() {
    return this.first.getDifficulties() + this.rest.checkDifficulties();
  }

  public int checkCount() {
    return this.first.getCount() + this.rest.checkCount();
  }

  public String checkName() {
    if (this.rest.checkName().equals("")) {
      return this.first.getName();
    }
    else {
      return this.first.getName() + ", " + this.rest.checkName();
    }
  }
}

class ExamplesEmbroidery { 
  ILoMotif flowers = new ConsLoMotif(new CrossStitchMotif("rose", 5.0), 
      new ConsLoMotif(new ChainStitchMotif("poppy", 4.75),
          new ConsLoMotif(new CrossStitchMotif("daisy", 3.2), new MtLoMotif())));

  ILoMotif elements = new ConsLoMotif(new CrossStitchMotif("bird", 4.5),
      new ConsLoMotif(new ChainStitchMotif("tree", 3.0), 
          new ConsLoMotif(new GroupMotif("flowers", this.flowers), new MtLoMotif())));  

  GroupMotif nature = new GroupMotif("nature", this.elements);
  EmbroideryPiece pillowCover = new EmbroideryPiece("Pillow Cover", this.nature);

  boolean testAverage(Tester t) {
    return t.checkInexact(this.pillowCover.averageDifficulty(), 4.09, 0.01);
  }

  boolean testInfo(Tester t) {
    return t.checkExpect(this.pillowCover.embroideryInfo(), "Pillow Cover:"
        + " bird (cross stitch), tree (chain stitch), rose (cross stitch), "
        + "poppy (chain stitch), daisy (cross stitch).");
  }

}


