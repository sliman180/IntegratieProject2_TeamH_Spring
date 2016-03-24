(function (angular) {

    "use strict";

    function OrganisatieIndexController($route, $rootScope, OrganisatieService, $window) {

        var vm = this;

        vm.organisaties = [];
        vm.hoofdthemas = [];

        OrganisatieService.allOfGebruiker($rootScope.gebruiker.id).then(function (data) {
            vm.organisaties = data;
            angular.forEach(vm.organisaties, function (value, key) {
                OrganisatieService.getHoofdthemas(value.id).then(function (hoofdthemadata) {
                    vm.hoofdthemas.push(hoofdthemadata);
                });
            });
        });

        vm.addOrganisatie = function (organisatie) {
            organisatie.gebruiker = $rootScope.gebruiker.id;
            OrganisatieService.create(organisatie).then(function () {
                $route.reload();
            });
        };

        vm.deleteOrganisatie = function (id) {
            if ($window.confirm("Bent u zeker dat u de organisatie wilt verwijderen?")) {
                OrganisatieService.delete(id).then(function () {
                    $route.reload();
                });
            }
        };
    }

    angular.module("kandoe").controller("OrganisatieIndexController", OrganisatieIndexController);

})(window.angular);
