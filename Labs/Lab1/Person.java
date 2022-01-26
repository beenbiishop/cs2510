/*
+-----------------+
| Person          |
+-----------------+
| String name     |
| int age         |
| String gender   |
| Address address +---+
+-----------------+   |
                      |
                      |
                      v
                +---------------+
                | Address       |
                +---------------+
                | String city   |
                | String state  |
                +---------------+
 */

//to represent a person's address
class Address {
	String city;
	String state;

	// the constructor
	Address(String city, String state) {
		this.city = city;
		this.state = state;
	}
}

// to represent a person
class Person {
	String name;
	int age;
	String gender;
	Address address;

	// the constructor
	Person(String name, int age, String gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}
}

// examples for the classes that represent a person
class ExamplesPerson {
	ExamplesPerson() {}
	// Address examples
	Address boston = new Address("Boston", "MA");
	Address warwick = new Address("Warwick", "RI");
	Address nashua = new Address("Nashua", "NH");
	Address sanfran = new Address("San Francisco", "CA");

	// Person examples
	Person tim = new Person("Tim", 23, "Male", this.boston);
	Person kate = new Person("Kate", 22, "Female", this.warwick);
	Person rebecca = new Person("Rebecca", 31, "Non-binary", this.nashua);
	Person ben = new Person("Ben", 18, "Male", this.boston);
	Person pooja = new Person ("Pooja", 18, "Female", this.sanfran);
}

