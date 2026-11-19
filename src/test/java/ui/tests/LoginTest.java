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
    public void standardUserCanLogin() {
        ProductsPage productsPage = new LoginPage().open().login("standard_user", "secret_sauce");

        productsPage.title().shouldBe(visible).shouldHave(exactText("Products"));
        productsPage.products().shouldHave(sizeGreaterThan(0));
    }

    @Test
    public void rejectsInvalidCredentials() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.login("invalid_user", "invalid_password");

        loginPage.errorMessage().shouldBe(visible)
                .shouldHave(exactText("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test
    public void rejectsEmptyCredentials() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.login("", "");

        loginPage.errorMessage().shouldBe(visible)
                .shouldHave(exactText("Epic sadface: Username is required"));
    }
}
