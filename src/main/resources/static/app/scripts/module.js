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
                GebruikerService.find(JwtService.decodeToken(data.token).sub).then(function (gebruiker) {

                    $rootScope.id = gebruiker.id;
                    $rootScope.naam = gebruiker.gebruikersnaam;
                    $rootScope.rollen = gebruiker.rollen;
                    $rootScope.loggedIn = true;
                    $rootScope.aantalDeelnames = gebruiker.deelnames.length;
                    $rootScope.aantalHoofdthemas = gebruiker.hoofdthemas.length;
                    $rootScope.aantalOrganisaties = gebruiker.organisaties.length;
                    $rootScope.aantalSubthemas = gebruiker.subthemas.length;


                });

            }

        });


})(window.angular);

