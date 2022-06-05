import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class HomeWorkSelenideTest {

    @Before
    public void setup() {
        Configuration.browserSize = "1366x768";
    }

//    @RunWith(Parameterized.class)
//    public class FibonacciTest {
        @Parameter
        public static Object[] value() {
            return new Object[][]{{"first test", "second test"}};
        }
    //}

    @Test
    public void jysk_SearchSuggestion_Themes() {
        open("https://jysk.ua/");
        $(".coi-banner__summary").should(Condition.appear);
        $(By.xpath("//button[text()='Прийняти все']")).click();
        $(".coi-banner__summary").should(Condition.disappear);
        $(".search-input.form-control").shouldBe(Condition.empty).shouldHave(Condition.attribute("placeholder", "Шукати товар або категорію..."));
        $(".search-input.form-control").setValue("шафа");
        $(".header-search__results").should(Condition.appear);
        $(By.xpath("//div[text()='Як вибрати шафу?']")).click();
        $(".mt-0.mb-4").shouldHave(Condition.text("Як вибрати шафу?"));
    }

    @Test
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

    @Test
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

    @Test
    public void hillel_OpenCourseOfTesting() {
        open("https://ithillel.ua/");
        $(".cookie-ntf").shouldHave(Condition.attribute("class", "cookie-ntf -visible"));
        $(By.xpath("//button[text()='Прийняти']")).click();
        $("#form-lead-magnet > div > button").click();
        $(".block-course-cats_title").scrollTo();
        $("a[href='https://ithillel.ua/courses/testing']").click();
        $(".block-profession-heading_group").scrollTo();
        $(By.xpath("//*[@id='categories']/div[2]//li[2]")).click();
        $(By.xpath("//span[@class='course-descriptor_header-text']/strong")).shouldHave(Condition.text("QA Automation"));

    }

    @Ignore //@Disabled("skip it test, because necessary") - JUnit5
    public void rozetka_EmptyCart() {
        open("https://rozetka.com.ua/");
        $("button[opencart]").click();
        $(By.className("cart-dummy__heading")).shouldHave(Condition.text("Корзина пуста"));
        $(By.className("cart-dummy__caption")).shouldHave(Condition.text("Но это никогда не поздно исправить :)"));
    }

    @Test
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
