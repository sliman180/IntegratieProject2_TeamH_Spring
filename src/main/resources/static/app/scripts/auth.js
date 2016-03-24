(function (angular) {

    "use strict";

    angular.module("kandoe")

        .run(function ($location, $rootScope, localStorageService) {

            $rootScope.logout = function () {

                localStorageService.remove("auth");
                $rootScope.gebruiker = null;
                $location.path("/");

            };

        })

        .run(function ($rootScope, $timeout, GebruikerService, JwtService, localStorageService) {

            var data = localStorageService.get("auth");

            if (data) {

                GebruikerService.find(JwtService.decodeToken(data.token).sub).then(function (gebruiker) {

                    $rootScope.gebruiker = {};
                    $rootScope.gebruiker.id = gebruiker.id;
                    $rootScope.gebruiker.naam = gebruiker.gebruikersnaam;
                    $rootScope.gebruiker.rollen = gebruiker.rollen;
                    $rootScope.gebruiker.aangemeld = true;

                    var haalGebruikerDataOp = function () {

                        GebruikerService.deelnames(gebruiker.id).then(function (deelnames) {
                            $rootScope.gebruiker.aantalDeelnames = deelnames.length;
                        });

                        GebruikerService.hoofdthemas(gebruiker.id).then(function (hoofdthemas) {
                            $rootScope.gebruiker.aantalHoofdthemas = hoofdthemas.length;
                        });

                        GebruikerService.organisaties(gebruiker.id).then(function (organisaties) {
                            $rootScope.gebruiker.aantalOrganisaties = organisaties.length;
                        });

                        GebruikerService.subthemas(gebruiker.id).then(function (subthemas) {
                            $rootScope.gebruiker.aantalSubthemas = subthemas.length;
                        });

                        $timeout(haalGebruikerDataOp, 1000);
                    };

                    haalGebruikerDataOp();

                });

            }

    });


})(window.angular);
