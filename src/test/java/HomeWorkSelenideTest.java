import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

//Не зміг використати для Selenide
///////////////////////////////////////////////////////
////////- waitUntil(Condition, milliseconds)
////////- waitWhile(Condition, milliseconds)
///////////////////////////////////////////////////////

public class HomeWorkSelenideTest {

    @BeforeMethod
    public void setUp() {
        Configuration.browserSize = "1366x768";
    }

    @AfterMethod
    public void waitAtTheEnd() throws InterruptedException {
        Thread.sleep(2000);
    }

    @BeforeGroups("search.jysk")
    public void setUpForGroup_jysk() {
        open("https://jysk.ua/");
        $(".coi-banner__summary").should(Condition.appear);
        $(By.xpath("//button[text()='Прийняти все']")).click();
        $(".coi-banner__summary").should(Condition.disappear);
        System.out.println("BeforeGroups method executed for search.jysk");//info message
    }

    @DataProvider
    public Object[][] dataProviderTest(){
        return new Object[][]{{"Софа"},{"Декор"}};
    }
    /////////////////// TEST ///////////////////

    @Test(groups = {"search.jysk"})
    public void jysk_SearchSuggestion_Themes() {
        $(".search-input.form-control").shouldBe(Condition.empty).shouldHave(Condition.attribute("placeholder", "Шукати товар або категорію..."));
        $(".search-input.form-control").setValue("шафа");
        $(".header-search__results").should(Condition.appear);
        $(By.xpath("//div[text()='Як вибрати шафу?']")).click();
        $(".mt-0.mb-4").shouldHave(Condition.text("Як вибрати шафу?"));
    }

    @Test(groups = {"search.jysk"}, dataProvider = "dataProviderTest") //Data Provider
    public void jysk_SearchSuggestion_DataProvider(String value) {
        $(".search-input.form-control").shouldBe(Condition.empty).shouldHave(Condition.attribute("placeholder", "Шукати товар або категорію..."));
        $(".search-input.form-control").setValue(value);
        $(".header-search__results").should(Condition.appear);
        $(By.xpath("//*[@class='search-suggestion-list']/li[1]//li[1]")).click();
        $(".category-menu").scrollTo();
        $(".category-menu").shouldHave(Condition.text(value));
    }

    @Test(priority = 1, groups = {"search.np"})
    public void novaPoshta_SearchByNumberOfOffice() {
        open("https://novaposhta.ua/");
        $(By.xpath("//*[@id='top_menu']/li[5]")).hover();
        $("a[href='/office/list']").click();
        $("#oCityArrow").click();
        $("#o_cities").should(Condition.appear);
        $(By.xpath("//span[text()='м. Київ, Київська обл.']")).click();
        $("#oWarehouseFilter").setValue("41");
        $("#o_warehouses").should(Condition.appear);
        $(By.xpath("//*[@id='o_warehouses']/li[1]")).click();
        $("a[href='/office/view/id/41/city/Київ']").click();
        $(".lowercase").shouldHave(Condition.text("Відділення №41"));
    }

    @Test(priority = 2, groups = {"filter.dou"})
    public void dou_FilterSalaryByPositionAtKiev() {
        open("https://dou.ua/");
        $("a[href='https://jobs.dou.ua/salaries/']").click();
        $("#dou-widget-salary").shouldBe(Condition.visible);
        $("#dd-position .labels .value").shouldHave(Condition.text("Middle Software Engineer"));
        $("#dd-position").click();
        $("#dd-position .options-list").shouldBe(Condition.visible);
        $(By.xpath("//div[text()='Junior QA']")).click();
        $("#dd-position").shouldHave(Condition.cssClass("drop-down"));
        $("#dd-position .labels .value").shouldHave(Condition.text("Junior QA"));
        $("#button-filters").click();
        $("#dws-filters").shouldHave(Condition.cssClass("expand"));
        $(By.xpath("//div[text()='Automation QA']")).click();
        $(By.xpath("//div[text()='Automation QA']")).shouldBe(Condition.enabled);
        $(By.xpath("//div[text()='Київ']")).click();
        $(By.xpath("//div[text()='Київ']")).shouldBe(Condition.enabled);
        $("#dws-fl-close").click();
        $("#dws-filters").shouldNotHave(Condition.cssClass("expand"));
        $(".query").should(Condition.appear);
    }

