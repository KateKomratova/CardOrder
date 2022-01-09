package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static javax.swing.text.html.CSS.getAttribute;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardOrderInvalidTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendEmptyForm() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedTex = "Поле обязательно для заполнения";
        String actualTex = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals(expectedTex, actualTex);
    }

    @Test
    public void shouldSendFormInvalidName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Maria Klokova");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+78934793702");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedTex = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualTex = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals(expectedTex, actualTex);
    }

    @Test
    public void shouldSendFormInvalidNameWithNumber() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Мария Клокова 5678");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+78934793702");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedTex = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualTex = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals(expectedTex, actualTex);
    }

    @Test
    public void shouldSendFormInvalidPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Мария Клокова");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+789347937");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedTex = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualTex = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals(expectedTex, actualTex);
    }

    @Test
    public void shouldSendFormInvalidPhone2() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Мария Клокова");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("88934793702");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedTex = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualTex = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals(expectedTex, actualTex);
    }

    @Test
    public void shouldSendFormInvalidCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Мария Клокова");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79347937503");
        driver.findElement(By.cssSelector("button")).click();
        String colorString = driver.findElement(By.cssSelector(".input_invalid .checkbox__text")).getCssValue("color");
        assertTrue(colorString.equals("rgba(255, 92, 92, 1)"));
    }


}
