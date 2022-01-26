/*
            +-----------+
            | IIceCream |
            +-----------+
            +-----------+
                  |
                 / \
                 ---
                  |
       -----------------------
       |                     |
+--------------+    +----------------+
| EmptyServing |    | Scooped        |
+--------------+    +----------------+
| boolean cone |    | IIceCream more |
+--------------+    | String flavor  |
                    +----------------+
 */

// to represent an ice cream serving
interface IIceCream{}


// to represent an empty serving of ice cream
class EmptyServing implements IIceCream {
  boolean cone;

  // the constructor
  EmptyServing(boolean cone) {
    this.cone = cone;
  }
}


// to represent a scoop of ice cream
class Scooped implements IIceCream {
  IIceCream more;
  String flavor;

  // the constructor
  Scooped(IIceCream more, String flavor) {
    this.more = more;
    this.flavor = flavor;
  }
}


// examples for the classes that represent servings of ice cream
class ExamplesIceCream {
  ExamplesIceCream() {}

  // Order 1 scoops
  IIceCream emptyCup = new EmptyServing(false);
  IIceCream caramelSwirl = new Scooped(this.emptyCup, "caramel swirl");
  IIceCream blackRaspberry = new Scooped(this.caramelSwirl, "black raspberry");
  IIceCream coffee = new Scooped(this.blackRaspberry, "coffee");
  IIceCream order1 = new Scooped(this.coffee, "mint chip");

  // Order 2 scoops
  IIceCream emptyCone = new EmptyServing(true);
  IIceCream strawberry = new Scooped(this.emptyCone, "strawberry");
  IIceCream vanilla = new Scooped(this.strawberry, "vanilla");
  IIceCream order2 = new Scooped(this.vanilla, "chocolate");
}

