package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductsPage {
    private final SelenideElement title = $("[data-test='title']");
    private final ElementsCollection products = $$("[data-test='inventory-item']");
    private final ElementsCollection productPrices = $$("[data-test='inventory-item-price']");
    private final ElementsCollection productNames = $$("[data-test='inventory-item-name']");
    private final SelenideElement sortDropdown = $("[data-test='product-sort-container']");
    private final SelenideElement cartLink = $("[data-test='shopping-cart-link']");

    public SelenideElement title() {
        return title;
    }

    public ElementsCollection products() {
        return products;
    }

    public ElementsCollection productPrices() {
        return productPrices;
    }

    public void sortByPriceLowToHigh() {
        sortDropdown.selectOptionByValue("lohi");
    }

    public void addProductToCart(String productName) {
        productNames.findBy(exactText(productName))
                .ancestor("[data-test=inventory-item]")
                .$("[data-test^='add-to-cart-']")
                .click();
    }

    public CartPage openCart() {
        cartLink.click();
        return new CartPage();
    }
}
