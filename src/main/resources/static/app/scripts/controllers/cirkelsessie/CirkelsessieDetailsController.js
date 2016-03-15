(function (angular) {

    "use strict";

    function CirkelsessieDetailsController($route, $rootScope, $routeParams, CirkelsessieService, KaartService) {

        var vm = this;

        vm.cirkelsessie = {};
        vm.spelkaarten = [];
        vm.deelnames = [];
        vm.berichten = [];

        CirkelsessieService.find($routeParams.id).then(function (data) {

            vm.cirkelsessie = data;

            CirkelsessieService.getSpelkaarten(data.id).then(function(spelkaarten) {
                vm.spelkaarten = spelkaarten
            });

            CirkelsessieService.getDeelnames(data.id).then(function(deelnames) {
                vm.deelnames = deelnames
            });

            CirkelsessieService.getBerichten(data.id).then(function(berichten) {
                vm.berichten = berichten
            });

        });

        vm.isDeelnemer = function (list) {

            if (list == null) {
                return false;
            }

            if (list.length === 0) {
                return false;
            }

            for (var i = 0; i < list.length; i++) {
                if (list[i].gebruiker.id == $rootScope.id) {
                    return true;
                }
            }

            return false;
        };

        vm.addBericht = function (id, bericht) {
            CirkelsessieService.addBericht(id, bericht).then(function () {
                $route.reload();
            });
        };

        vm.addDeelname = function (id) {
            CirkelsessieService.addDeelname(id).then(function () {
                $route.reload();
            });
        };

        vm.createKaart = function (cirkelsessieId, kaart) {
            KaartService.createKaart(cirkelsessieId, kaart).then(function () {
                $route.reload();
            });
        };

        vm.verschuifKaart = function (spelkaartId) {
            KaartService.verschuifKaart(spelkaartId).then(function () {
                $route.reload();
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
