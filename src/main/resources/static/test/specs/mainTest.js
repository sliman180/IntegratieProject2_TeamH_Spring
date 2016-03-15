describe("From home page",function(){

    beforeEach(function(){
        browser.driver.manage().window().maximize();
    });

    it("test",function(){
        var navLinks = element(by.css(".header-nav")).all(by.tagName("a"));
        var isPresent = false;
        for(i = 0; i < navLinks.length; i++){
            if(navLinks[i] === "Aanmelden"){
                isPresent = true;
            }
        }
        expect(isPresent).toBe(false);
    });

    it("user should be able to create organisation",function(){

        element(by.css(".nav-organisation-btn")).click();
        element(by.id("form-title")).element(by.tagName("h2")).click();
        element(by.id("naam")).sendKeys("KdG");
        element(by.id("beschrijving")).sendKeys("Karel de Grote");
        element(by.id("form")).element(by.tagName("button")).click();

        expect(element.all(by.css(".lijst-item")).count()).toBe(1);
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/organisaties")
    });


    it("user should be able to create main theme and attache existing organisation to it",function(){

        element(by.css(".nav-main-theme-btn")).click();
        element(by.id("form-title")).element(by.tagName("h2")).click();
        element(by.id("naam")).sendKeys("HW");
        element(by.id("beschrijving")).sendKeys("Handelswetenschappen");
        element(by.cssContainingText(".org-opt","KdG")).click();
        element(by.id("form")).element(by.tagName("button")).click();

        expect(element.all(by.css(".lijst-item")).count()).toBe(1);
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/hoofdthemas")

    });

    it("user should be able to create subtheme and attache existing main theme to it",function(){

        element(by.css(".nav-subtheme-btn")).click();
        element(by.id("form-title")).element(by.tagName("h2")).click();
        element(by.id("naam")).sendKeys("AO");
        element(by.id("beschrijving")).sendKeys("Applicatieontwikkeling");
        element(by.cssContainingText(".mt-opt","AO"));
        element(by.id("form")).element(by.tagName("button")).click();

        expect(element.all(by.css(".lijst-item")).count()).toBe(1);
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/subthemas")

    });


});