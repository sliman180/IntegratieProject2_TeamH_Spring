(function (angular) {

    "use strict";

    function HoofdthemaIndexController($rootScope, $route, HoofdthemaService, OrganisatieService) {

        var vm = this;

        vm.hoofdthemas = [];

        vm.organisaties = [];

        vm.gekoppeldeOrganisaties = [];

        HoofdthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.hoofdthemas = data;
            angular.forEach(vm.hoofdthemas, function (value, key) {
                HoofdthemaService.getOrganisatie(value.id).then(function (organisatiedata) {
                    vm.gekoppeldeOrganisaties.push(organisatiedata);
                });
            });
        });


        OrganisatieService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.organisaties = data;
        });

        vm.addHoofdthema = function (hoofdthema) {
            hoofdthema.gebruiker = $rootScope.id;
            HoofdthemaService.create(hoofdthema).then(function () {
                $route.reload();
            });
        }

    }

    angular.module("kandoe").controller("HoofdthemaIndexController", HoofdthemaIndexController);

})(window.angular);
