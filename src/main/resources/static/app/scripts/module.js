(function (angular) {

    "use strict";

    angular.module("kandoe", ["ngRoute", "ngStorage"])

        .config(function ($httpProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix("kandoe");
            $httpProvider.interceptors.push("AuthInterceptor");

        })

        .run(function ($rootScope, $timeout) {

            $rootScope.$on('$viewContentLoaded', function () {
                $timeout(function () {
                    componentHandler.upgradeAllRegistered();
                }, 0);
            });

        })

        .run(function ($rootScope, GebruikerService, JwtService, localStorageService) {

            var data = localStorageService.get("auth");

            if (data) {

                GebruikerService.find(JwtService.decodeToken(data.token).sub).then(function (gebruiker) {

                    $rootScope.id = gebruiker.id;
                    $rootScope.naam = gebruiker.gebruikersnaam;
                    $rootScope.rollen = gebruiker.rollen;
                    $rootScope.loggedIn = true;
                    $rootScope.aantalDeelnames = data.deelnames.length;
                    $rootScope.aantalHoofdthemas = data.hoofdthemas.length;
                    $rootScope.aantalOrganisaties = data.organisaties.length;
                    $rootScope.aantalSubthemas = data.subthemas.length;

                });

            }

        })

        .run(function ($location, $rootScope, localStorageService) {

            $rootScope.logout = function () {

                localStorageService.remove("auth");

                $rootScope.id = null;
                $rootScope.naam = null;
                $rootScope.rollen = null;
                $rootScope.loggedIn = false;

                $location.path("/");

            };

        });

})(window.angular);

