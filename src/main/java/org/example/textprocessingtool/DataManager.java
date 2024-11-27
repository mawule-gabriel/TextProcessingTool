package org.example.textprocessingtool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The DataManager class manages a collection of Person objects.
 * It provides methods to create, update, delete, and search for persons in three collections:
 * an ArrayList, a Set, and a Map.
 */
public class DataManager {

    private ArrayList<Person> personList;
    private Set<Person> personSet;
    private Map<String, Person> personMap;


    /**
     * Constructor to initialize the collections.
     * Initializes the ArrayList, HashSet, and HashMap for storing Person objects.
     */
    public DataManager() {
        personList = new ArrayList<>();
        personSet = new HashSet<>();
        personMap = new HashMap<>();
    }

    /**
     * Creates a new Person and adds it to all collections (ArrayList, HashSet, and HashMap).
     *
     * @param name The name of the person.
     * @param age  The age of the person.
     */
    public void createPerson(String name, int age) {
        Person person = new Person(name, age);
        personList.add(person);
        personSet.add(person);
        personMap.put(name, person);
    }


    /**
     * Updates the age of an existing Person identified by name.
     *
     * @param name   The name of the person to update.
     * @param newAge The new age to set for the person.
     */
    public void updatePerson(String name, int newAge) {
        Person person = personMap.get(name);
        if (person != null) {
            person.setAge(newAge);
        } else {
            System.out.println("Person with name " + name + " not found.");
        }
    }


    /**
     * Deletes a Person identified by name from all collections (ArrayList, HashSet, and HashMap).
     *
     * @param name The name of the person to delete.
     */
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

    /**
     * Searches for a Person by name in the Map.
     *
     * @param name The name of the person to search for.
     * @return The Person object if found, otherwise null.
     */
    public Person searchPersonByName(String name) {
        return personMap.get(name);
    }


    /**
     * Retrieves all persons in the ArrayList.
     *
     * @return An ArrayList containing all the persons.
     */
    public ArrayList<Person> getAllPersons() {
        return personList;
    }


    /**
     * Retrieves all unique persons in the Set (no duplicates).
     *
     * @return A Set containing all unique persons.
     */
    public Set<Person> getUniquePersons() {
        return personSet;
    }

    /**
     * The Person class represents an individual with a name and age.
     * It overrides equals() and hashCode() to ensure proper handling in Sets and Maps.
     */
    static class Person {
        private String name;
        private int age;

        /**
         * Constructor to initialize a Person object with a name and age.
         *
         * @param name The name of the person.
         * @param age  The age of the person.
         */
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }


        /**
         * Gets the name of the person.
         *
         * @return The name of the person.
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the age of the person.
         *
         * @return The age of the person.
         */
        public int getAge() {
            return age;
        }


        /**
         * Sets the age of the person.
         *
         * @param age The new age to set.
         */
        public void setAge(int age) {
            this.age = age;
        }

        /**
         * Checks if two Person objects are equal by comparing their names.
         *
         * @param o The object to compare to.
         * @return true if the names are equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return name.equals(person.name);
        }

        /**
         * Generates a hash code based on the name of the person.
         *
         * @return The hash code of the person.
         */
        @Override
        public int hashCode() {
            return name.hashCode();
        }

        /**
         * Returns a string representation of the Person object.
         *
         * @return A string representation of the person.
         */
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + '}';
        }
    }

}
