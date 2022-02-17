// CS 2510, Assignment 4, Problem 1

// Pooja Gidwani, Ben Bishop

import tester.*;

// represents a bagel recipe
class BagelRecipe {

  /* TEMPLATE:
  Fields:
  ... this.flour ...       -- double
  ... this.water ...       -- double
  ... this.yeast ...       -- double
  ... this.salt ...        -- double
  ... this.malt ...        -- double

  Methods:
  ... this.sameRecipe(BagelRecipe) ...   -- boolean
   */

  double flour;
  double water;
  double yeast;
  double salt;
  double malt;

  // the constructor w/ inputted values for weight (5 parameters given)
  BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    this.flour = (new Utils()).checkFlourAndYeast(flour, water, "Invalid flour weight: ");
    this.water = water;
    this.yeast = (new Utils()).checkFlourAndYeast(yeast, malt, "Invalid yeast weight: ");
    this.malt = malt;
    this.salt = (new Utils()).checkSalt(salt, yeast, flour, "Invalid salt and yeast weight: "); 
  }

  // the constructor forcing a perfect bagel in weights (2 parameters given)
  BagelRecipe(double flour, double yeast) {
    this.flour = flour;
    this.water = this.flour;
    this.yeast = yeast;
    this.malt = this.yeast;
    this.salt = ((1 / 20) * this.flour) - this.yeast;
  }

  // the constructor forcing a perfect bagel in volumes (3 parameters given)
  BagelRecipe(double flour, double yeast, double salt) {
    this.flour = (flour * 4.25);
    this.water = this.flour;
    this.yeast = (yeast / 48 * 5);
    this.malt = this.yeast;
    this.salt = (new Utils()).checkSalt((salt / 48 * 10) ,
        this.yeast, this.flour, "Invalid salt and yeast weight: ");
  }

  // returns true if this BagelRecipe and another BagelRecipe are equal
  boolean sameRecipe( BagelRecipe other ) {
    return (new Utils()).checkDifference(this.flour, other.flour) 
        && (new Utils()).checkDifference(this.water, other.water)
        && (new Utils()).checkDifference(this.yeast, other.yeast)  
        && (new Utils()).checkDifference(this.salt, other.salt) 
        && (new Utils()).checkDifference(this.malt, other.malt);
  }
}

// abstractions for the BagelRecipe functions
class Utils {

  /* TEMPLATE:

  Methods:
  ... this.checkFlourAndYeast(double value,
      double checkMeasurement,
      String message) ...                    -- double
  ... this.checkSalt(double salt,
      double yeast,
      double flour,
      String message) ...                    -- double
  ... checkDifference(double thisVal,
      double otherVal) ...                   -- boolean
   */

  // the constructor
  Utils() {}

  // checks the measurements for flour and yeast,
  // and throws an exception if invalid
  double checkFlourAndYeast(double value, double checkMeasurement, String message) {
    if (value == checkMeasurement) {
      return value;
    }
    else {
      throw new IllegalArgumentException(message + Double.toString( value) );
    }
  }

  // checks the measurements for salt, and throws an exception if invalid
  double checkSalt(double salt, double yeast, double flour, String message) {
    if (Math.abs((salt + yeast) - (0.05 * flour)) <= 0.01) {
      return salt;
    }
    else {
      throw new IllegalArgumentException(message + Double.toString( salt + yeast ) );
    }
  }

  // checks if two values are the same within 0.001
  boolean checkDifference(double thisVal, double otherVal) {
    return (Math.abs(thisVal - otherVal) <= 0.001);
  }
}

// Examples and tests for the BagelRecipe class
class ExamplesBagels {

  ExamplesBagels() {}

  // examples of bagel recipes
  BagelRecipe plain = new BagelRecipe(7.0, 7.0, 0.125, 0.225, 0.125);
  BagelRecipe glutenFree = new BagelRecipe(14.0, 3.0, 12.8);
  BagelRecipe creamCheese = new BagelRecipe(40, 1);

  // tests of invalid BagelRecipes
  boolean testBagelRecipe(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Invalid flour weight: 2.0"),
        "BagelRecipe", 2.0, 4.0, 1.0, 1.0, 0.3)
        && t.checkConstructorException(
            new IllegalArgumentException("Invalid salt and yeast weight: 1.5416666666666667"),
            "BagelRecipe", 3.0, 3.0, 5.9);
  }

  // tests of the sameRecipe method
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(this.glutenFree.sameRecipe(this.glutenFree), true)
        && t.checkExpect(this.creamCheese.sameRecipe(this.plain), false);
  }
}

