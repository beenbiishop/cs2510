// Assignment 7, Problem 2
// Pooja Gidwani and Ben Bishop

import tester.Tester;

// Represents functions for with two arguments of type T and
// result type boolean
interface IFunc<T> {
  boolean apply(T input1, T input2);
}

// Represents functions for some argument type T and
//result type boolean
interface IFunc2<T> {
  boolean apply(T input1);
}

//Compares two students
class StudentComparator implements IFunc<Student> {
  public boolean apply(Student s1, Student s2) {
    return s1.name.equals(s2.name) && s1.id == s2.id;
  }
}

//Checks whether a course has a student enrolled in it
class HasStudent implements IFunc2<Course> {
  Student student;

  HasStudent(Student student) {
    this.student = student;
  }

  public boolean apply(Course c1) {
    return c1.hasStudent(this.student);
  }
}

// generic list
interface IList<T> {

  // Checks whether the given item is found in
  // this list, using the comparator passed in
  boolean isPresent(T item, IFunc<T> compare);

  // Checks whether at least count items in this list are present
  // in the given list, using the comparator passed in
  boolean multiple(int count, IFunc2<T> compare);

}

// empty generic list
class MtList<T> implements IList<T> {
  public boolean isPresent(T item, IFunc<T> compare) {
    return false;
  }

  public boolean multiple(int count, IFunc2<T> compare) {
    return false;
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

  public boolean isPresent(T item, IFunc<T> compare) {
    return compare.apply(this.first, item) 
        || this.rest.isPresent(item, compare);
  }

  public boolean multiple(int count, IFunc2<T> compare) {
    if (compare.apply(this.first) && count - 1 > 0) {
      return this.rest.multiple(count - 1, compare);
    }
    else if (compare.apply(this.first) && count - 1 == 0) {
      return true;
    }

    else {
      return this.rest.multiple(count, compare);
    }
  }
}

// Represents a Course
class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    this.prof.addCourse(this);
    this.students = new MtList<Student>();
  }

  // Adds the given student to this course's student list
  void enroll(Student s) {
    this.students = new ConsList<Student>(s, this.students);
  }

  //Checks whether a given student is in this course
  boolean hasStudent(Student s) {
    IFunc<Student> sc = new StudentComparator();
    return this.students.isPresent(s, sc);
  }
}

// Represents an instructor
class Instructor {
  String name;
  IList<Course> courses;

  Instructor(String name) {
    this.name = name;
    this.courses = new MtList<Course>();
  }

  // Adds a course onto this professor's course list
  void addCourse(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
  }

  // Returns true if this student is in at least two courses
  // with this professor
  boolean dejavu(Student s) {
    IFunc2<Course> cc = new HasStudent(s);
    return this.courses.multiple(2, cc);
  }
}

// Represents a student
class Student {
  String name;
  int id;
  IList<Course> courses;

  Student(String name, int id) {
    this.name = name;
    this.id = id;
    this.courses = new MtList<Course>();
  }

  void enroll(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
    c.enroll(this);
  }

  boolean classmates(Student s) {
    IFunc2<Course> cc = new HasStudent(s);
    return this.courses.multiple(1, cc);
  }
}

// Examples and tests for the Registrar classes
class ExamplesRegistrar {
  ExamplesRegistrar(){}

  Student ben;
  Student pooja;
  Student will;
  Student ash;
  Student riva;
  Student sonia;

  IList<Student> compSciStudents;
  IList<Student> artStudents;

  Course fundies;
  Course discrete;
  Course colorAndComp;
  Course formAndStruct;

  Instructor elena;
  Instructor brooke;

  IList<Course> compSciCourses;
  IList<Course> artCourses;
  IList<Course> combined;

  IFunc<Student> studentCompare;
  IFunc2<Course> hasPooja;
  IFunc2<Course> hasSonia;

  // Initializes all registrar elements in order to assure
  // they have the correct base values associated
  void initialize() {

    this.ben = new Student("Ben Bishop", 00211);
    this.pooja = new Student("Pooja Gidwani", 00212);
    this.will = new Student("Will Liu", 00213);
    this.ash = new Student("Ashley Lee", 002114);
    this.riva = new Student("Riva Shukla", 00215);
    this.sonia = new Student("Sonia Goyal", 00216);

    this.elena = new Instructor("Elena Strange");
    this.brooke = new Instructor("Brooke Stewart");

    this.compSciStudents = new ConsList<Student>(this.ben,
        new ConsList<Student>(this.pooja,
            new ConsList<Student>(this.will, new MtList<Student>())));
    this.artStudents = new ConsList<Student>(this.ben,
        new ConsList<Student>(this.pooja, new MtList<Student>()));

    this.discrete = new Course("Discrete Structures", this.elena);
    this.fundies = new Course("Fundies 1", this.elena);

    this.formAndStruct = new Course("Form and Structure", this.brooke);
    this.colorAndComp = new Course("Color and Composition", this.brooke);

    this.compSciCourses = new ConsList<Course>(this.fundies,
        new ConsList<Course>(this.discrete, new MtList<Course>()));
    this.artCourses = new ConsList<Course>(this.colorAndComp,
        new ConsList<Course>(this.formAndStruct, new MtList<Course>()));
    this.combined = new ConsList<Course>(this.fundies,
        new ConsList<Course>(this.discrete,
            new ConsList<Course>(this.colorAndComp,
                new ConsList<Course>(this.formAndStruct, new MtList<Course>()))));

    this.studentCompare = new StudentComparator();
    this.hasPooja = new HasStudent(this.pooja);
    this.hasSonia = new HasStudent(this.sonia);
  }

