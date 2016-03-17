(function (angular) {

    "use strict";

    function CirkelsessieDetailsController($route,$timeout, $rootScope, $routeParams, CirkelsessieService, KaartService, DeelnameService, SpelkaartService, BerichtService) {

        var vm = this;

        vm.cirkelsessie = {};
        vm.gebruikers = [];
        vm.deelnames = [];
        vm.berichten = [];
        vm.spelkaarten = [];
        vm.commentaren = [];

        var cirkelpolling = function() {
        CirkelsessieService.find($routeParams.id).then(function (data) {
            vm.cirkelsessie = data;
            CirkelsessieService.getDeelnames(vm.cirkelsessie.id).then(function (deelnamedata) {
                vm.deelnames = deelnamedata;
                angular.forEach(vm.deelnames, function (value, key) {
                    DeelnameService.getGebruiker(value.id).then(function (gebruikerdata) {
                        vm.gebruikers.push(gebruikerdata);
                    });
                });
            });
        });
            var promise = $timeout(cirkelpolling, 1500);

            $rootScope.$on('$destroy', function(){
                $timeout.cancel(promise);
            });
            $rootScope.$on('$locationChangeStart', function(){
                $timeout.cancel(promise);
            });
        };

        var spelkaartpolling = function() {
            CirkelsessieService.getSpelkaarten($routeParams.id).then(function (spelkaartendata) {
                vm.spelkaarten = spelkaartendata;
                angular.forEach(vm.spelkaarten, function (value, key) {
                    KaartService.getComments(value.kaart.id).then(function (commentaardata) {
                        vm.commentaren.push(commentaardata);
                    });
                });
            });
            var promise = $timeout(spelkaartpolling, 2000);

            $rootScope.$on('$destroy', function(){
                $timeout.cancel(promise);
            });
            $rootScope.$on('$locationChangeStart', function(){
                $timeout.cancel(promise);
            });
        };
            var chatpolling = function() {
                CirkelsessieService.getBerichten($routeParams.id).then(function (berichtendata) {
                    vm.berichten = berichtendata;
                });
                var promise=$timeout(chatpolling, 500);

                $rootScope.$on('$destroy', function(){
                    $timeout.cancel(promise);
                });
                $rootScope.$on('$locationChangeStart', function(){
                    $timeout.cancel(promise);
                });
            };

        cirkelpolling();
        spelkaartpolling();
        chatpolling();

        vm.isActive = function (date) {
            return new Date() > new Date(date);
        };

        vm.isDeelnemer = function () {
            for (var i = 0; i < vm.gebruikers.length; i++) {
                if ($rootScope.id == vm.gebruikers[i].id) {
                    return true;
                }
            }

            return false;
        };

        vm.addBericht = function (id, bericht) {
            bericht.gebruiker = $rootScope.id;
            CirkelsessieService.addBericht(id, bericht);
        };

        vm.addDeelname = function (id) {
            CirkelsessieService.addDeelname(id).then(function () {
              alert('Beste ' +$rootScope.gebruikersnaam +', Dank u voor uw deelname !');
            });
        };

        vm.createKaart = function (cirkelsessieId, kaart) {
            kaart.gebruiker = $rootScope.id;
            KaartService.createKaart(cirkelsessieId, kaart).then(function () {
                alert('De kaart  ' + kaart.tekst + ' is toegevoegd.');
            });
        };

        vm.verschuifKaart = function (spelkaartId) {
            KaartService.verschuifKaart(spelkaartId).then(function () {
                alert('U hebt de kaart 1 stap dichter verschoven');
            });
        };

        vm.getTimes = function (n) {

            var numbers = [];

            for (var i = n; i > 0; i--) {
                numbers.push(i);
            }

            return numbers;
        };

        vm.setCircleColor = function (number) {

            if (number % 2 == 0) {
                return "#4985b9"
            }

            return "#ffffff"
        };

        vm.showTooltip = function (event, mouseovertext) {
            var tooltip = document.getElementById('tooltip');
            tooltip.setAttribute("x", event.clientX - 50);
            tooltip.setAttribute("y", event.clientY - 50);
            tooltip.firstChild.data = mouseovertext;
            tooltip.setAttribute("visibility", "visible");
        };

        vm.hideTooltip = function () {
            document.getElementById('tooltip').setAttribute("visibility", "hidden");
        };

    }

    angular.module("kandoe").controller("CirkelsessieDetailsController", CirkelsessieDetailsController);

})(window.angular);
