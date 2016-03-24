(function (angular) {

    "use strict";

    function OrganisatieEditController($routeParams, OrganisatieService, $location) {

        var vm = this;

        vm.organisatie = {};

        OrganisatieService.find($routeParams.id).then(function (data) {
            vm.organisatie = data;
        });

        vm.updateOrganisatie = function () {

            vm.organisatie.gebruiker = vm.organisatie.gebruiker.id;

            OrganisatieService.update(vm.organisatie).then(function () {
                $location.path("/organisaties");
            });

        }

    }

    angular.module("kandoe").controller("OrganisatieEditController", OrganisatieEditController);

})(window.angular);
