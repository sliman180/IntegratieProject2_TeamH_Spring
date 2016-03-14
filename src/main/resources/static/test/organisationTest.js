describe("On the organisations page",function(){
    beforeEach(function(){
        browser.get("http://teamh-spring.herokuapp.com/#/");
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Aanmelden')).click();
    });

    afterEach(function(){
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Afmelden')).click();
    });

    it("user should be able to create an organisation",function(){
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Aanmelden')).click();
        element(by.tagName("form")).element(by.id("gebruikersnaam")).sendKeys("user");
        element(by.tagName("form")).element(by.id("wachtwoord")).sendKeys("user");
        element(by.tagName("form")).element(by.tagName("button")).click();

        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Mijn organisaties')).click();

        //can't determine tag, div"Maak een organisatie" needs an id or a unique class
        element(by.id("form-title")).click();
        element(by.id("naam")).sendKeys("KdG");
        element(by.id("beschrijving")).sendKeys("Karel de Grote");

        //can't determine tag, button needs an id or a unique class
        element(by.linkText("Toevoegen")).click();

        expect(element.all(by.tagName("li")).count()).toBe(1);

    });
});