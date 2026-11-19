package ui.tests;

import com.codeborne.selenide.testng.BrowserPerTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ui.pages.CartPage;
import ui.pages.LoginPage;
import ui.pages.ProductsPage;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;

@Listeners(BrowserPerTest.class)
public class ProductCartTest {
    @Test
    public void standardUserCanSortProductsAndAddBackpackToCart() {
        ProductsPage productsPage = new LoginPage().open().login("standard_user", "secret_sauce");
        productsPage.title().shouldBe(visible).shouldHave(exactText("Products"));

        List<String> currentPrices = productsPage.productPrices().shouldHave(sizeGreaterThan(0)).texts();
        List<String> expectedPrices = currentPrices.stream()
                .sorted(Comparator.comparing(price -> new BigDecimal(price.substring(1))))
                .toList();

        productsPage.sortByPriceLowToHigh();
        productsPage.productPrices().shouldHave(exactTexts(expectedPrices));

        String productName = "Sauce Labs Backpack";
        productsPage.addProductToCart(productName);
        CartPage cartPage = productsPage.openCart();
        cartPage.productNames().findBy(exactText(productName)).shouldBe(visible);
    }
}
