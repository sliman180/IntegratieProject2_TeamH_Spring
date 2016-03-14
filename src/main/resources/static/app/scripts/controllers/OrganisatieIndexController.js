(function (angular) {

    "use strict";

    function OrganisatieIndexController($route, OrganisatieService) {

        var vm = this;

        vm.organisaties = [];

        OrganisatieService.myOrganisaties().then(function (data) {
            vm.organisaties = data;
        });

        vm.addOrganisatie = function (organisatie) {
            OrganisatieService.create(organisatie).then(function () {
                $route.reload();
            });
        }

    }

    angular.module("kandoe").controller("OrganisatieIndexController", OrganisatieIndexController);

})(window.angular);
