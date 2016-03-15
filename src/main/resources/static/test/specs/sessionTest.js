describe("From home page", function(){


    it("user should be able to create a session", function(){

        element(by.css(".nav-session-btn")).click();
        element(by.id("form-title")).element(by.tagName("h2")).click();

        element(by.id("naam")).sendKeys("Session one");
        element(by.id("aantalCirkels")).sendKeys(5);
        element(by.id("maxAantalKaarten")).sendKeys(15);
        element(by.cssContainingText(".sts-opt","OPEN")).click();
        element(by.id("isGesloten")).click();
        element(by.id("datum")).sendKeys("01/05/2016 15:15");
        element(by.cssContainingText(".sbt-opt","AO")).click();
        element(by.id("form")).element(by.tagName("button")).click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/?#/cirkelsessies");
        expect(element.all(by.css(".demo-card-wide")).count()).toBe(1);

    });



});