var AuthenticationPageObject = require("./authenticatiePageObject.js");

describe("After login in",function() {

    var authPageObject;

    beforeEach(function(){
        authPageObject = new AuthenticationPageObject();
        browser.driver.manage().window().maximize();
    });

    it("user should be able to logoff",function(){

        authPageObject.index();
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/");
        authPageObject.login();
        expect($(".nav-login-btn").isPresent()).toBe(false);
        authPageObject.logOff();
        expect($(".nav-logoff-btn").isPresent()).toBe(false);
        expect($(".nav-login-btn").isPresent()).toBe(true);

    })

});