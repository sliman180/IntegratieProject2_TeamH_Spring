var AuthenticationPageObject = require('./authenticatiePageObject.js');

describe("On the home page", function(){

    var loginPage;

    beforeEach(function(){
        loginPage = new AuthenticationPageObject();
        browser.driver.manage().window().maximize();
    });

    afterEach(function(){
        loginPage.logOff();
    });

    it("user should be able to login", function(){

        loginPage.index();
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#/');
        loginPage.login();
        expect($('.nav-login-btn').isPresent()).toBe(false);

    });

});