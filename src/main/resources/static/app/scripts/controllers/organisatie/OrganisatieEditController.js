(function (angular) {

    "use strict";

    function OrganisatieEditController($routeParams, $rootScope, OrganisatieService, $location) {

        var vm = this;

        vm.organisatie = {};

        OrganisatieService.find($routeParams.id).then(function (data) {
            vm.organisatie = data;
        });

        vm.editOrganisatie = function (organisatie) {
            organisatie.id = $routeParams.id;
            organisatie.gebruiker = $rootScope.gebruiker.id;
            OrganisatieService.update(organisatie).then(function () {
                $location.path("/organisaties");
            });
        }

    }

    angular.module("kandoe").controller("OrganisatieEditController", OrganisatieEditController);

})(window.angular);
