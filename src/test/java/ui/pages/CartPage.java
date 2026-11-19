package ui.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class CartPage {
    private final ElementsCollection productNames = $$("[data-test='cart-list'] [data-test='inventory-item-name']");

    public ElementsCollection productNames() {
        return productNames;
    }
}
