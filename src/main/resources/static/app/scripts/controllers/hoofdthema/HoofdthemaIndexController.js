(function (angular) {

    "use strict";

    function HoofdthemaIndexController($rootScope, $route, HoofdthemaService, OrganisatieService, $window) {

        var vm = this;

        vm.hoofdthemas = [];

        vm.organisaties = [];

        vm.subthemas = [];

        HoofdthemaService.allOfGebruiker($rootScope.gebruiker.id).then(function (data) {
            vm.hoofdthemas = data;
            angular.forEach(vm.hoofdthemas, function (value, key) {
                HoofdthemaService.getSubthemas(value.id).then(function (subthemadata) {
                    vm.subthemas.push(subthemadata);
                });
            });
        });


        OrganisatieService.allOfGebruiker($rootScope.gebruiker.id).then(function (data) {
            vm.organisaties = data;
        });

        vm.addHoofdthema = function (hoofdthema) {
            hoofdthema.gebruiker = $rootScope.gebruiker.id;
            HoofdthemaService.create(hoofdthema).then(function () {
                $route.reload();
            });
        };

        vm.deleteHoofdthema = function (id) {
            if ($window.confirm("Bent u zeker dat u de hoofdthema wilt verwijderen?")) {
                HoofdthemaService.delete(id).then(function () {
                    $route.reload();
                });
            }
        };

    }

    angular.module("kandoe").controller("HoofdthemaIndexController", HoofdthemaIndexController);

})(window.angular);
