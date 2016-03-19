(function (angular) {

    "use strict";


    function SubthemaDetailsController($route, $rootScope, $routeParams, SubthemaService, KaartService) {

        var vm = this;

        vm.subthema = {};
        vm.kaarten = [];
        vm.cirkelsessies = [];
        vm.commentaren = [];

        SubthemaService.find($routeParams.id).then(function (data) {
            vm.subthema = data;
            SubthemaService.getKaarten(vm.subthema.id).then(function (kaartendata) {
                vm.kaarten = kaartendata;
                angular.forEach(vm.kaarten, function (value, key) {
                    KaartService.getComments(value.id).then(function (commentaardata) {
                        vm.commentaren.push(commentaardata);
                    });
                });
            });

            SubthemaService.getCirkelsessies(vm.subthema.id).then(function (cirkelsessiedata) {
                vm.cirkelsessies = cirkelsessiedata;
            });

        });

        vm.createKaart = function (subthemaId, kaart) {
            kaart.gebruiker = $rootScope.id;
            KaartService.createKaartForSubthema(subthemaId, kaart).then(function () {
                $route.reload();
            });
        };
    }


    angular.module("kandoe").controller("SubthemaDetailsController", SubthemaDetailsController);

})(window.angular);
