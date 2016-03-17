(function (angular) {

    "use strict";

    function SubthemaIndexController($route, $rootScope, HoofdthemaService, SubthemaService, OrganisatieService) {

        var vm = this;

        vm.subthemas = [];

        vm.hoofdthemas = [];

        vm.gekoppeldeHoofdthemas = [];

        vm.gekoppeldeOrganisaties = [];


        SubthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.subthemas = data;
            angular.forEach(vm.subthemas, function (value, key) {
                SubthemaService.getHoofdthema(value.id).then(function (hoofdthemadata) {
                    vm.gekoppeldeHoofdthemas.push(hoofdthemadata);
                });
                SubthemaService.getOrganisatie(value.id).then(function (organisatiedata) {
                    vm.gekoppeldeOrganisaties.push(organisatiedata);
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
