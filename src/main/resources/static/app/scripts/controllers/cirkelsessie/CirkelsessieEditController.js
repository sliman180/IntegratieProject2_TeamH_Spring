(function (angular) {

    "use strict";

    function CirkelsessieEditController($routeParams, $rootScope, CirkelsessieService, $location) {

        var vm = this;

        vm.cirkelsessie = {};

        CirkelsessieService.find($routeParams.id).then(function (data) {
            vm.cirkelsessie = data;
        });

        vm.editCirkelsessie = function (cirkelsessie) {
            cirkelsessie.id = $routeParams.id;
            cirkelsessie.gebruiker = $rootScope.id;
            cirkelsessie.status = vm.cirkelsessie.status;
            if (cirkelsessie.subthema != null) {
                cirkelsessie.subthema = vm.cirkelsessie.subthema.id;
            } else {
                cirkelsessie.subthema = 0;
            }
            CirkelsessieService.update(cirkelsessie).then(function () {
                $location.path("/cirkelsessies");
            });
        }

    }

    angular.module("kandoe").controller("CirkelsessieEditController", CirkelsessieEditController);

})(window.angular);
