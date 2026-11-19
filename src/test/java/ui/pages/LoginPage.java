package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameInput = $("[data-test='username']");
    private final SelenideElement passwordInput = $("[data-test='password']");
    private final SelenideElement loginButton = $("[data-test='login-button']");
    private final SelenideElement errorMessage = $("[data-test='error']");

    public LoginPage open() {
        Selenide.open("/");
        return this;
    }

    public ProductsPage login(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        return new ProductsPage();
    }

    public SelenideElement errorMessage() {
        return errorMessage;
    }
}
