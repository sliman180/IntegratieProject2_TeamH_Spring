var MainThemePageObject = function(){

    var navMTPage = $(".nav-main-theme-btn");
    var deleteBtn = element(by.id("verwijder"));
    var editBtn = element(by.id("wijzig"));

    this.index = function(){
        browser.get("http://localhost:8080/#/")
    }

    this.toMainthemePage = function () {
        navMTPage.click();
    }

    this.create = function(name, desc, org){
        element(by.id("form-title")).element(by.tagName("h2")).click();
        element(by.id("naam")).sendKeys(name);
        element(by.id("beschrijving")).sendKeys(desc);
        element(by.cssContainingText(".org-opt",org)).click();
        $(".organisation-form").element(by.tagName("button")).click();
    }

    this.delete = function(){
        deleteBtn.click();
        browser.switchTo().alert().accept();
        $("body").sendKeys(protractor.Key.ENTER);
    }

    this.edit = function(name,desc){
        editBtn.click();
        element(by.id("naam")).sendKeys(name);
        element(by.id("beschrijving")).sendKeys(desc);
        $(".organisation-edit-form").element(by.tagName("button")).click();
    }

}

module.exports = MainThemePageObject;