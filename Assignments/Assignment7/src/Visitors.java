// Assignment 7, Problem 1
// Pooja Gidwani and Ben Bishop

import tester.Tester;
import java.util.function.Function;
import java.util.function.BiFunction;

// Represents an addition function
class Plus implements BiFunction<Double, Double, Double> {
  // Returns the sum of two given values
  public Double apply(Double input1, Double input2) {
    return input1 + input2;
  }
}

// Represents a subtraction function
class Minus implements BiFunction<Double, Double, Double> {
  // Returns the difference of two given values
  public Double apply(Double input1, Double input2) {
    return input1 - input2;
  }
}

// Represents a multiplication function
class Mul implements BiFunction<Double, Double, Double> {
  // Returns the product of two given values
  public Double apply(Double input1, Double input2) {
    return input1 * input2;
  }
}

// Represents a division function
class Div implements BiFunction<Double, Double, Double> {
  // Returns the quotient of two given values
  public Double apply(Double input1, Double input2) {
    return input1 / input2;
  }
}

// Represents a negative function
class Neg implements Function<Double, Double> {
  // Returns the opposite of a given value
  public Double apply(Double input) {
    return input * -1.0;
  }
}

// Represents a square function
class Sqr implements Function<Double, Double> {
  // Returns a given value squared
  public Double apply(Double input) {
    return Math.pow(input, 2);
  }
}

// Represents an arithmetic expression
interface IArith {
  <R> R accept(IArithVisitor<R> visitor);
}

// Represents a constant in an arithmetic expression
class Const implements IArith {
  double num;

