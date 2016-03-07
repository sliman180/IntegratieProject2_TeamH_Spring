(function (angular) {

    "use strict";

    function AuthController($location, $rootScope, AuthService, localStorageService) {

        var vm = this;

        vm.login = function (credentials) {

            AuthService.login(credentials).then(function (data) {

                localStorageService.set("auth", {
                    id: data.id,
                    naam: data.naam,
                    token: data.token
                });

                $rootScope.id = data.id;
                $rootScope.naam = data.naam;
                $rootScope.loggedIn = true;
                $location.path("/");

            });

        };


    }

    angular.module("kandoe").controller("AuthController", AuthController);

})(window.angular);
