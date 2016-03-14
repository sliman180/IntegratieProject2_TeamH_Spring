(function (angular) {

    "use strict";


    function SubthemaDetailsController($route, $rootScope, $routeParams, SubthemaService, ChatService, KaartService, DeelnameService, SpelkaartService) {

        var vm = this;

        vm.subthema = {};
        vm.subthemaId = {};
        vm.kaarten = [];

        SubthemaService.find($routeParams.id).then(function (data) {

            KaartService.getKaarten(data.id).then(function (kaartendata) {
                vm.kaarten = kaartendata;
            });

            vm.subthema = data;
            vm.subthemaId = data.id;

        });


        vm.createKaart = function (subthemaId, kaart) {
            KaartService.createKaartForSubthema(subthemaId, kaart).then(function () {
                $route.reload();
            });
        };

    }


    angular.module("kandoe").controller("SubthemaDetailsController", SubthemaDetailsController);

})(window.angular);
