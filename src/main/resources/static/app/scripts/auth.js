(function (angular) {

    "use strict";

    angular.module("kandoe").run(function ($location, $rootScope, $timeout, GebruikerService, JwtService, localStorageService) {

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

                    var haalGebruikerDataOp = function () {

                        GebruikerService.deelnames(gebruiker.id).then(function (deelnames) {
                            $rootScope.aantalDeelnames = deelnames.length;
                        });

                        GebruikerService.hoofdthemas(gebruiker.id).then(function (hoofdthemas) {
                            $rootScope.aantalHoofdthemas = hoofdthemas.length;
                        });

                        GebruikerService.organisaties(gebruiker.id).then(function (organisaties) {
                            $rootScope.aantalOrganisaties = organisaties.length;
                        });

                        GebruikerService.subthemas(gebruiker.id).then(function (subthemas) {
                            $rootScope.aantalSubthemas = subthemas.length;
                        });

                        $timeout(haalGebruikerDataOp, 1000);
                    }();

                });

            }

        });


})(window.angular);
