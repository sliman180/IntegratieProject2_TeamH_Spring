describe("From home page",function(){

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

    it("user shoud be able to create card(s) inside of a existing subtheme",function(){

        element(by.css(".lijst-content")).element(by.tagName("a")).click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/subthemas/details/1");

        element(by.id("kaartTekst")).sendKeys("Card");
        element(by.id("imageUrl")).sendKeys("imageUrl");
        element(by.css("form")).element(by.tagName("button")).click();

        expect(element.all(by.css(".kaart")).count()).toBe(1);

    });
});