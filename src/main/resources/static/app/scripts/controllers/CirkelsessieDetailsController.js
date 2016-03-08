(function (angular) {

    "use strict";


    function CirkelsessieDetailsController($route, $rootScope, $routeParams, CirkelsessieService, ChatService, KaartService) {

        var vm = this;

        vm.getTimes = function (n) {

            var numbers = [];
            for (var i = n; i > 0; i--) {
                numbers.push(i);
            }

            return numbers;
        };

        vm.setCircleColor = function (number) {
            if (number % 2 == 0) {
                return "#4985B9"
            } else {
                return "white"
            }
        };

        vm.cirkelsessie = {};

        CirkelsessieService.find($routeParams.id).then(function (data) {
            vm.cirkelsessie = data;
        });

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


    }

    angular.module("kandoe").controller("CirkelsessieDetailsController", CirkelsessieDetailsController);

})(window.angular);
