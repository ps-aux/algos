package aux.ps.excercices.ctci.chapter3;


import org.junit.jupiter.api.Test;

import java.util.*;

import static aux.ps.excercices.ctci.chapter3.Cat.cat;
import static aux.ps.excercices.ctci.chapter3.Dog.dog;
import static org.assertj.core.api.Java6Assertions.assertThat;

interface AnimalShelterSpec {

    AnimalShellter sut();

    @Test
    default void test() {
        var s = sut();

        s.enque(cat(1));
        s.enque(cat(2));

        s.enque(dog(1));
        s.enque(dog(2));

        s.enque(cat(3));
        s.enque(dog(3));

        assertThat(dog(1)).isEqualTo(s.dequeDog());
        assertThat(cat(1)).isEqualTo(s.dequeAny());
        assertThat(cat(2)).isEqualTo(s.dequeAny());

        assertThat(dog(2)).isEqualTo(s.dequeAny());
        assertThat(cat(3)).isEqualTo(s.dequeCat());
        assertThat(dog(3)).isEqualTo(s.dequeAny());


    }
}


class AnimalShelter1Test implements AnimalShelterSpec {

    @Override
    public AnimalShellter sut() {
        return new AnimalShellter1();
    }
}

class AnimalShelter2Test implements AnimalShelterSpec {

    @Override
    public AnimalShellter sut() {
        return new AnimalShellter2();
    }
}

abstract class Animal {

    private int no;

    public Animal(int no) {
        this.no = no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return no == animal.no;
    }

    @Override
    public int hashCode() {
        return Objects.hash(no);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "no=" + no +
                '}';
    }
}


class Cat extends Animal {

    public Cat(int no) {
        super(no);
    }

    public static Cat cat(int n) {
        return new Cat(n);
    }

}

class Dog extends Animal {

    public Dog(int no) {
        super(no);
    }

    public static Dog dog(int n) {
        return new Dog(n);
    }
}


interface AnimalShellter {

    void enque(Animal a);

    Animal dequeAny();

    Dog dequeDog();

    Cat dequeCat();
}

class AnimalShellter1 implements AnimalShellter {

    private Deque<Animal> queue = new LinkedList<>();

    public void enque(Animal a) {
        queue.add(a);
    }

    @Override
    public Animal dequeAny() {
        return queue.removeFirst();

    }


    <A extends Animal> A dequeOfType(Class<A> clazz) {
        if (queue.peekFirst().getClass() == clazz)
            return clazz.cast(queue.removeFirst());

        var stack = new LinkedList<Animal>();

        while (queue.peekFirst().getClass() != clazz) {
            stack.push(queue.removeFirst());
        }

        var a = queue.removeFirst();


        while (!stack.isEmpty()) {
            queue.addFirst(stack.pop());

        }

        return clazz.cast(a);
    }

    @Override
    public Dog dequeDog() {
        return dequeOfType(Dog.class);
    }

    @Override
    public Cat dequeCat() {
        return dequeOfType(Cat.class);
    }

}


class AnimalShellter2 implements AnimalShellter {

    int seq = 0;

    private class Box<A extends Animal> {
        int no;
        A a;

        Box(A a, int no) {
            this.a = a;
            this.no = no;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "no=" + no +
                    ", a=" + a +
                    '}';
        }
    }

    private LinkedList<Box<Cat>> cats = new LinkedList<>();

    private LinkedList<Box<Dog>> dogs = new LinkedList<>();

    public void enque(Animal a) {
        if (a instanceof Cat) {
            cats.add(new Box<>((Cat) a, seq));
        } else if (a instanceof Dog) {
            dogs.add(new Box<>((Dog) a, seq));
        } else {
            throw new IllegalArgumentException();
        }
        seq++;
    }

    @Override
    public Animal dequeAny() {
        if (dogs.isEmpty() && cats.isEmpty()) {
            throw new NoSuchElementException();
        }

        Box<Dog> d = dogs.peekFirst();
        Box<Cat> c = cats.peekFirst();

        if (d == null) {
            return cats.removeFirst().a;
        } else if (c == null) {
            return dogs.removeFirst().a;
        } else if (d.no < c.no) {
            return dogs.removeFirst().a;
        } else {
            return cats.removeFirst().a;
        }
    }

    @Override
    public Dog dequeDog() {
        return dogs.removeFirst().a;
    }

    @Override
    public Cat dequeCat() {
        return cats.removeFirst().a;
    }

}


