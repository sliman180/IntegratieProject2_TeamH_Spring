(function (angular) {

    "use strict";

    function SubthemaIndexController($route, $rootScope, HoofdthemaService, SubthemaService, $window) {

        var vm = this;

        vm.subthemas = [];

        vm.kaarten = [];

        vm.hoofdthemas = [];

        SubthemaService.allOfGebruiker($rootScope.gebruiker.id).then(function (data) {
            vm.subthemas = data;
            angular.forEach(vm.subthemas, function (value, key) {
                SubthemaService.getKaarten(value.id).then(function (kaartendata) {
                    vm.kaarten.push(kaartendata);
                });
            });
        });

        HoofdthemaService.allOfGebruiker($rootScope.gebruiker.id).then(function (data) {
            vm.hoofdthemas = data;
        });


        vm.addSubthema = function (subthema) {
            subthema.gebruiker = $rootScope.gebruiker.id;
            SubthemaService.create(subthema).then(function () {
                $route.reload();
            });
        };

        vm.deleteSubthema = function (id) {
            if ($window.confirm("Bent u zeker dat u de subthema wilt verwijderen?")) {
                SubthemaService.delete(id).then(function () {
                    $route.reload();
                });
            }
        };

    }

    angular.module("kandoe").controller("SubthemaIndexController", SubthemaIndexController);

})(window.angular);
