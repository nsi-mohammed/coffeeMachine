package coffeemachine.objects;

public class Menu
{
    public static final Menu HOT_CHOCOLATE =
            new Menu(
                    DrinkType.HOT_CHOCOLATE,
                    new Money(50));

    public static final Menu TEA =
            new Menu(
                    DrinkType.TEA,
                    new Money(40));

    public static final Menu COFFEE =
            new Menu(
                    DrinkType.COFFEE,
                    new Money(60));

    public static final Menu ORANGE_JUICE =
            new Menu(
                    DrinkType.ORANGE_JUICE,
                    new Money(60));

    private DrinkType drinkType;
    private Money price;

    public Menu(DrinkType drinkType, Money price) {
        this.drinkType = drinkType;
        this.price = price;
    }

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public Money getPrice() {
        return price;
    }
}
