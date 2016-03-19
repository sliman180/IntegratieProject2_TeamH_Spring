describe("From a home page",function(){

    it("user should be able to create an organisation",function(){

        element(by.css(".nav-organisation-btn")).click();
        element(by.id("form-title")).element(by.tagName("h2")).click();
        element(by.id("naam")).sendKeys("KdG");
        element(by.id("beschrijving")).sendKeys("Karel de Grote");
        element(by.id("form")).element(by.tagName("button")).click();

        expect(element.all(by.css(".lijst-item")).count()).toBe(1);
        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/organisaties")

    });

});