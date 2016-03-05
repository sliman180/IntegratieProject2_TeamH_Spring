(function (angular) {

    "use strict";

    function CirkelsessieIndexController(CirkelsessieService) {

        var vm = this;

        vm.cirkelsessies = [];

        CirkelsessieService.all().then(function (data) {
            vm.cirkelsessies = data;
        });

        vm.addCirkelsessie = function (cirkelsessie) {
            CirkelsessieService.create(cirkelsessie).then(function () {
            });
        }


    }

    angular.module("kandoe").controller("CirkelsessieIndexController", CirkelsessieIndexController);

})(window.angular);
