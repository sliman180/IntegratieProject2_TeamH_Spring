(function (angular) {

    "use strict";

    function CirkelsessieEditController($routeParams, CirkelsessieService, $location) {

        var vm = this;

        vm.cirkelsessie = {};

        CirkelsessieService.find($routeParams.id).then(function (data) {
            vm.cirkelsessie = data;
        });

        vm.updateCirkelsessie = function () {

            vm.cirkelsessie.gebruiker = vm.cirkelsessie.gebruiker.id;

            if (vm.cirkelsessie.subthema != null) {
                vm.cirkelsessie.subthema = vm.cirkelsessie.subthema.id;
            } else {
                vm.cirkelsessie.subthema = 0;
            }

            CirkelsessieService.update(cirkelsessie).then(function () {
                $location.path("/cirkelsessies");
            });

        }

    }

    angular.module("kandoe").controller("CirkelsessieEditController", CirkelsessieEditController);

})(window.angular);
