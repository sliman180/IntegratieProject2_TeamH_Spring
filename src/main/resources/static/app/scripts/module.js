(function (angular) {

    "use strict";

    angular.module("kandoe", ["ngRoute", "ngStorage"])

        .config(function ($httpProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix("kandoe");
            $httpProvider.interceptors.push("AuthInterceptor");

        })

        .run(function ($location, $rootScope, localStorageService) {

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
