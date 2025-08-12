package shoppingTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class ShoppingBasket {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    //Test Data
    @DataProvider(name = "cartTestData")
    public Object[][] cartData() {
        return new Object[][] {
            { new String[]{"Apple"}, 35 },
            { new String[]{"Banana"}, 20 },
            { new String[]{"Melon", "Melon"}, 50 },
            { new String[]{"Lime", "Lime", "Lime"}, 30 },
            { new String[]{"Apple", "Apple", "Banana", "Melon", "Melon", "Lime", "Lime", "Lime"}, 170 }
        };
    }

    @Test(dataProvider = "cartTestData")
    public void testCartTotal(String[] items, int expectedTotal) {
    	
    	//Navigate to shopping cart page
        driver.get("https://<<Assuming URL of shopping cart>>");
       
        //Adding item's one by one to the cart from test data 
        for (String item : items) {
            WebElement input = driver.findElement(By.id("itemInput"));
            input.clear();
            input.sendKeys(item);
            driver.findElement(By.id("addItemBtn")).click();
        }
        
       //Clicking on calculate total button
       driver.findElement(By.id("calculateBtn")).click();
        
        
       //Get total from UI
        String totalText = driver.findElement(By.id("totalPrice")).getText();

      
        //Convert Pound to Paise
        int actualTotalPaise = convertToPaise(totalText);
        Assert.assertEquals(actualTotalPaise, expectedTotal, "Total price mismatch");
    }

    private int convertToPaise(String price) {
       
        price = price.replace("Rs", "");
        double rupees = Double.parseDouble(price);
        return (int) Math.round(rupees * 100);
    }

   
}

