(function (angular) {

    "use strict";

    function CirkelsessieIndexController($route, CirkelsessieService, SubthemaService) {

        var vm = this;

        vm.cirkelsessies = [];

        vm.subthemas = [];

        vm.subthema = {};


        CirkelsessieService.all().then(function (data) {
            vm.cirkelsessies = data;
        });

        SubthemaService.mySubthemas().then(function (data) {
            vm.subthemas = data;
        });

        vm.getSubthema = function (subthemaId) {

            SubthemaService.find(subthemaId).then(function (data) {
                vm.subthema = data;
            });

        };

        vm.addCirkelsessie = function (cirkelsessie, subthemaId) {
            if (subthemaId > 0) {
                CirkelsessieService.createWithSubthema(cirkelsessie, subthemaId).then(function () {
                    $route.reload();
                });
            } else {
                CirkelsessieService.create(cirkelsessie).then(function () {
                    $route.reload();
                });
            }

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
