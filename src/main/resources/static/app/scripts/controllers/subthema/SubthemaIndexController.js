(function (angular) {

    "use strict";

    function SubthemaIndexController($route, $rootScope, HoofdthemaService, SubthemaService, OrganisatieService) {

        var vm = this;

        vm.subthemas = [];

        vm.kaarten = [];

        vm.hoofdthemas = [];

        SubthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.subthemas = data;
            angular.forEach(vm.subthemas, function (value, key) {
                SubthemaService.getKaarten(value.id).then(function (kaartendata) {
                    vm.kaarten.push(kaartendata);
                });
            });
        });

        HoofdthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.hoofdthemas = data;
        });


        vm.addSubthema = function (subthema) {
            subthema.gebruiker = $rootScope.id;
            SubthemaService.create(subthema).then(function () {
                $route.reload();
            });
        }
    }

    angular.module("kandoe").controller("SubthemaIndexController", SubthemaIndexController);

})(window.angular);
