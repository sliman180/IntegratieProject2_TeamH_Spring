describe("On the home page", function(){
    var login_btn = element(by.css('.mdl-layout--large-screen-only')).element(by.linkText('Aanmelden'));

    var form = element(by.tagName("form"));
    var username_inpt = form.element(by.id("gebruikersnaam"));
    var passwd_inpt = form.element(by.id("wachtwoord"));
    var form_submit_btn = form.element(by.tagName("button"));

    function login(username, passwd){
        login_btn.click();
        username_inpt.sendKeys(username);
        passwd_inpt.sendKeys(passwd);
        form_submit_btn.click();
    }


    it("user should be able to login", function(){
        browser.get('http://localhost:8080');
        login("user","user");
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/");
    });
});