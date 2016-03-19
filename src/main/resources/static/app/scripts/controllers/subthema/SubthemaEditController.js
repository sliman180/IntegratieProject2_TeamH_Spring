(function (angular) {

    "use strict";

    function SubthemaEditController($routeParams, $rootScope, SubthemaService, $location) {

        var vm = this;

        vm.subthema = {};

        SubthemaService.find($routeParams.id).then(function (data) {
            vm.subthema = data;
        });

        vm.editSubthema = function (subthema) {
            subthema.id = $routeParams.id;
            subthema.gebruiker = $rootScope.id;
            subthema.hoofdthema = vm.subthema.hoofdthema.id;
            SubthemaService.update(subthema).then(function () {
                $location.path("/subthemas");
            });
        }

    }

    angular.module("kandoe").controller("SubthemaEditController", SubthemaEditController);

})(window.angular);
