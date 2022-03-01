import tester.*;

// Represents functions of signature A -> R, for some argument type A and
// result type R
interface IFunc<A, R> {
  R apply(A input);
}

// Multiplies a given integer by two.
class TimesTwo implements IFunc<Integer, Integer> {
  public Integer apply(Integer input) {
    return input * 2;
  }
}

// Represents functions of signature A1 A2 -> R, for some argument types A1
// and A2, and result type R
interface IFuncTwo<A1, A2, R> {
  R apply(A1 input1, A2 input2);
}

// Sums two given integers
class Sum implements IFuncTwo<Integer, Integer, Integer> {
  public Integer apply(Integer input1, Integer input2) {
    return input1 + input2;
  }
}

// Multiplies two given integers
class Product implements IFuncTwo<Integer, Integer, Integer> {
  public Integer apply(Integer input1, Integer input2) {
    return input1 * input2;
  }
}

// generic list
interface IList<T> {
  // map over a list, and produce a new list with a (possibly different)
  // element type
  <U> IList<U> map(IFunc<T, U> f);
  <U> U foldr(IFuncTwo<T, U, U> f, U baseCase);
}

// empty generic list
class MtList<T> implements IList<T> {
  public <U> IList<U> map(IFunc<T, U> f) {
    return new MtList<U>();
  }
  public <U> U foldr(IFuncTwo <T, U, U> f, U baseCase) {
    return baseCase;
  }
}

// non-empty generic list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }

  public <U> U foldr(IFuncTwo<T, U, U> f, U baseCase) {
    return f.apply(this.first, this.rest.foldr(f, baseCase));
  }
}

class ExamplesVisitors<T> {
  ExamplesVisitors() {}

  IList<Integer> emptyInt = new MtList<Integer>();

  IList<Integer> list1 = new ConsList<Integer>(1,
      new ConsList<Integer>(2,
          new ConsList<Integer>(3,
              new ConsList<Integer>(4,
                  new ConsList<Integer>(5,
                      emptyInt)))));
  IList<Integer> list1TimesTwo = new ConsList<Integer>(2,
      new ConsList<Integer>(4,
          new ConsList<Integer>(6,
              new ConsList<Integer>(8,
                  new ConsList<Integer>(10,
                      emptyInt)))));
  IFuncTwo<Integer, Integer, Integer> sum = new Sum();
  IFuncTwo<Integer, Integer, Integer> product = new Product();
  IFunc<Integer, Integer> timesTwo = new TimesTwo();

  boolean testFoldr(Tester t) {
    return t.checkExpect(this.list1.foldr(this.sum, 0), 15) &&
        t.checkExpect(this.list1.foldr(this.sum, 5), 20) &&
        t.checkExpect(this.list1.foldr(this.product, 1), 120);
  }

  boolean testMap(Tester t) {
    return t.checkExpect(this.list1.map(this.timesTwo),
        this.list1TimesTwo) &&
        t.checkExpect(this.emptyInt.map(this.timesTwo),
            this.emptyInt);
  }
}

