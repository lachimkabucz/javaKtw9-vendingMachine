package pl.sdacademy.vending.model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * Vending Machine element, that can handle and manage (sell) products for specified price. Tray is identified by its symbol.
 */
public class Tray implements Serializable {
    public static final long serialVersionUID = 1L;
    public static final int MAX_SIZE = 10;

    private String symbol;
    private Long price;
    // queue preserves order of products that can be bought (first product that was placed in tray will be sold as first)
    private Queue<Product> products;

    private Tray(Builder builder) {
        symbol = builder.symbol;
        price = builder.price;
        products = builder.products;
    }

    public static Builder builder(String symbol) {
        return new Builder(symbol);
    }

    public boolean addProduct(Product product) {
        if (products.size() < MAX_SIZE) {
            return products.add(product);
        } else {
            return false;
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getPrice() {
        return price;
    }

    /**
     * Looks on first product in queue. If product is available, returns it wrapped into optional. In other case empty optional is returned.
     * Does not modify queue of products.
     * Can be used for displaying product name.
     */
    public Optional<String> firstProductName() {
        return Optional.ofNullable(products.peek()).map(Product::getName);
    }

    /**
     * Performs operation of buying product. Does not care, if there are products available, as it does not check money (it should be someone else responsibility).
     * Can be imagined like order to move queue - if there is nothing in queue, it will not move. If there is something in queue, it will be sold.
     * Wraps bought product into optional. If there was no product that can be bought, empty optional is returned.
     */
    public Optional<Product> buyProduct() {
        return Optional.ofNullable(products.poll());
    }

    public void updatePrice(Long price) {
        this.price = price;
    }

    public TraySnapshot snapshot(){
        return new TraySnapshot(
                symbol,
                price,
                firstProductName().orElse("--")
        );
    }


    public static class Builder {
        private String symbol;
        private Long price;
        private Queue<Product> products;

        private Builder(String symbol) {
            this.symbol = symbol;
            products = new ArrayDeque<>();
        }

        public Builder price(Long price) {
            this.price = price;
            return this;
        }

        public Builder product(Product product) {
            products.add(product);
            return this;
        }

        public Tray build() {
            if (price == null || price < 0) {
                price = 0L;
            }
            return new Tray(this);
        }
    }
}