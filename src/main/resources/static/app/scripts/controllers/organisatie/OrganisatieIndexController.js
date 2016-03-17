(function (angular) {

    "use strict";

    function OrganisatieIndexController($route, $rootScope, OrganisatieService) {

        var vm = this;

        vm.organisaties = [];
        vm.hoofdthemas = [];

        OrganisatieService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.organisaties = data;
            angular.forEach(vm.organisaties, function (value, key) {
                OrganisatieService.getHoofdthemas(value.id).then(function (hoofdthemadata) {
                    vm.hoofdthemas.push(hoofdthemadata);
                });
            });
        });

        vm.addOrganisatie = function (organisatie) {
            organisatie.gebruiker = $rootScope.id;
            OrganisatieService.create(organisatie).then(function () {
                $route.reload();
            });
        }

    }

    angular.module("kandoe").controller("OrganisatieIndexController", OrganisatieIndexController);

})(window.angular);
