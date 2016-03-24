var RegistrationpageObject = function(){

    var navRegForm = $(".nav-register-btn");

    this.index = function(){
        browser.get('http://localhost:8080/#/');
    };

    this.register = function(){
        navRegForm.click();

        element(by.id("gebruikersnaam")).sendKeys("georgybagramya");
        element(by.id("wachtwoord")).sendKeys("georgybagramyan");
        element(by.id("confirmatie")).sendKeys("georgybagramyan");
        $("#register-form").element(by.tagName("button")).click();
    };

};

module.exports = RegistrationpageObject;
