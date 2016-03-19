describe("From home page", function(){


    it("user should be able to create a session", function(){

        element(by.css(".nav-session-btn")).click();
        element(by.id("form-title")).element(by.tagName("h2")).click();

        element(by.id("naam")).sendKeys("Session one");
        element(by.id("aantalCirkels")).sendKeys(5);
        element(by.id("maxAantalKaarten")).sendKeys(15);
        element(by.cssContainingText(".sts-opt","OPEN")).click();
        element(by.id("datum")).sendKeys("01/05/20160015:15");
        element(by.cssContainingText(".sbt-opt","AO")).click();
        element(by.css(".session-form")).element(by.tagName("button")).click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/cirkelsessies");
        expect(element.all(by.css(".card-container")).count()).toBe(1);

    });


    //filter

    //sort

    //create
    it("user should be able to create a card during a session",function(){

        element(by.css(".view-link")).click();
        element(by.css(".participate-btn")).click();
        element(by.id("kaartTekst")).sendKeys("Card");
        element(by.id("imageUrl")).sendKeys("Url");
        element(by.css(".card-form")).element(by.tagName("button")).click();

        expect(element.all(by.css(".kaart")).count()).toBe(1);

    });

    //chat
    it("user should be able to chat during a session",function(){

        element(by.id("tekst")).sendKeys("Dit is een tekst");
        element(by.css(".message-form")).element(by.tagName("button")).click();

        expect(element.all(by.css(".bericht")).count()).toBe(1);
    });

    //move card
    it("user shoul be able to move a card",function(){

        expect(element(by.css(".card-position")).getText()).toEqual("1");

        browser.actions().mouseMove(element(by.css(".dropbtn"))).perform().then(function(){
            element(by.css(".move-card-link")).click().then(function(){
                expect(element(by.css(".card-position")).getText()).toEqual("2");
            });
        });

    });

    //comment on card
    it("user should be able to comment on a card during a session",function(){

        //hover inplaats van click -->https://github.com/angular/protractor/issues/159
        browser.actions().mouseMove(element(by.css(".dropbtn"))).perform().then(function(){
            element(by.css(".info-link")).click().then(function(){
                expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#/kaarten/details/1");
            });
        });


        //test faalt:Expected 'http://localhost:8080/?#/cirkelsessies/details/1' to equal 'http://localhost:8080/#/kaarten/details/1'.

        element(by.id("commentaartTekst")).sendKeys("Dit is een commentaar");
        element(by.id("comment-form")).element(by.tagName("button")).click();

        expect(element.all(by.repeater("comment in  vm.comments")).count()).toBe(1);

    });

});