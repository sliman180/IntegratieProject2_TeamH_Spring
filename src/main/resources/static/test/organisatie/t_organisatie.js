var OrganisationPageObject = require('./organisatiePageObject.js');
var AuthenticationPageObject = require('../auth/authenticatiePageObject.js');

describe("From a organisation page",function(){

    var orgPageObj;
    var authPageObj;

    beforeEach(function(){
        browser.driver.manage().window().maximize();
        authPageObj = new AuthenticationPageObject();
        authPageObj.index();
        authPageObj.login();
        orgPageObj = new OrganisationPageObject();
        orgPageObj.toOrganisationPage();
    });

    afterEach(function(){
        authPageObj.logOff();
    });

    it("user should be able to create a organisation",function(){
        orgPageObj.create();
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/organisaties");
        expect(element.all(by.repeater('organisatie in vm.organisaties')).count()).toBe(1);
        orgPageObj.delete();
    });

    it("user should be able to delete a organisation",function(){
        orgPageObj.create();
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/organisaties");
        expect(element.all(by.repeater('organisatie in vm.organisaties')).count()).toBe(1);
        orgPageObj.delete();
        expect(element.all(by.repeater('organisatie in vm.organisaties')).count()).toBe(0);
    });

    it("user should be able to edit a organisation",function(){
        orgPageObj.create();
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/organisaties");
        expect(element.all(by.repeater('organisatie in vm.organisaties')).count()).toBe(1);
        orgPageObj.edit('DTO','Data Transfer Object');
        expect($('.org-naam').getText()).toEqual('DTO');
        expect($('.org-beschrijving').getText()).toEqual('Data Transfer Object');
        orgPageObj.delete();
    });
});



