(function (angular) {

    "use strict";

    angular.module("kandoe", ["ngRoute", "ngStorage"])

        .config(function ($httpProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix("kandoe");
            $httpProvider.interceptors.push("AuthInterceptor");

        })

        .run(function ($location, $rootScope, $timeout, localStorageService) {

            $rootScope.$on('$viewContentLoaded', function(event) {
                $timeout(function() {
                    componentHandler.upgradeAllRegistered();
                }, 0);
            });

            var data = localStorageService.get("auth");

            if (data) {
                $rootScope.id = data.id;
                $rootScope.naam = data.naam;
                $rootScope.loggedIn = true;
            }

            $rootScope.logout = function () {
                localStorageService.remove("auth");
                $rootScope.id = null;
                $rootScope.naam = null;
                $rootScope.loggedIn = false;
                $location.path("/");
            };

        });

})(window.angular);
