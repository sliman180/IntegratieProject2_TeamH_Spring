var AuthenticationPageObject = require("./authenticatiePageObject.js");

describe("On the home page", function(){

    var authPageObject;

    beforeEach(function(){
        authPageObject = new AuthenticationPageObject();
        browser.driver.manage().window().maximize();
    });

    afterEach(function(){
        authPageObject.logOff();
    });

    it("user should be able to login", function(){

        authPageObject.index();
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/");
        authPageObject.login("userone","userone");
        expect($(".nav-login-btn").isPresent()).toBe(false);

    });

});