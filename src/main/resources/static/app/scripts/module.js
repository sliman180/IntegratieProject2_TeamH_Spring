(function (angular) {

    "use strict";

    angular.module("kandoe", ["ngRoute", "ngStorage"])

        .config(function ($httpProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix("kandoe");
            $httpProvider.interceptors.push("AuthInterceptor");

        })

        .run(function ($location, $rootScope, localStorageService) {

            if (localStorageService.get("auth")) {
                $rootScope.loggedIn = true;
            }

            $rootScope.logout = function () {
                localStorageService.remove("auth");
                $rootScope.loggedIn = false;
                $location.path("/");
            };

        });

})(window.angular);
