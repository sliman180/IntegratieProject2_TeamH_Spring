(function (angular) {

    "use strict";

    angular.module("kandoe", ["ngRoute", "ngStorage"])

        .config(function ($httpProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix("kandoe");
            $httpProvider.interceptors.push("AuthInterceptor");

        })

        .run(function ($location, $rootScope, $timeout, JwtService, localStorageService) {

            $rootScope.$on('$viewContentLoaded', function() {
                $timeout(function() {
                    componentHandler.upgradeAllRegistered();
                }, 0);
            });

            $rootScope.logout = function () {

                localStorageService.remove("auth");

                $rootScope.naam = null;
                $rootScope.roles = null;
                $rootScope.loggedIn = false;

                $location.path("/");

            };

            var data = localStorageService.get("auth");

            if (data) {

                var claims = JwtService.decodeToken(data.token);

                $rootScope.naam = claims.naam;
                $rootScope.roles = claims.roles;
                $rootScope.loggedIn = true;

            }

        });

})(window.angular);
