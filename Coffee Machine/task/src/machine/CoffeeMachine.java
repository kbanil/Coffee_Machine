package machine;

import java.util.Scanner;

public class CoffeeMachine {

    private static final Scanner scanner = new Scanner(System.in);
    private int waterQty;
    private int milkQty;
    private int coffeeBeansQty;
    private int cupsQty;
    private int money;
    private State state;

    public CoffeeMachine() {
        initialize();
    }

    public static void main(String[] args) {

        final CoffeeMachine coffeeMachine = new CoffeeMachine();

        while (true) {
            final String input = scanner.next();
            coffeeMachine.process(input);
        }
    }

    private void initialize() {
        final int initialWater = 400;
        final int initialMilk = 540;
        final int initialCoffeeBeans = 120;
        final int initialCups = 9;
        final int initialMoney = 550;
        fill(initialWater, initialMilk, initialCoffeeBeans, initialCups);
        addMoney(initialMoney);
        setState(State.CHOOSING_ACTION);
        System.out.println(state.getMessage());
    }

    public void process(String input) {
        switch (state) {
            case CHOOSING_ACTION:
                switch (input) {
                    case "buy":
                        setState(State.CHOOSING_COFFEE_VARIANT);
                        break;
                    case "fill":
                        setState(State.FILLING_WATER);
                        break;
                    case "take":
                        final int amount = take();
                        System.out.println("I gave you $" + amount);
                        break;
                    case "remaining":
                        System.out.println(this);
                        break;
                    case "exit":
                        setState(State.TURNED_OFF);
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again");
                }
                break;
            case CHOOSING_COFFEE_VARIANT:
                if (!"back".equalsIgnoreCase(input)) {
                    final int id = Integer.parseInt(input);
                    Coffee coffee = Coffee.parseInt(id);
                    buy(coffee);
                }
                setState(State.CHOOSING_ACTION);
                break;
            case FILLING_WATER:
                fillWater(Integer.parseInt(input));
                setState(State.FILLING_MILK);
                break;
            case FILLING_MILK:
                fillMilk(Integer.parseInt(input));
                setState(State.FILLING_COFFEE_BEANS);
                break;
            case FILLING_COFFEE_BEANS:
                fillCoffeeBeans(Integer.parseInt(input));
                setState(State.FILLING_CUPS);
                break;
            case FILLING_CUPS:
                fillDisposableCups(Integer.parseInt(input));
                setState(State.CHOOSING_ACTION);
                break;
            case TURNED_OFF:
                break;
        }
        System.out.println(state.getMessage());
    }

    private void setState(State state) {
        this.state = state;
    }

    private void addMoney(final int initialMoney) {
        this.money += initialMoney;
    }

    private void fillWater(final int waterQty) {
        this.waterQty += waterQty;
    }

    private void fillMilk(final int milkQty) {
        this.milkQty += milkQty;
    }

    private void fillCoffeeBeans(final int coffeeBeansQty) {
        this.coffeeBeansQty += coffeeBeansQty;
    }

    private void fillDisposableCups(final int cupsQty) {
        this.cupsQty += cupsQty;
    }

    public void fill(
            final int waterQty, final int milkQty, final int coffeeBeansQty, final int cupsQty) {
        fillWater(waterQty);
        fillMilk(milkQty);
        fillCoffeeBeans(coffeeBeansQty);
        fillDisposableCups(cupsQty);
    }

    private boolean hasEnoughWater(int numberOfCoffee, Coffee coffeeType) {
        if (waterQty >= (numberOfCoffee * coffeeType.water)) {
            return true;
        } else {
            System.out.println("Sorry, not enough water!");
            return false;
        }
    }

    private boolean hasEnoughMilk(int numberOfCoffee, Coffee coffeeType) {
        if (milkQty >= (numberOfCoffee * coffeeType.milk)) {
            return true;
        } else {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
    }

    private boolean hasEnoughCoffeeBeans(int numberOfCoffee, Coffee coffeeType) {
        if (coffeeBeansQty >= (numberOfCoffee * coffeeType.coffeeBeans)) {
            return true;
        } else {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
    }

    private boolean hasEnoughCups(int numberOfCoffee) {
        if (cupsQty >= numberOfCoffee) {
            return true;
        } else {
            System.out.println("Sorry, not enough cups!");
            return false;
        }
    }

    public boolean canMakeCoffee(int numberOfCoffee, Coffee coffeeType) {
        if (numberOfCoffee == 0) return true;
        return hasEnoughCups(numberOfCoffee)
                && hasEnoughWater(numberOfCoffee, coffeeType)
                && hasEnoughMilk(numberOfCoffee, coffeeType)
                && hasEnoughCoffeeBeans(numberOfCoffee, coffeeType);
    }

    public void buy(Coffee coffee) {
        if (canMakeCoffee(1, coffee)) {
            System.out.println("I have enough resources, making you a coffee!");
            waterQty -= coffee.water;
            milkQty -= coffee.milk;
            coffeeBeansQty -= coffee.coffeeBeans;
            cupsQty--;
            money += coffee.cost;
        }
    }

    public int take() {
        int amount = money;
        money = 0;
        return amount;
    }

    @Override
    public String toString() {
        String sb = "The coffee machine has:\n" +
                waterQty +
                " of water\n" +
                milkQty +
                " of milk\n" +
                coffeeBeansQty +
                " of coffee beans\n" +
                cupsQty +
                " of disposable cups\n" +
                "$" +
                money +
                " of money\n";
        return sb;
    }

    private enum Coffee {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPUCCINO(200, 100, 12, 6);

        final int water;
        final int milk;
        final int coffeeBeans;
        final int cost;

        Coffee(int water, int milk, int coffeeBeans, int cost) {
            this.water = water;
            this.milk = milk;
            this.coffeeBeans = coffeeBeans;
            this.cost = cost;
        }

        public static Coffee parseInt(int id) {
            switch (id) {
                case 1:
                    return ESPRESSO;
                case 2:
                    return LATTE;
                case 3:
                    return CAPUCCINO;
            }
            return null;
        }
    }

    private enum State {
        CHOOSING_ACTION("Write action (buy, fill, take, remaining, exit):"),
        CHOOSING_COFFEE_VARIANT("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"),
        FILLING_WATER("Write how many ml of water do you want to add:"),
        FILLING_MILK("Write how many ml of milk do you want to add:"),
        FILLING_COFFEE_BEANS("Write how many grams of coffee beans do you want to add:"),
        FILLING_CUPS("Write how many disposable cups of coffee do you want to add:"),
        TURNED_OFF("Machine is off!!");

        private final String message;

        State(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
