var RegistrationpageObject = function(){

    var navRegForm = $(".nav-register-btn");

    this.index = function(){
        browser.get("http://localhost:8080/#/");
    }

    this.register = function(user,passwd){
        navRegForm.click();

        element(by.id("gebruikersnaam")).sendKeys(user);
        element(by.id("wachtwoord")).sendKeys(passwd);
        element(by.id("confirmatie")).sendKeys(passwd);
        $(".register-form").element(by.tagName("button")).click();
    }
}

module.exports = RegistrationpageObject;