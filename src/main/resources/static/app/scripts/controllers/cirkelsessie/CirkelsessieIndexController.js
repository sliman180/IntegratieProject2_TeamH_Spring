(function (angular) {

    "use strict";

    function CirkelsessieIndexController($rootScope, $route, $location, $timeout, $window, CirkelsessieService, SubthemaService) {

        var vm = this;

        vm.cirkelsessies = [];
        vm.subthemas = [];
        vm.subthema = {};
        vm.deelnames = [];
        vm.mijnCirkelsessies = [];
        vm.aanDeBeurt = "";

        var cirkelsessiepolling = function () {

            CirkelsessieService.all().then(function (data) {

                vm.cirkelsessies = data;

                angular.forEach(vm.cirkelsessies, function (value) {
                    CirkelsessieService.getDeelnames(value.id).then(function (deelnamesdata) {
                        vm.deelnames.push(deelnamesdata);
                    });
                });

            });

            var promise = $timeout(cirkelsessiepolling, 5000);

            $rootScope.$on('$destroy', function () {
                $timeout.cancel(promise);
            });

            $rootScope.$on('$locationChangeStart', function () {
                $timeout.cancel(promise);
            });

        }();

        if ($rootScope.gebruiker) {

            SubthemaService.allOfGebruiker($rootScope.gebruiker.id).then(function (data) {
                vm.subthemas = data;
            });

            CirkelsessieService.allOfGebruiker($rootScope.gebruiker.id).then(function (data) {
                vm.mijnCirkelsessies = data;
            });

        }

        vm.getSubthema = function (subthemaId) {
            SubthemaService.find(subthemaId).then(function (data) {
                vm.subthema = data;
            });
        };

        vm.initAanDeBeurt = function (index) {
            angular.forEach(vm.deelnames[index], function (value) {
                if (value.aanDeBeurt) {
                    vm.aanDeBeurt = value.gebruiker.gebruikersnaam;
                }
            });
        };

        vm.isActive = function (date) {
            return new Date() > new Date(date);
        };

        vm.addCirkelsessie = function (cirkelsessie) {

            cirkelsessie.gebruiker = $rootScope.gebruiker.id;

            CirkelsessieService.create(cirkelsessie).then(function () {
                $route.reload();
            });

        };

        vm.cloneCirkelsessie = function (id, cirkelsessie) {

            cirkelsessie.gebruiker = $rootScope.gebruiker.id;

            CirkelsessieService.cloneCirkelsessie(id, cirkelsessie).then(function () {
                $route.reload();
            });

        };

        vm.deleteCirkelsessie = function (id) {
            if ($window.confirm("Bent u zeker dat u de cirkelsessie wilt verwijderen?")) {
                CirkelsessieService.delete(id).then(function () {
                    $route.reload();
                });
            }
        };

        vm.showCirkelsessieLink = function (id) {
            $location.path('/cirkelsessies/details/' + id);
        };

        vm.editCirkelsessieLink = function (id) {
            $location.path('/cirkelsessies/edit/' + id);
        };

    }

    angular.module("kandoe").controller("CirkelsessieIndexController", CirkelsessieIndexController);

})(window.angular);
