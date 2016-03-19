(function (angular) {

    "use strict";

    function KaartEditController($routeParams, $rootScope, KaartService) {

        var vm = this;

        vm.kaart = {};

        KaartService.find($routeParams.id).then(function (data) {
            vm.kaart = data;
        });

        vm.editKaart = function (kaart) {
            kaart.id = $routeParams.id;
            kaart.gebruiker = $rootScope.id;
            KaartService.update(kaart).then(function () {
                alert('Beste ' + $rootScope.naam + ', de kaart is gewijzigd!');
            });
        }

    }

    angular.module("kandoe").controller("KaartEditController", KaartEditController);

})(window.angular);
