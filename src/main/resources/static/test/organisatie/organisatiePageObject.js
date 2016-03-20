var OrganisationPageObject = function(){

    var navOrgPage = $(".nav-organisation-btn");
    var deleteBtn = element(by.id('verwijder'));
    var editBtn = element(by.id('wijzig'));

    this.index = function(){

    }

    this.toOrganisationPage = function () {
        navOrgPage.click();
    }

    this.create = function(){
        element(by.id("form-title")).element(by.tagName("h2")).click();
        element(by.id("naam")).sendKeys("KdG");
        element(by.id("beschrijving")).sendKeys("Karel de Grote");
        $('.organisation-form').element(by.tagName("button")).click();
    }

    this.delete = function(){
        deleteBtn.click();
        browser.switchTo().alert().accept();
        $('body').sendKeys(protractor.Key.ENTER);
    }

    this.edit = function(naam,beschrijving){
        editBtn.click();
        element(by.id("naam")).sendKeys(naam);
        element(by.id("beschrijving")).sendKeys(beschrijving);
        $('.organisation-edit-form').element(by.tagName("button")).click();
    }
}

module.exports = OrganisationPageObject;