(function (angular) {

    "use strict";

    function CirkelsessieDetailsController($routeParams, CirkelsessieService) {

        var vm = this;

        vm.cirkelsessie = {};

        CirkelsessieService.find($routeParams.id).then(function (data) {
            vm.cirkelsessie = data;
        });

    }

    angular.module("kandoe").controller("CirkelsessieDetailsController", CirkelsessieDetailsController);

})(window.angular);
