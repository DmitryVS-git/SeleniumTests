import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class MainTestPage {
    private WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void thereIsAnErrorMessageAfterFailLogin(){
        String expectedErrorText = "Epic sadface: Username and password do not match any user in this service";

        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        WebElement errorContainer = driver.findElement(By.xpath("//div[contains(@class, 'error')]"));
        String actualErrorText = errorContainer.getText();

        Assert.assertEquals("Values are not equals",expectedErrorText, actualErrorText);
    }

    @Test
    public void successfulLogin(){
        String expectedResult = "https://www.saucedemo.com/inventory.html";

        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        String actualResult = driver.getCurrentUrl();

        Assert.assertEquals("You're not logged in", expectedResult, actualResult);
    }

    @Test
    public void successfullLogOut(){
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        boolean InventoryBlockDisplayed = driver.findElement(By.xpath("//div[@id='inventory_container']")).isDisplayed();
        Assert.assertTrue("There isn't an inventory block. You're not logged in", InventoryBlockDisplayed);

        driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
        driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();

        boolean expectedResult = driver.findElement(By.xpath("//input[@id='login-button']")).isDisplayed();
        Assert.assertTrue("There isn't a login btn. You're not logged out", expectedResult);
    }

    @Test
    public void AddingAProductToTheCart(){
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.xpath("//div[@id='shopping_cart_container']")).click();
        String actualCartQuantity = driver.findElement(By.xpath("//div[@class='cart_quantity']")).getText();

        Assert.assertEquals("Values in the cart are not equals", "1", actualCartQuantity);

    }

    @Test
    public void RemoveAProductFromTheCart(){
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.xpath("//div[@id='shopping_cart_container']")).click();

        driver.findElement(By.xpath("//button[@id='continue-shopping']")).click();
        driver.findElement(By.xpath("//button[@id='remove-sauce-labs-backpack']")).click();

        boolean isNotPresent = driver.findElements(By.xpath("//span[@class='shopping_cart_badge']")).size() > 0;

        Assert.assertFalse("Element is still present", isNotPresent);

    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
