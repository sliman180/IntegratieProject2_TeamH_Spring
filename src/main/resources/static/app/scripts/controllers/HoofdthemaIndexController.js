(function (angular) {

    "use strict";

    function HoofdthemaIndexController($route, HoofdthemaService, OrganisatieService) {

        var vm = this;

        vm.hoofdthemas = [];

        vm.organisaties = [];


        HoofdthemaService.myHoofdthemas().then(function (data) {
            vm.hoofdthemas = data;
        });

        OrganisatieService.myOrganisaties().then(function (data) {
            vm.organisaties = data;
        });


        vm.addHoofdthema = function (hoofdthema, organisatieId) {
            if (organisatieId > 0) {
                HoofdthemaService.createWithOrganisation(hoofdthema, organisatieId).then(function () {
                    $route.reload();
                });
            } else {
                HoofdthemaService.create(hoofdthema).then(function () {
                    $route.reload();
                });
            }
        }

    }

    angular.module("kandoe").controller("HoofdthemaIndexController", HoofdthemaIndexController);

})(window.angular);
