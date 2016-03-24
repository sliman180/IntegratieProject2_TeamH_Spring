(function (angular) {

    "use strict";

    function KaartEditController($location, $routeParams, KaartService) {

        var vm = this;

        vm.kaart = {};

        KaartService.find($routeParams.id).then(function (data) {
            vm.kaart = data;
        });

        vm.updateKaart = function () {

            vm.kaart.gebruiker = vm.kaart.gebruiker.id;

            KaartService.update(vm.kaart).then(function () {
                $location.path("/kaarten/details/" + $routeParams.id)
            });

        }

    }

    angular.module("kandoe").controller("KaartEditController", KaartEditController);

})(window.angular);
