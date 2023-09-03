import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Main {
    static class Toy {
        private int id;
        private String name;
        private int quantity;
        private double weight;

        public Toy(int id, String name, int quantity, double weight) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.weight = weight;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public double getWeight() {
            return weight;
        }

        public void decrementQuantity() {
            if (this.quantity > 0) {
                this.quantity--;
            }
        }

        public Toy copy() {
            return new Toy(this.id, this.name, this.quantity, this.weight);
        }

        @Override
        public String toString() {
            return "Toy{" + "id=" + id + ", name='" + name + '\'' + ", quantity=" + quantity + ", weight=" + weight + '}';
        }
    }

    static class ToyRaffle {
        private List<Toy> toys = new ArrayList<>();
        private List<Toy> raffledToys = new ArrayList<>();

        public void addToy(Toy toy) {
            toys.add(toy);
        }

        public Toy raffleToy() {
            if (toys.isEmpty()) {
                return null;
            }

            double totalWeight = toys.stream().mapToDouble(Toy::getWeight).sum();
            double randomValue = new Random().nextDouble() * totalWeight;
            double accumulatedWeight = 0.0;

            for (Toy toy : toys) {
                accumulatedWeight += toy.getWeight();
                if (randomValue <= accumulatedWeight) {
                    Toy raffledToy = toy.copy();
                    toy.decrementQuantity();
                    if (toy.getQuantity() == 0) {
                        toys.remove(toy);
                    }
                    raffledToys.add(raffledToy);
                    return raffledToy;
                }
            }
            return null;
        }

        public void claimToy() throws IOException {
            if (raffledToys.isEmpty()) {
                return;
            }

            Toy toy = raffledToys.remove(0);
            writeToyToFile(toy);
        }

        private void writeToyToFile(Toy toy) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("raffled_toys.txt", true))) {
                writer.write(toy.toString());
                writer.newLine();
            }
        }

        @Override
        public String toString() {
            return "ToyRaffle{" + "toys=" + toys + '}';
        }
    }
    public static void main(String[] args) throws IOException {
        ToyRaffle raffle = new ToyRaffle();
        raffle.addToy(new Toy(1, "Teddy Bear", 10, 20.0));
        raffle.addToy(new Toy(2, "Car", 5, 30.0));
        raffle.addToy(new Toy(3, "Doll", 7, 50.0));

        raffle.raffleToy();
        //raffle.raffleToy();
        //raffle.raffleToy();

        raffle.claimToy();
        //raffle.claimToy();
        //raffle.claimToy();
    }
}