  // Enrolls students in their courses
  void enrollStudents() {
    this.pooja.enroll(this.formAndStruct);
    this.pooja.enroll(this.colorAndComp);
    this.pooja.enroll(this.discrete);
    this.pooja.enroll(this.fundies);

    this.ben.enroll(this.formAndStruct);
    this.ben.enroll(this.colorAndComp);
    this.ben.enroll(this.discrete);
    this.ben.enroll(this.fundies);

    this.will.enroll(this.discrete);
    this.will.enroll(this.fundies);

    this.ash.enroll(this.discrete);
    this.ash.enroll(this.fundies);

    this.riva.enroll(this.formAndStruct);
    this.riva.enroll(this.colorAndComp);

    this.sonia.enroll(this.discrete);
  }

  // Tests the enroll method
  void testEnroll(Tester t) {
    this.initialize();

    // before: pooja
    t.checkExpect(this.pooja.courses, new MtList<Course>());
    // method call: pooja
    this.pooja.enroll(this.formAndStruct);
    this.pooja.enroll(this.colorAndComp);
    this.pooja.enroll(this.discrete);
    this.pooja.enroll(this.fundies);
    // after: pooja
    t.checkExpect(this.pooja.courses, this.combined);

    // before: ben
    t.checkExpect(this.ben.courses, new MtList<Course>());
    // method call: ben
    this.ben.enroll(this.formAndStruct);
    this.ben.enroll(this.colorAndComp);
    this.ben.enroll(this.discrete);
    this.ben.enroll(this.fundies);
    // after: ben
    t.checkExpect(this.ben.courses, this.combined);

    // before: will
    t.checkExpect(this.will.courses, new MtList<Course>());
    // method call: will
    this.will.enroll(this.discrete);
    this.will.enroll(this.fundies);
    // after: will
    t.checkExpect(this.will.courses, this.compSciCourses);

    // before: ash
    t.checkExpect(this.ash.courses, new MtList<Course>());
    // method call: ash
    this.ash.enroll(this.discrete);
    this.ash.enroll(this.fundies);
    // after: ash
    t.checkExpect(this.ash.courses, this.compSciCourses);

    // before: riva
    t.checkExpect(this.riva.courses, new MtList<Course>());
    // method call: riva
    this.riva.enroll(this.formAndStruct);
    this.riva.enroll(this.colorAndComp);
    // after: riva
    t.checkExpect(this.riva.courses, this.artCourses);

    // before: sonia
    t.checkExpect(this.sonia.courses, new MtList<Course>());
    // method call: sonia
    this.sonia.enroll(this.discrete);
    // after: sonia
    t.checkExpect(sonia.courses,
        new ConsList<Course>(this.discrete, new MtList<Course>()));
  }

  //Tests the apply method when used in the Comparator classes
  boolean testApply1(Tester t) {
    this.initialize();
    this.enrollStudents();

    return t.checkExpect(this.studentCompare.apply(this.will, this.ash), false)
        && t.checkExpect(this.studentCompare.apply(this.will, this.will), true);
  }

  //Tests the apply method when used in the HasStudent class
  boolean testApply2(Tester t) {
    this.initialize();
    this.enrollStudents();

    return t.checkExpect(this.hasPooja.apply(this.fundies), true)
        && t.checkExpect(this.hasSonia.apply(this.discrete), true)
        && t.checkExpect(this.hasSonia.apply(this.colorAndComp), false);
  }

  //Tests the isPresent and multiple methods
  boolean testListMethods(Tester t) {
    this.initialize();
    this.enrollStudents();

    return t.checkExpect(this.fundies.students.isPresent(this.riva, studentCompare), false)
        && t.checkExpect(this.discrete.students.isPresent(this.pooja, studentCompare), true)
        && t.checkExpect(this.artCourses.multiple(0, hasSonia), false)
        && t.checkExpect(this.compSciCourses.multiple(1, hasPooja), true)
        && t.checkExpect(this.compSciCourses.multiple(2, hasPooja), true)
        && t.checkExpect(this.artCourses.multiple(2, hasSonia), false);
  }

  // Tests the classmates method
  boolean testClassmates(Tester t) {
    this.initialize();
    this.enrollStudents();

    return t.checkExpect(this.riva.classmates(this.will), false)
        && t.checkExpect(this.pooja.classmates(this.will), true)
        && t.checkExpect(this.ash.classmates(this.ben), true);
  }

  // Tests the dejavu method
  boolean testDejaVu(Tester t) {
    this.initialize();
    this.enrollStudents();

    return t.checkExpect(this.brooke.dejavu(this.will), false)
        && t.checkExpect(this.elena.dejavu(this.sonia), false)
        && t.checkExpect(this.elena.dejavu(this.ash), true)
        && t.checkExpect(this.elena.dejavu(this.sonia), false);
  }
}

