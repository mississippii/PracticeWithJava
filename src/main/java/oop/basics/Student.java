package oop.basics;

public class Student {
    private String name;
    private int age;
    private String gender;
    private String department;

    /** Default Constructor */
    public Student() {
        this.name = "Unknown";
        this.age = 0;
        this.gender = "Unknown";
        this.department = "Undeclared";
    }

    /** Parameterized Constructor */
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /** Constructor Overloading — adds gender on top of name + age */
    public Student(String name, int age, String gender) {
        this(name, age);          // Constructor chaining
        this.gender = gender;
    }

    /** Constructor Overloading — full details */
    public Student(String name, int age, String gender, String department) {
        this(name, age, gender);  // Constructor chaining
        this.department = department;
    }

    /** Copy Constructor */
    public Student(Student student) {
        this.name = student.name;
        this.age = student.age;
        this.gender = student.gender;
        this.department = student.department;
    }

    // Getters
    public String getName()       { return name; }
    public int getAge()           { return age; }
    public String getGender()     { return gender; }
    public String getDepartment() { return department; }

    // Setters
    public void setName(String name)           { this.name = name; }
    public void setAge(int age)                { this.age = age; }
    public void setGender(String gender)       { this.gender = gender; }
    public void setDepartment(String dept)     { this.department = dept; }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age
                + ", gender='" + gender + "', department='" + department + "'}";
    }

    /** --- Demo ---
    public static void main(String[] args) {
        // Default constructor
        Student s1 = new Student();
        System.out.println("Default  : " + s1);

        // Parameterized constructor
        Student s2 = new Student("Alice", 20);
        System.out.println("2-param  : " + s2);

        // Overloaded constructor (with gender)
        Student s3 = new Student("Bob", 22, "Male");
        System.out.println("3-param  : " + s3);

        // Overloaded constructor (full)
        Student s4 = new Student("Carol", 21, "Female", "CSE");
        System.out.println("4-param  : " + s4);

        // Copy constructor
        Student s5 = new Student(s4);
        System.out.println("Copy of s4: " + s5);
    }*/
}
