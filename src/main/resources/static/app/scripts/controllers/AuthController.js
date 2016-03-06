(function (angular) {

    "use strict";

    function AuthController($location, $rootScope, AuthService, localStorageService) {

        var vm = this;

        vm.login = function (credentials) {

            AuthService.login(credentials).then(function (data) {

                localStorageService.set("auth", {
                    token: data.token
                });

                $rootScope.loggedIn = true;
                $location.path("/");

            });

        };

    }

    angular.module("kandoe").controller("AuthController", AuthController);

})(window.angular);
