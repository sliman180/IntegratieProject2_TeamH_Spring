(function (angular) {

    "use strict";

    angular.module("kandoe", ["ngRoute", "ngStorage"])

        .config(function ($httpProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix("kandoe");
            $httpProvider.interceptors.push("AuthInterceptor");

        })

        .run(function ($location, $rootScope, $timeout, GebruikerService, JwtService, localStorageService) {

            $rootScope.$on('$viewContentLoaded', function () {
                $timeout(function () {
                    componentHandler.upgradeAllRegistered();
                }, 0);
            });

            $rootScope.logout = function () {

                localStorageService.remove("auth");

                $rootScope.id = null;
                $rootScope.naam = null;
                $rootScope.rollen = null;
                $rootScope.loggedIn = false;
                $rootScope.aantalDeelnames = null;
                $rootScope.aantalHoofdthemas = null;
                $rootScope.aantalOrganisaties = null;
                $rootScope.aantalSubthemas = null;

                $location.path("/");

            };

            var data = localStorageService.get("auth");

            if (data) {

                GebruikerService.find(JwtService.decodeToken(data.token).sub).then(function (data) {

                    $rootScope.id = data.id;
                    $rootScope.naam = data.gebruikersnaam;
                    $rootScope.rollen = data.rollen;
                    $rootScope.loggedIn = true;
                    $rootScope.aantalDeelnames = data.deelnames.length;
                    $rootScope.aantalHoofdthemas = data.hoofdthemas.length;
                    $rootScope.aantalOrganisaties = data.organisaties.length;
                    $rootScope.aantalSubthemas = data.subthemas.length;

                });

            }

        });


})(window.angular);

