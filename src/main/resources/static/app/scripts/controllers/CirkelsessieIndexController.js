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
        };

        vm.showCirkelsessieLink = function (id) {

            window.location.href = '/#/cirkelsessies/details/' + id;
        };

        vm.deleteCirkelsessieLink = function (id) {

            window.location.href = '/#/cirkelsessies/delete/' + id;
        };


    }

    angular.module("kandoe").controller("CirkelsessieIndexController", CirkelsessieIndexController);

})(window.angular);
