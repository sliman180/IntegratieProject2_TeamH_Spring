(function (angular) {

    "use strict";

    function SubthemaIndexController($route, HoofdthemaService, SubthemaService) {

        var vm = this;

        vm.subthemas = [];

        vm.hoofdthemas = [];


        SubthemaService.mySubthemas().then(function (data) {
            vm.subthemas = data;
        });

        HoofdthemaService.myHoofdthemas().then(function (data) {
            vm.hoofdthemas = data;
        });


        vm.addSubthema = function (subthema, hoofdthemaId) {
            if (hoofdthemaId > 0) {
                SubthemaService.createWithHoofdthema(subthema, hoofdthemaId).then(function () {
                    $route.reload();
                });
            } else {
                SubthemaService.create(subthema).then(function () {
                    $route.reload();
                });
            }
        }

    }

    angular.module("kandoe").controller("SubthemaIndexController", SubthemaIndexController);

})(window.angular);
