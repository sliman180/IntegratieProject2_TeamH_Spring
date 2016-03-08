(function (angular) {

    "use strict";

    function LoginController($location, $rootScope, AuthService, JwtService, localStorageService) {

        var vm = this;

        vm.login = function (credentials) {

            AuthService.login(credentials).then(function (data) {

                var claims = JwtService.decodeToken(data.token);

                localStorageService.set("auth", {
                    token: data.token
                });

                $rootScope.naam = claims.sub;
                $rootScope.roles = claims.roles;
                $rootScope.loggedIn = true;

                $location.path("/");

            });

        };


    }

    angular.module("kandoe").controller("LoginController", LoginController);

})(window.angular);
