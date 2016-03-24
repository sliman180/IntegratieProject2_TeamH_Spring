var AuthenticationPageObject = require('./authenticatiePageObject.js');

describe("After login in",function() {

    var loginPage;
    var loginBtn = $('.nav-login-btn');
    var logoffBtn = $('.nav-logoff-btn');

    beforeEach(function(){
        loginPage = new AuthenticationPageObject();
        browser.driver.manage().window().maximize();
    });

    it("user should be able to logoff",function(){

        loginPage.index();
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#/');

        loginPage.login();
        expect(loginBtn.isPresent()).toBe(false);

        loginPage.logOff();
        expect(logoffBtn.isPresent()).toBe(false);
        expect(loginBtn.isPresent()).toBe(true);

    })

});
