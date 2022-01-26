// to represent a deli menu
interface DeliMenu {
}

// to represent a soup
class Soup implements DeliMenu {
	String name;
	int price;
	boolean isVeggie;

	// the constructor
	Soup(String name, int price, boolean isVeggie) {
		this.name = name;
		this.price = price;
		this.veggie = veggie;
	}
}

// to represent a salad
class Salad implements DeliMenu {
	String name;
	int price;
	boolean isVeggie;
	String dressing;

	// the constructor
	Salad(String name, int price, boolean isVeggie, String dressing) {
		this.name = name;
		this.price = price;
		this.isVeggie = isVeggie;
		this.dressing = dressing;
	}
}

//to represent a sandwich
class Sandwich implements DeliMenu {
	String name;
	int price;
	boolean isVeggie;
	String bread;
	String filling1;
	String filling2;

	// the constructor
	Sandwich(String name, int price, String bread, String filling1, String filling2) {
		this.name = name;
		this.price = price;
		this.bread = bread;
		this.filling1 = filling1;
		this.filling2 = filling2;
	}
}

// examples for the classes that represent a menu item
class MenuItemExamples {
	MenuItemExamples() {}

	// Menu item examples
	DeliMenu tomato = new Soup("Tomato", 3.99, true);
	DeliMenu potato = new Soup("Potato", 4.99, true);
	DeliMenu cobb = new Salad("Cobb", 14.99, false, "Blue Cheese");
	DeliMenu caesar = new Salad("Caesar", 11.99, true, "Ranch");
	DeliMenu italian = new Sandwich("Spicy Italian", 12.99, "Wheat", "Salami", "Lettuce");
	DeliMenu pbj = new Sandwich("Peanut Butter & Jelly", 5.99, "Rye", "Peanut Butter", "Jelly");
}


