(function (angular) {

    "use strict";

    function SubthemaEditController($routeParams, SubthemaService, $location) {

        var vm = this;

        vm.subthema = {};

        SubthemaService.find($routeParams.id).then(function (data) {
            vm.subthema = data;
        });

        vm.updateSubthema = function () {

            vm.subthema.gebruiker = vm.subthema.gebruiker.id;
            vm.subthema.hoofdthema = vm.subthema.hoofdthema.id;

            SubthemaService.update(vm.subthema).then(function () {
                $location.path("/subthemas");
            });

        }

    }

    angular.module("kandoe").controller("SubthemaEditController", SubthemaEditController);

})(window.angular);
