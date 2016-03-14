describe("After login in",function() {

    beforeEach(function () {
        browser.get("http://teamh-spring.herokuapp.com/#/");
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Aanmelden')).click();
    });

    afterEach(function () {
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Afmelden')).click();
    });

    it("user should be able to logoff",function(){
        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Aanmelden')).click();
        element(by.tagName("form")).element(by.id("gebruikersnaam")).sendKeys("user");
        element(by.tagName("form")).element(by.id("wachtwoord")).sendKeys("user");
        element(by.tagName("form")).element(by.tagName("button")).click();

        element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Afmelden')).click();

        //error: no such element-->tag needs an id or unique class
        expect(element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Aanmelden')).isPresent()).toBe(true);

    })

});