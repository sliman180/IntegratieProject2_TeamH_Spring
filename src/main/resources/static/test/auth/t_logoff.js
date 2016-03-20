var AuthenticationPageObject = require('./authenticatiePageObject.js');

describe("After login in",function() {

    var loginPage;

    beforeEach(function(){
        loginPage = new AuthenticationPageObject();
        browser.driver.manage().window().maximize();
    });

    it("user should be able to logoff",function(){

        loginPage.index();
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#/');
        loginPage.login();
        expect($('.nav-login-btn').isPresent()).toBe(false);
        loginPage.logOff();
        expect($('.nav-logoff-btn').isPresent()).toBe(false);
        expect($('.nav-login-btn').isPresent()).toBe(true);

    })

});