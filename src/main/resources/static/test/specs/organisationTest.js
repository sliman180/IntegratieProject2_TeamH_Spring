describe("On organisations page",function(){

    beforeEach(function(){
        browser.driver.manage().window().maximize();
        browser.get("http://localhost:8080/#/");
        element(by.css(".nav-login-btn")).click();
    });


    it("user should be able to create an organisation",function(){

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
        //--->Login end

        element(by.css(".nav-organisation-btn")).click();
        expect(element(by.css(".sh-org-form-btn")).getText()).toEqual("Maak een organisatie aan");
   /*     element(by.id("naam")).sendKeys("KdG");
        element(by.id("beschrijving")).sendKeys("Karel de Grote");
        element(by.css(".org-form-add-btn")).click();

        expect(element.all(by.css(".lijst-item")).count()).toBe(1);*/

    });
});