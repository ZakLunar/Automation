import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DataProviderTest {

    @BeforeAll
    static void setup() {
        Configuration.browserSize = "1366x768";
        open("https://jysk.ua/");
        $(".coi-banner__summary").should(Condition.appear);
        $(By.xpath("//button[text()='Прийняти все']")).click();
        $(".coi-banner__summary").should(Condition.disappear);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Софа", "Декор"})
    public void jysk_SearchSuggestion_DataProvider(String value) {
        $(".search-input.form-control").shouldBe(Condition.empty).shouldHave(Condition.attribute("placeholder", "Шукати товар або категорію..."));
        $(".search-input.form-control").setValue(value);
        $(".header-search__results").should(Condition.appear);
        $(By.xpath("//*[@class='search-suggestion-list']/li[1]//li[1]")).click();
        $(".category-menu").scrollTo();
        $(".category-menu").shouldHave(Condition.text(value));
    }

}