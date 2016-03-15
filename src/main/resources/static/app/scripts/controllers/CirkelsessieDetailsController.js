(function (angular) {

    "use strict";


    function CirkelsessieDetailsController($route, $rootScope, $routeParams, CirkelsessieService, ChatService, KaartService, DeelnameService, SpelkaartService) {

        var vm = this;

        vm.cirkelsessie = {};
        vm.chat = {};
        vm.chatId = {};
        vm.deelnames = [];
        vm.cirkelsessieId = {};
        vm.spelkaarten = [];


        CirkelsessieService.find($routeParams.id).then(function (data) {

            ChatService.getChat(data.id).then(function (chatdata) {
                vm.chat = chatdata;
                vm.chatId = chatdata.id;
            });

            SpelkaartService.getSpelkaarten(data.id).then(function (spelkaartdata) {
                vm.spelkaarten = spelkaartdata;
            });

            DeelnameService.getDeelnames(data.id).then(function (deelnamedata) {
                vm.deelnames = deelnamedata;
            });

            vm.cirkelsessie = data;
            vm.cirkelsessieId = data.id;


        });

        vm.isActive = function (date) {
            return new Date() > new Date(date);
        };

        vm.getTimes = function (n) {

            var numbers = [];

            for (var i = n; i > 0; i--) {
                numbers.push(i);
            }

            return numbers;
        };

        vm.isDeelnemer = function (list) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].gebruiker.id == $rootScope.id) {
                    return true;
                }
            }
            return false;
        };

        vm.setCircleColor = function (number) {

            if (number % 2 == 0) {

                return "#4985B9"

            } else {

                return "white"
            }
        };

        vm.ShowTooltip = function (event, mouseovertext) {
            var tooltip = document.getElementById('tooltip');
            tooltip.setAttribute("x", event.clientX - 50);
            tooltip.setAttribute("y", event.clientY - 50);
            tooltip.firstChild.data = mouseovertext;
            tooltip.setAttribute("visibility", "visible");
        };

        vm.HideTooltip = function () {
            var tooltip = document.getElementById('tooltip');
            tooltip.setAttribute("visibility", "hidden");
        };


        vm.createMessage = function (chatId, bericht) {
            ChatService.createMessage(chatId, bericht).then(function () {
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

        vm.doeDeelname = function () {
            DeelnameService.doeDeelname(vm.cirkelsessieId).then(function () {
                $route.reload();
            });
        };


    }


    angular.module("kandoe").controller("CirkelsessieDetailsController", CirkelsessieDetailsController);

})(window.angular);
