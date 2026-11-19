package ui.tests;

import com.codeborne.selenide.testng.BrowserPerTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ui.pages.LoginPage;
import ui.pages.ProductsPage;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;

@Listeners(BrowserPerTest.class)
public class LoginTest {
    @Test
    public void standardUserCanLogIn() {
        ProductsPage productsPage = new LoginPage().open().login("standard_user", "secret_sauce");

        productsPage.title().shouldBe(visible).shouldHave(exactText("Products"));
        productsPage.products().shouldHave(sizeGreaterThan(0));
    }
}
