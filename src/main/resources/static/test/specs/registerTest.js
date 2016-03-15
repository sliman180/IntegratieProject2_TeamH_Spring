describe("On the start page", function(){

    it("user should be able to register", function(){

        browser.get('http://localhost:8080');
        browser.driver.manage().window().maximize();

        element(by.css(".nav-register-btn")).click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/auth/register");

        element(by.id("gebruikersnaam")).sendKeys("georgy");
        element(by.id("wachtwoord")).sendKeys("georgy");
        element(by.css(".register-form")).element(by.tagName("button")).click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/auth/login");


    });
});