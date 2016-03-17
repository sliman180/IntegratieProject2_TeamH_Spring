(function (angular) {

    "use strict";

    function OrganisatieIndexController($route, $rootScope, OrganisatieService) {

        var vm = this;

        vm.organisaties = [];

        OrganisatieService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.organisaties = data;
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
