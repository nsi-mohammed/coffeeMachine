package coffeemachine.adapter;

import coffeemachine.objects.Order;

public class OrderAdapter {

    public String adapt(Order o) {
        return codeDrink(o) + extraHot(o) +
                ":" + extractSugar(o) +
                ":" + putStick(o);
    }

    private String extraHot(Order o) {
        if (!o.isExtraHot()) {
            return "";
        }
        return "h";
    }

    private String codeDrink(Order order) {
        switch (order.getDrinkType()) {
            case TEA:
                return "T";
            case COFFEE:
                return "C";
            case HOT_CHOCOLATE:
                return "H";
            case ORANGE_JUICE:
                return "O";
            default:
                return "";
        }
    }

    private String extractSugar(Order order) {
        if (order.getSugarQuantity() == 0) {
            return "";
        }

        return String.valueOf(order.getSugarQuantity());
    }

    private String putStick(Order order) {
        if (order.getSugarQuantity() == 0) {
            return "";
        }

        return "0";
    }
}