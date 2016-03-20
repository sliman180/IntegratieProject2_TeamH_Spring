var RegistrationPageObject = require('./registratiePageObject.js');

describe("On the home page", function(){

    var regPageObj;

    beforeEach(function(){
        regPageObj = new RegistrationPageObject();
        browser.driver.manage().window().maximize();

    });

    it("user should be able to register", function(){

        regPageObj.index();
        regPageObj.register();
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/auth/login");

    });
});