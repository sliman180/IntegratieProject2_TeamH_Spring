describe("From the home page", function(){

    beforeEach(function(){
        browser.get('http://localhost:8080');
        element(by.linkText("Afmelden")).click();
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

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/?#/cirkelsessies")

        //expect(element.all(by.css(".demo-card-wide")).count()).toEqual(1);
    });
});