describe("On the home page", function(){

    var register_btn = element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Registreren'));

    var form = element(by.tagName("form"));
    var username_inpt = form.element(by.id("gebruikersnaam"));
    var passwd_inpt = form.element(by.id("wachtwoord"));
    var form_submit_btn = form.element(by.tagName("button"));

    function register(username, passwd){
        register_btn.click();
        username_inpt.sendKeys(username);
        passwd_inpt.sendKeys(passwd);
        form_submit_btn.click();
    }

    it("user should be able to register", function(){
        browser.get('http://localhost:8080');
        register("kdoo","kdoo");
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/auth/login");
    });
});