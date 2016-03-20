(function (angular) {

    "use strict";

    angular.module("kandoe", ["ngRoute", "ngStorage"])

        .config(function ($httpProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix("kandoe");
            $httpProvider.interceptors.push("AuthInterceptor");

        })

        .run(function ($location, $rootScope, $timeout) {

            $rootScope.$on('$viewContentLoaded', function () {
                $timeout(function () {
                    componentHandler.upgradeAllRegistered();
                }, 0);
            });

        });


})(window.angular);

