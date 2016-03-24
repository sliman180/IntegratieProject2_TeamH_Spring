(function (angular) {

    "use strict";

    function HoofdthemaEditController($routeParams, HoofdthemaService, $location) {

        var vm = this;

        vm.hoofdthema = {};

        HoofdthemaService.find($routeParams.id).then(function (data) {
            vm.hoofdthema = data;
        });

        vm.updateHoofdthema = function () {

            vm.hoofdthema.gebruiker = vm.hoofdthema.gebruiker.id;
            vm.hoofdthema.organisatie = vm.hoofdthema.organisatie.id;

            HoofdthemaService.update(vm.hoofdthema).then(function () {
                $location.path("/hoofdthemas");
            });

        }

    }

    angular.module("kandoe").controller("HoofdthemaEditController", HoofdthemaEditController);

})(window.angular);
