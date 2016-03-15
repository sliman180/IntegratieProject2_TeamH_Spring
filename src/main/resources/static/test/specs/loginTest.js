describe("On the home page", function(){

    it("user should be able to login", function(){

        browser.driver.manage().window().maximize();
        browser.get("http://localhost:8080/#/");
        element(by.css(".nav-login-btn")).click();

        element(by.id("gebruikersnaam")).sendKeys("user");
        element(by.id("wachtwoord")).sendKeys("user");
        element(by.css(".login-form")).element(by.css(".login-btn")).click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/");

        var navLinks = element(by.css(".header-nav")).all(by.tagName("a"));
        var isPresent = false;
        for(i = 0; i < navLinks.length; i++){
            if(navLinks[i] === "Aanmelden"){
                isPresent = true;
            }
        }
        expect(isPresent).toBe(false);

        element(by.css(".nav-logoff-btn")).click();

    });
});