  Const(double num) {
    this.num = num;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

// Represents a unary formula in an arithmetic expression
class UnaryFormula implements IArith {
  Function<Double, Double> func;
  String name;
  IArith child;

  UnaryFormula(Function<Double, Double> func, String name, IArith child) {
    this.func = func;
    this.name = name;
    this.child = child;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitUnaryFormula(this);
  }
}

// Represents a binary formula in an arithmetic expression
class BinaryFormula implements IArith {
  BiFunction<Double, Double, Double> func;
  String name;
  IArith left;
  IArith right;

  BinaryFormula(BiFunction<Double, Double, Double> func, String name, IArith left, IArith right) {
    this.func = func;
    this.name = name;
    this.left = left;
    this.right = right;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitBinaryFormula(this);
  }
}

//Represents a visitor that visits an IArith and produces a result of type R
interface IArithVisitor<R> {

  // Applies the this object onto the given IArith
  R apply(IArith object);

  // Returns the value from visiting the given Const
  R visitConst(Const given);

  //Returns the value from visiting the given Unary formula
  R visitUnaryFormula(UnaryFormula given);

  //Returns the value from visiting the given Binary formula
  R visitBinaryFormula(BinaryFormula given);
}

// Evaluates a tree to a Double
class EvalVisitor implements IArithVisitor<Double> {
  public Double apply(IArith object) {
    return object.accept(this);
  }

  public Double visitConst(Const given) {
    return given.num;
  }

  public Double visitUnaryFormula(UnaryFormula given) {
    return given.func.apply(given.child.accept(this));
  }

  public Double visitBinaryFormula(BinaryFormula given) {
    return given.func.apply(given.left.accept(this), given.right.accept(this));
  }
}

//Evaluates a tree to a String equation shown in 
// prefix notation
class PrintVisitor implements IArithVisitor<String> {
  public String apply(IArith object) {
    return object.accept(this);
  }

  public String visitConst(Const given) {
    return Double.toString(given.num);
  }

  public String visitUnaryFormula(UnaryFormula given) {
    return "(" + given.name + " " + given.child.accept(this) + ")";
  }

  public String visitBinaryFormula(BinaryFormula given) {
    return "(" + given.name + " " + given.left.accept(this) + " " + given.right.accept(this) + ")";
  }
}

//Evaluates a tree to a an IArith
class DoublerVisitor implements IArithVisitor<IArith> {
  public IArith apply(IArith object) {
    return object.accept(this);
  }

  public IArith visitConst(Const given) {
    return new Const(given.num * 2);
  }

  public IArith visitUnaryFormula(UnaryFormula given) {
    return new UnaryFormula(given.func, given.name, given.child.accept(this));
  }

  public IArith visitBinaryFormula(BinaryFormula given) {
    return new BinaryFormula(given.func, given.name,
        given.left.accept(this), given.right.accept(this));
  }
}

// Evaluates the tree to a boolean, returning true if
// it never encounters a negative number 
class NoNegativeResults implements IArithVisitor<Boolean> {

  public Boolean apply(IArith object) {
    return object.accept(this);
  }

  public Boolean visitConst(Const given) {
    return given.num >= 0;
  }

  public Boolean visitUnaryFormula(UnaryFormula given) {
    IArithVisitor<Double> eval = new EvalVisitor();
    return (given.accept(eval) >= 0) && given.child.accept(this);
  }

  public Boolean visitBinaryFormula(BinaryFormula given) {
    IArithVisitor<Double> eval = new EvalVisitor();
    return (given.accept(eval) >= 0)
        && given.left.accept(this)
        && given.right.accept(this);
  }
}

// Examples and tests for visitors and trees
class ExamplesVisitors<T> {
  
  // Examples of functions and BiFunctions
  Plus plus = new Plus();
  Minus minus = new Minus();
  Mul mul = new Mul();
  Div div = new Div();
  Neg neg = new Neg();
  Sqr sqr = new Sqr();
  
  // Examples of IArith's
  IArith twenty = new Const(20.0);
  IArith ten = new Const(10.0);
  IArith five = new Const(5.0);
  IArith one = new Const(1.0);
  IArith two = new Const(2.0);
  IArith zero = new Const(0.0);

  IArith tenPlusFive = new BinaryFormula(plus, "plus", ten, five);
  IArith twentyPlusTen = new BinaryFormula(plus, "plus", twenty, ten);
  IArith onePlusTenPlusFive = new BinaryFormula(plus, "plus", one, tenPlusFive);
  IArith fiveMinusTen = new BinaryFormula(minus, "minus", five, ten);
  IArith twentyPlusTenMinusTwo = new BinaryFormula(minus, "minus", this.twentyPlusTen, this.two);
  IArith fiveMinusOne = new BinaryFormula(minus, "minus", five, one);
  IArith tenMinusTwo = new BinaryFormula(minus, "minus", this.ten, this.two);
  IArith tenPlusFiveMinusOne = new BinaryFormula(minus, "minus", tenPlusFive, one);
  IArith fiveMulFive = new BinaryFormula(mul, "mul", five, five);
  IArith tenPlusFiveMinusOneMulTen = new BinaryFormula(mul, "mul", tenPlusFiveMinusOne, ten);
  IArith tenDivFive = new BinaryFormula(div, "div", ten, five);
  IArith fiveDivFiveMinusOne = new BinaryFormula(div, "div", five, fiveMinusOne);

  IArith negFive = new UnaryFormula(neg, "neg", five);
  IArith negFiveMinusOne = new UnaryFormula(neg, "neg", fiveMinusOne);
  IArith sqrTen = new UnaryFormula(sqr, "sqr", ten);
  IArith sqrFiveMulFive = new UnaryFormula(sqr, "sqr", fiveMulFive);

  IArithVisitor<Double> evalVisitor = new EvalVisitor();
  IArithVisitor<String> printVisitor = new PrintVisitor();
  IArithVisitor<IArith> doublerVisitor = new DoublerVisitor();
  IArithVisitor<Boolean> noNegVisitor = new NoNegativeResults();

  // tests the accept method
  boolean testAccept(Tester t) {
    return t.checkExpect(this.twenty.accept(evalVisitor), 20.0)
        && t.checkExpect(this.twenty.accept(printVisitor), "20.0")
        && t.checkExpect(this.twenty.accept(doublerVisitor), new Const(40.0))
        && t.checkExpect(this.twenty.accept(noNegVisitor), true)
        && t.checkExpect(this.tenPlusFive.accept(evalVisitor), 15.0)
        && t.checkExpect(this.tenPlusFive.accept(printVisitor), "(plus 10.0 5.0)")
        && t.checkExpect(this.tenPlusFive.accept(doublerVisitor), 
            new BinaryFormula(new Plus(), "plus", this.twenty, this.ten))
        && t.checkExpect(this.tenPlusFive.accept(noNegVisitor), true)
        && t.checkExpect(this.negFive.accept(evalVisitor), -5.0)
        && t.checkExpect(this.negFive.accept(printVisitor), ("(neg 5.0)"))
        && t.checkExpect(this.negFive.accept(doublerVisitor), 
            new UnaryFormula(new Neg(), "neg", this.ten))
        && t.checkExpect(this.sqrTen.accept(noNegVisitor), true);
  }

  // tests the apply method
  boolean testApply(Tester t) {
    return t.checkExpect(this.plus.apply(5.0, 10.0), 15.0)
        && t.checkExpect(this.minus.apply(5.0, 1.0), 4.0)
        && t.checkExpect(this.mul.apply(5.0, 5.0), 25.0)
        && t.checkExpect(this.div.apply(10.0, 5.0), 2.0)
        && t.checkExpect(this.neg.apply(5.0), -5.0)
        && t.checkExpect(this.sqr.apply(10.0), 100.0)
        && t.checkExpect(this.evalVisitor.apply(this.ten), 10.0)
        && t.checkExpect(this.evalVisitor.apply(this.tenPlusFive), 15.0)
        && t.checkExpect(this.evalVisitor.apply(this.tenPlusFiveMinusOneMulTen), 140.0)
        && t.checkExpect(this.evalVisitor.apply(this.negFive), -5.0)
        && t.checkExpect(this.printVisitor.apply(this.ten), "10.0")
        && t.checkExpect(this.printVisitor.apply(this.tenDivFive), "(div 10.0 5.0)")
        && t.checkExpect(this.printVisitor.apply(this.fiveDivFiveMinusOne),
            "(div 5.0 (minus 5.0 1.0))")
        && t.checkExpect(this.printVisitor.apply(this.sqrTen), "(sqr 10.0)")
        && t.checkExpect(this.doublerVisitor.apply(this.five), this.ten)
        && t.checkExpect(this.doublerVisitor.apply(this.fiveMinusOne), this.tenMinusTwo)
        && t.checkExpect(this.doublerVisitor.apply(this.tenPlusFiveMinusOne), 
            this.twentyPlusTenMinusTwo)
        && t.checkExpect(this.doublerVisitor.apply(this.negFive), 
            new UnaryFormula(new Neg(), "neg", this.ten))
        && t.checkExpect(this.noNegVisitor.apply(this.twenty), true)
        && t.checkExpect(this.noNegVisitor.apply(this.tenPlusFive), true)
        && t.checkExpect(this.noNegVisitor.apply(this.fiveMinusTen), false)
        && t.checkExpect(this.noNegVisitor.apply(this.negFive), false)
        && t.checkExpect(this.noNegVisitor.apply(this.sqrTen), true);
  }

  // tests the visitConst method
  boolean testVisitConst(Tester t) {
    return t.checkExpect(this.evalVisitor.visitConst((Const) this.five), 5.0)
        && t.checkExpect(this.evalVisitor.visitConst((Const) this.ten), 10.0)
        && t.checkExpect(this.printVisitor.visitConst((Const) this.one), "1.0")
        && t.checkExpect(this.printVisitor.visitConst((Const) this.zero), "0.0")
        && t.checkExpect(this.doublerVisitor.visitConst((Const) this.ten), this.twenty)
        && t.checkExpect(this.doublerVisitor.visitConst((Const) this.five), this.ten)
        && t.checkExpect(this.noNegVisitor.visitConst((Const) this.five), true)
        && t.checkExpect(this.noNegVisitor.visitConst((Const) this.twenty), true);
  }

  // tests the visitUnaryFormula method
  boolean testVisitUnaryFormula(Tester t) {
    return t.checkExpect(this.evalVisitor.visitUnaryFormula((UnaryFormula) this.negFive), -5.0)
        && t.checkExpect(this.evalVisitor.visitUnaryFormula((UnaryFormula)
            this.sqrFiveMulFive), 625.0)
        && t.checkExpect(this.printVisitor.visitUnaryFormula((UnaryFormula) this.sqrTen),
            "(sqr 10.0)")
        && t.checkExpect(this.printVisitor.visitUnaryFormula((UnaryFormula) this.negFiveMinusOne),
            "(neg (minus 5.0 1.0))")
        && t.checkExpect(this.doublerVisitor.visitUnaryFormula((UnaryFormula) this.negFive),
            new UnaryFormula(neg, "neg", this.ten))
        && t.checkExpect(this.doublerVisitor.visitUnaryFormula((UnaryFormula) this.sqrFiveMulFive),
            new UnaryFormula(sqr, "sqr", new BinaryFormula(mul, "mul", this.ten, this.ten)))
        && t.checkExpect(this.noNegVisitor.visitUnaryFormula((UnaryFormula) this.negFive), false)
        && t.checkExpect(this.noNegVisitor.visitUnaryFormula((UnaryFormula) this.sqrTen), true);
  }   

  // tests the visitBinaryFormula method
  boolean testVisitBinaryFormula(Tester t) {
    return t.checkExpect(this.evalVisitor.visitBinaryFormula((BinaryFormula)
        this.tenPlusFive),15.0)
        && t.checkExpect(this.evalVisitor.visitBinaryFormula((BinaryFormula)
            this.tenPlusFiveMinusOne), 14.0)
        && t.checkExpect(this.printVisitor.visitBinaryFormula((BinaryFormula)
            this.fiveMulFive), "(mul 5.0 5.0)")
        && t.checkExpect(this.printVisitor.visitBinaryFormula((BinaryFormula)
            this.onePlusTenPlusFive), 
            "(plus 1.0 (plus 10.0 5.0))")
        && t.checkExpect(this.doublerVisitor.visitBinaryFormula((BinaryFormula)
            this.tenPlusFive),
            new BinaryFormula(plus, "plus", this.twenty, this.ten))
        && t.checkExpect(this.doublerVisitor.visitBinaryFormula((BinaryFormula)
            this.fiveMulFive), 
            new BinaryFormula(mul, "mul", this.ten, this.ten))
        && t.checkExpect(this.noNegVisitor.visitBinaryFormula((BinaryFormula)
            this.tenPlusFive), true)
        && t.checkExpect(this.noNegVisitor.visitBinaryFormula((BinaryFormula)
            this.fiveMinusTen), false);
  }
}


