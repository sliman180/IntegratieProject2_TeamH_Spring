(function (angular) {

    "use strict";

    function HoofdthemaEditController($routeParams, $rootScope, HoofdthemaService, $location) {

        var vm = this;

        vm.hoofdthema = {};

        var init = function () {
            HoofdthemaService.find($routeParams.id).then(function (data) {
                vm.hoofdthema = data;
            });

        };

        init();

        vm.editHoofdthema = function (hoofdthema) {
            hoofdthema.id = $routeParams.id;
            hoofdthema.gebruiker = $rootScope.gebruiker.id;
            hoofdthema.organisatie = vm.hoofdthema.organisatie.id;
            HoofdthemaService.update(hoofdthema).then(function () {
                $location.path("/hoofdthemas");
            });
        }

    }

    angular.module("kandoe").controller("HoofdthemaEditController", HoofdthemaEditController);

})(window.angular);
