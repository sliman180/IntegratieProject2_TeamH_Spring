(function (angular) {

    "use strict";


    function SubthemaDetailsController($route, $rootScope, $routeParams, SubthemaService, HoofdthemaService, OrganisatieService, KaartService) {

        var vm = this;

        vm.subthema = {};
        vm.hoofdthema = {};
        vm.organisatie = {};

        SubthemaService.find($routeParams.id).then(function (data) {
            vm.subthema = data;
            SubthemaService.getHoofdthema(vm.subthema.id).then(function (subthemadata) {
                vm.hoofdthema = subthemadata;
            });
            SubthemaService.getOrganisatie(vm.subthema.id).then(function (organisatiedata) {
                vm.organisatie = organisatiedata;
            });
        });

        vm.createKaart = function (subthemaId, kaart) {
            kaart.gebruiker = $rootScope.id;
            KaartService.createKaartForSubthema(subthemaId, kaart).then(function () {
                $route.reload();
            });
        };

    }


    angular.module("kandoe").controller("SubthemaDetailsController", SubthemaDetailsController);

})(window.angular);
