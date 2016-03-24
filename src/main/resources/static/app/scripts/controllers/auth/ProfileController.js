(function (angular) {

    "use strict";

    function ProfileController($location, $rootScope, GebruikerService) {

        var vm = this;

        vm.gebruiker = {};

        GebruikerService.find($rootScope.gebruiker.id).then(function (data) {
            vm.gebruiker = data;
        });

        vm.updateProfile = function () {

            if (vm.gebruiker.wachtwoord == null) {
                vm.gebruiker.wachtwoord = "";
            }

            GebruikerService.update($rootScope.gebruiker.id, vm.gebruiker).then(function () {

                GebruikerService.find($rootScope.gebruiker.id).then(function (data) {

                    $rootScope.gebruiker.id = data.id;
                    $rootScope.gebruiker.naam = data.gebruikersnaam;
                    $rootScope.gebruiker.rollen = data.rollen;

                    $location.path("/");

                });

            });

        };


    }

    angular.module("kandoe").controller("ProfileController", ProfileController);

})(window.angular);
