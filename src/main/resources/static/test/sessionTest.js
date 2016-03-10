describe("On the sessions page", function(){

    beforeEach(function(){
        browser.get("http://teamh-spring.herokuapp.com/#/");
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Aanmelden')).click();
    });

    afterEach(function(){
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Afmelden')).click();
    });

    it("user should be able to create a session", function(){

        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Aanmelden')).click();
        element(by.tagName("form")).element(by.id("gebruikersnaam")).sendKeys("user");
        element(by.tagName("form")).element(by.id("wachtwoord")).sendKeys("user");
        element(by.tagName("form")).element(by.tagName("button")).click();

        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Cirkelsessies')).click();
        element(by.id("form-title")).click();

        element(by.tagName("form")).element(by.id("naam")).sendKeys("Session one");
        element(by.tagName("form")).element(by.id("aantalCirkels")).sendKeys(5);
        element(by.tagName("form")).element(by.id("maxAantalKaarten")).sendKeys(15);
        element(by.tagName("form")).element(by.id("isGesloten")).click();
        element(by.tagName("form")).element(by.id("datum")).sendKeys(01/05/2016);
        element(by.tagName("form")).element(by.tagName("button")).click();

        //verwachte url: http://teamh-spring.herokuapp.com/#/cirkelsessies
        //echte url: http://teamh-spring.herokuapp.com/?13=true#/cirkelsessies
        expect(browser.getCurrentUrl()).toEqual("http://teamh-spring.herokuapp.com/#/cirkelsessies");

        //er bestaan 3 sessies al maar na het aanmeken word de aangemaakte sessie niet toegevoegd
        expect(element.all(by.css(".demo-card-wide")).count()).toBe(4);

    });

    it("user should be able to take part in an existing session",function(){
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Cirkelsessies')).click();
        element(by.css(".demo-card-wide")).element(by.css(".mdl-card__actions")).element(by.tagName("a")).click();

        //TODO: test verder uitwerken, er nog geen implementatie
    });
});