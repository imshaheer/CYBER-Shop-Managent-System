import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price;
    }
}

class ProductManager {
    private List<Product> products;
    private File file;

    public ProductManager(String filename) {
        products = new ArrayList<>();
        file = new File(filename);

        if (file.exists()) {
            loadProducts();
        }
    }

    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
        System.out.println("Product added successfully.");
    }

    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("List of Products:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    public void updateProduct(int productId, String newName, double newPrice) {
        for (Product product : products) {
            if (product.getId() == productId) {
                product.setName(newName);
                product.setPrice(newPrice);
                saveProducts();
                System.out.println("Product updated successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public void deleteProduct(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                products.remove(product);
                saveProducts();
                System.out.println("Product deleted successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private void loadProducts() {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                Product product = new Product(id, name, price);
                products.add(product);
            }
            System.out.println("Products loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    private void saveProducts() {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Product product : products) {
                writer.println(product.getId() + "," + product.getName() + "," + product.getPrice());
            }
            System.out.println("Products saved successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }
}