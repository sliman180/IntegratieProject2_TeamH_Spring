(function (angular) {

    "use strict";

    function CirkelsessieIndexController($rootScope, $route, CirkelsessieService, SubthemaService) {

        var vm = this;

        vm.nowDate = new Date();
        vm.cirkelsessies = [];
        vm.subthemas = [];
        vm.subthema = {};
        vm.deelnames = [];
        vm.mijnCirkelsessies = [];

        SubthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.subthemas = data;
        });

        CirkelsessieService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.mijnCirkelsessies = data;
        });

        CirkelsessieService.all().then(function (data) {
            vm.cirkelsessies = data;
            angular.forEach(vm.cirkelsessies, function (value, key) {
                CirkelsessieService.getDeelnames(value.id).then(function (deelnamesdata) {
                    vm.deelnames.push(deelnamesdata);
                });
            });
        });

        vm.isActive = function (date) {
            return new Date() > new Date(date);
        };

        vm.getSubthema = function (subthemaId) {

            SubthemaService.find(subthemaId).then(function (data) {
                vm.subthema = data;
            });

        };

        vm.addCirkelsessie = function (cirkelsessie) {
            cirkelsessie.gebruiker = $rootScope.id;
            CirkelsessieService.create(cirkelsessie).then(function () {
                $route.reload();
            });
        };

        vm.showCirkelsessieLink = function (id) {

            window.location.href = '/#/cirkelsessies/details/' + id;
        };

        vm.deleteCirkelsessieLink = function (id) {
            window.location.href = '/#/cirkelsessies/delete/' + id;
        };

        vm.cloneCirkelsessie = function (id, cirkelsessie) {
            cirkelsessie.gebruiker=$rootScope.id;
            CirkelsessieService.cloneCirkelsessie(id, cirkelsessie).then(function () {
                $route.reload();
            });
        };

    }

    angular.module("kandoe").controller("CirkelsessieIndexController", CirkelsessieIndexController);

})(window.angular);