    @Test(priority = 3, groups = {"course.hillel"}, enabled = false)
    public void hillel_OpenCourseOfTesting() {
        open("https://ithillel.ua/");
        $(".cookie-ntf").shouldBe(Condition.visible);
        $(By.xpath("//button[text()='Прийняти']")).click();
        $(".cookie-ntf").shouldNotHave(Condition.attribute("class", "-visible"));
        $("#form-lead-magnet > div > button").click();
        $(".block-course-cats_title").scrollTo();
        $("a[href='https://ithillel.ua/courses/testing']").click();
        $(".block-profession-heading_group").scrollTo();
        $(By.xpath("//*[@id='categories']/div[2]//li[2]")).click();
        $(By.xpath("//span[@class='course-descriptor_header-text']/strong")).shouldHave(Condition.text("QA Automation"));

    }
    //before group --->
    @BeforeGroups("c.hillel")
    public void setUpForHillel(){
        open("https://ithillel.ua/");
        $(".cookie-ntf").shouldBe(Condition.visible);
        $(By.xpath("//button[text()='Прийняти']")).click();
        $(".cookie-ntf").shouldNotHave(Condition.attribute("class", "-visible"));
        $("#form-lead-magnet > div > button").click();
    }
    //For invocation count
    @Test(priority = 3, groups = {"c.hillel"}, invocationCount = 2)
    public void hillel_OpenCourseOfTesting_Clone() {
        $(".block-course-cats_title").scrollTo();
        $("a[href='https://ithillel.ua/courses/testing']").click();
        $(".block-profession-heading_group").scrollTo();
        $(By.xpath("//*[@id='categories']/div[2]//li[2]")).click();
        $(By.xpath("//span[@class='course-descriptor_header-text']/strong")).shouldHave(Condition.text("QA Automation"));
        $(".site-nav_logo").click();
    }

    @Test(priority = 4, groups = {"negative_test.cart"}, enabled = false)
    public void rozetka_EmptyCart() {
        open("https://rozetka.com.ua/");
        $("button[opencart]").click();
        $(By.className("cart-dummy__heading")).shouldHave(Condition.text("Корзина пуста"));
        $(By.className("cart-dummy__caption")).shouldHave(Condition.text("Но это никогда не поздно исправить :)"));
    }

    @Test(priority = 5, groups = {"dns.read"})
    public void dns_ReadStory() {
        open("https://howdns.works/");
        $(By.className("btn")).click();
        $("header span").shouldHave(Condition.text("Episode 1"));
        $("header h1").shouldHave(Condition.text("The website is unknown"));
        $(By.className("next-ep")).scrollTo();
        $(By.className("btn")).click();
        $("header span").shouldHave(Condition.text("Episode 2"));
        $("header h1").shouldHave(Condition.text("Road trip"));
        $(By.className("next-ep")).scrollTo();
        $(By.className("btn")).click();
        $("header span").shouldHave(Condition.text("Episode 3"));
        $("header h1").shouldHave(Condition.text("Top of the hierarchy"));
        $(By.className("next-ep")).scrollTo();
        $(By.className("btn")).click();
        $("header span").shouldHave(Condition.text("Episode 4"));
        $("header h1").shouldHave(Condition.text(".HOT .PIZZA .COM"));
        $(By.className("next-ep")).scrollTo();
        $(By.className("btn")).click();
        $("header span").shouldHave(Condition.text("Episode 5"));
        $("header h1").shouldHave(Condition.text("Respect my authority!"));
        $(By.className("next-ep")).scrollTo();
        $(By.className("btn")).click();
        $("header span").shouldHave(Condition.text("Episode 6"));
        $("header h1").shouldHave(Condition.text("It's getting late"));
        $(By.className("bonus-ep")).scrollTo();
        $(".btn").shouldHave(Condition.text("Read Bonus Episode"));
        $(byText("Read Bonus Episode")).click(); //byText
        $("header span").shouldHave(Condition.text("Episode 7"));
        $("header h1").shouldHave(Condition.text("Bonus: Glue records"));
        $(By.className("next-ep")).scrollTo();
    }
}
