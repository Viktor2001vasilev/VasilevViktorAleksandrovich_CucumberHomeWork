package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class stepsWithAvito {
    public ChromeDriver driver;
    public WebDriverWait explicitWait;
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/resources/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        WebDriverWait explicitWait = new WebDriverWait(driver, 2);
        this.driver = driver;
        this.explicitWait = explicitWait;
    }
    @After
    public void close() {
        driver.quit();
    }

    @Пусть("открыт ресурс авито")
    public void openSite() {
        driver.get("https://www.avito.ru/");
    }

    @ParameterType(".*")
    public Categories categories(String category) {
        return Categories.valueOf(category);
    }

    @И("в выпадающем списке категорий выбрана {categories}")
    public void selectCategory(Categories cat) {
        Select category = new Select(driver.findElement(By.xpath("//select[@name = 'category_id']")));
        category.selectByValue(cat.id);
    }

    @И("в поле поиска введено значение {word}")
    public void enterProductName(String product) {
        WebElement input = driver.findElement(By.xpath("//input[@placeholder = 'Поиск по объявлениям']"));
        input.sendKeys(product);
    }

    @Тогда("кликнуть по выпадающему списку региона")
    public void regionClick() {
        WebElement city = driver.findElement(By.xpath("//div[@data-marker = 'search-form/region']"));
        city.click();
    }


    @Тогда("в поле регион введено значение {word}")
    public void enterTheCity(String city) {
        WebElement cityInput = driver.findElement(By.xpath("//input[@data-marker = 'popup-location/region/input']"));
        cityInput.sendKeys(city);
        explicitWait.until(ExpectedConditions.textToBe(By.xpath("//li[@data-marker = 'suggest(0)']//strong"), city));
    }

    @И("нажата кнопа показать объявления")
    public void showGoods() {
        WebElement suggestedCityButton = driver.findElement(By.xpath("//button[@data-marker = 'popup-location/save-button']"));
        suggestedCityButton.click();
    }

    @Тогда("открылась страница результаты по запросу {word}")
    public void pageCheck(String product)  {
        if (product.equals("принтер"))
            explicitWait.until(ExpectedConditions.urlContains("%D0%BF%D1%80%D0%B8%D0%BD%D1%82%D0%B5%D1%80"));
        else driver.quit();
    }

    @И("активирован чекбокс только с фотографией")
    public void checkBoxCheck() {
        WebElement checkBox = driver.findElement(By.xpath("//span[text() = 'только с фото']"));
        if (!checkBox.isSelected())
            checkBox.click();
    }

    @ParameterType(".*")
    public ShowSelect showSelect(String select) {
        return ShowSelect.valueOf(select);
    }
    @И("в выпадающем списке сотрировка выбрано значение {showSelect}")
    public void showSelectClick(ShowSelect select) {
        Select showSelect = new Select(driver.findElement(By.xpath("//div[@class = 'sort-select-3QxXG select-select-box-3LBfK select-size-s-2gvAy']//select[@class = 'select-select-3CHiM']")));
        showSelect.selectByValue(select.id);
    }

    @И("в консоль выведено значение названия и цены {int} первых товаров")
    public void showItemList(int howMuchToShow) {
        List<WebElement> itemsList = driver.findElements(By.xpath("//div[@class = 'items-items-38oUm'][1]/div[@data-marker = 'item']"));
        for (int count = 0; count < howMuchToShow; count++) {
            System.out.println(itemsList.get(count).findElement(By.xpath(".//div[@class = 'iva-item-titleStep-2bjuh']")).getText() + " стоит " + itemsList.get(count).findElement(By.xpath(".//div[@class = 'iva-item-priceStep-2qRpg']")).getText());
        }
    }


}
