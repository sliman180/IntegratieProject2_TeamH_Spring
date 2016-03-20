var AuthenticationPageObject = function(){

    var logInBtn = $('.nav-login-btn');
    var logOffBtn = $('.nav-logoff-btn');

    this.index = function(){
        browser.get("http://localhost:8080/#/");
    }

    this.login = function(){
        logInBtn.click();
        element(by.id("gebruikersnaam")).sendKeys("userone");
        element(by.id("wachtwoord")).sendKeys("userone");
        element(by.css(".login-form")).element(by.css(".login-btn")).click();
    }

    this.logOff = function(){
        logOffBtn.click();
    }
}

module.exports = AuthenticationPageObject;