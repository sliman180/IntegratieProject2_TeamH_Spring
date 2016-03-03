(function (angular) {

    "use strict";

    function CirkelsessieIndexController($route, CirkelsessieService) {

        var vm = this;

        vm.cirkelsessies = [];

        CirkelsessieService.all().then(function (data) {
            vm.cirkelsessies = data;
        });

        vm.addCirkelsessie = function (cirkelsessie) {
            CirkelsessieService.create(cirkelsessie).then(function () {
                $route.reload();
            });
        }

    }

    angular.module("kandoe").controller("CirkelsessieIndexController", CirkelsessieIndexController);

})(window.angular);
