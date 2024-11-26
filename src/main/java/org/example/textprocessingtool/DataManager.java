package org.example.textprocessingtool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataManager {

    private ArrayList<Person> personList;
    private Set<Person> personSet;
    private Map<String, Person> personMap;

    // Constructor to initialize the collections
    public DataManager() {
        personList = new ArrayList<>();
        personSet = new HashSet<>();
        personMap = new HashMap<>();
    }

    // Create a new person and add to all collections
    public void createPerson(String name, int age) {
        Person person = new Person(name, age);
        personList.add(person);
        personSet.add(person);
        personMap.put(name, person);
    }

    // Update a person's information by name
    public void updatePerson(String name, int newAge) {
        Person person = personMap.get(name);
        if (person != null) {
            person.setAge(newAge);
        } else {
            System.out.println("Person with name " + name + " not found.");
        }
    }

    // Delete a person by name
    public void deletePerson(String name) {
        Person person = personMap.remove(name);
        if (person != null) {
            personList.remove(person);
            personSet.remove(person);
            System.out.println("Deleted person: " + name);
        } else {
            System.out.println("Person with name " + name + " not found.");
        }
    }

    // Search for a person by name in the Map
    public Person searchPersonByName(String name) {
        return personMap.get(name);
    }

    // Get all persons in the ArrayList
    public ArrayList<Person> getAllPersons() {
        return personList;
    }

    // Get all unique persons in the Set (no duplicates)
    public Set<Person> getUniquePersons() {
        return personSet;
    }

    // Person class with equals and hashCode implemented
    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        // Override equals and hashCode for proper handling in Sets and Maps
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return name.equals(person.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + '}';
        }
    }

}
