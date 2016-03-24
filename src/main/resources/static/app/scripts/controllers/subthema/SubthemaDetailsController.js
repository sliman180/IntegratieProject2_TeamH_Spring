(function (angular) {

    "use strict";


    function SubthemaDetailsController($route, $rootScope, $routeParams, SubthemaService, KaartService, $window) {

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
            kaart.gebruiker = $rootScope.gebruiker.id;
            kaart.commentsToelaatbaar = !!document.getElementById('commentsToelaatbaar').checked;
            KaartService.createKaartForSubthema(subthemaId, kaart).then(function () {
                $route.reload();
            });
        };

        vm.deleteKaart = function (id) {
            if ($window.confirm("Bent u zeker dat u de kaart wilt verwijderen?")) {
                KaartService.delete(id).then(function () {
                    $route.reload();
                });
            }
        };
    }


    angular.module("kandoe").controller("SubthemaDetailsController", SubthemaDetailsController);

})(window.angular);
