describe("From a home page", function(){
    beforeEach(function(){
        browser.driver.manage().window().maximize();
        browser.get("http://localhost:8080/#/");
        element(by.css(".nav-login-btn")).click();
    });

    it("user should be able to create a main theme",function(){
        element(by.id("gebruikersnaam")).sendKeys("user");
        element(by.id("wachtwoord")).sendKeys("user");
        element(by.css(".login-form")).element(by.css(".login-btn")).click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/");
        //--->Login end

        element(by.css(".nav-main-theme-btn")).click();
        element(by.id("form-title")).element(by.tagName("h2")).click();
        element(by.id("naam")).sendKeys("KdG");
        element(by.id("beschrijving")).sendKeys("Karel de Grote");
        element(by.cssContainingText("org-opt","KdG")).click();
        element(by.id("form")).element(by.tagName("button")).click();

        expect(element.all(by.css(".lijst-item")).count()).toBe(1);
    });
});