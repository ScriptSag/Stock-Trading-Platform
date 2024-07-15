import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();
    private double cashBalance;

    public Portfolio(double initialBalance) {
        this.cashBalance = initialBalance;
    }

    public void buyStock(Stock stock, int quantity) {
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        
        double totalCost = stock.getPrice() * quantity;
        if (totalCost > cashBalance) {
      System.out.println("Not enough cash to buy " + quantity + " shares of " + stock.getSymbol());
        } else {
  holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            cashBalance -= totalCost;
            System.out.println("Bought " + quantity + " shares of " + stock.getSymbol());
        }
    }

    public double getPortfolioValue(Map<String, Stock> marketData) {
        double value = cashBalance;
        for (String symbol : holdings.keySet()) {
            value += holdings.get(symbol) * marketData.get(symbol).getPrice();
        }
        return value;
    }

    public void printPortfolio() {
        System.out.println("Portfolio:");
        for (String symbol : holdings.keySet()) {
            System.out.println(symbol + ": " + holdings.get(symbol) + " shares");
        }
        System.out.println("Cash Balance: $" + cashBalance);
    }
}

class StockMarket {
    private Map<String, Stock> marketData = new HashMap<>();
    private Random random = new Random();

    public void addStock(String symbol, double price) {
        marketData.put(symbol, new Stock(symbol, price));
    }

    public Stock getStock(String symbol) {
        return marketData.get(symbol);
    }

    public void updatePrices() {
        for (Stock stock : marketData.values()) {
            double newPrice = stock.getPrice() * (1 + (random.nextDouble() - 0.5) / 10);
            stock.setPrice(newPrice);
        }
    }

    public void printMarketData() {
        System.out.println("Market Data:");
        for (Stock stock : marketData.values()) {
            System.out.println(stock.getSymbol() + ": $" + stock.getPrice());
        }
    }

    public Map<String, Stock> getMarketData() {
        return marketData;
    }
}

class StockTradingPlatform {
    public static void main(String[] args) {
        StockMarket market = new StockMarket();
        market.addStock("AAPL", 150.0);
        market.addStock("GOOGL", 2800.0);
        market.addStock("AMZN", 3400.0);

        Portfolio portfolio = new Portfolio(10000.0);

        Scanner scanner = new Scanner(System.in);
        boolean continueTrading = true;

        while (continueTrading) {
            System.out.println("Enter the stock symbol to buy:");
            String symbol = scanner.nextLine().toUpperCase();
            System.out.println("Enter the number of shares to buy:");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // consume newline

            market.printMarketData();
            portfolio.buyStock(market.getStock(symbol), quantity);

            System.out.println("Do you want to continue trading? (yes/no)");
            String response = scanner.nextLine().toLowerCase();
            continueTrading = response.equals("yes");
        }

        market.updatePrices();
        market.printMarketData();
        portfolio.printPortfolio();

        System.out.println("Portfolio Value: $" + portfolio.getPortfolioValue(market.getMarketData()));
    }
}

