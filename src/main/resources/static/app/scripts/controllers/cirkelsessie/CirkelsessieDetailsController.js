(function (angular) {

    "use strict";

    function CirkelsessieDetailsController($location, $timeout, $rootScope, $routeParams, CirkelsessieService, KaartService, DeelnameService, $window, $route) {

        var vm = this;

        vm.cirkelsessie = {};
        vm.gebruikers = [];
        vm.deelnames = [];
        vm.berichten = [];
        vm.spelkaarten = [];
        vm.commentaren = [];

        var cirkelpolling = function () {
            CirkelsessieService.find($routeParams.id).then(function (data) {
                vm.cirkelsessie = data;
                CirkelsessieService.getDeelnames(vm.cirkelsessie.id).then(function (deelnamedata) {
                    vm.deelnames = deelnamedata;
                    angular.forEach(vm.deelnames, function (value, key) {
                        DeelnameService.getGebruiker(value.id).then(function (gebruikerdata) {
                            vm.gebruikers.push(gebruikerdata);
                        });
                    });
                });
            });

            var promise = $timeout(cirkelpolling, 1500);

            $rootScope.$on('$destroy', function () {
                $timeout.cancel(promise);
            });
            $rootScope.$on('$locationChangeStart', function () {
                $timeout.cancel(promise);
            });
        };

        var spelkaartpolling = function () {
            CirkelsessieService.getSpelkaarten($routeParams.id).then(function (spelkaartendata) {
                vm.spelkaarten = spelkaartendata;
                angular.forEach(vm.spelkaarten, function (value, key) {
                    KaartService.getComments(value.kaart.id).then(function (commentaardata) {
                        vm.commentaren.push(commentaardata);
                    });
                });
            });
            var promise = $timeout(spelkaartpolling, 2000);

            $rootScope.$on('$destroy', function () {
                $timeout.cancel(promise);
            });
            $rootScope.$on('$locationChangeStart', function () {
                $timeout.cancel(promise);
            });
        };
        var chatpolling = function () {
            CirkelsessieService.getBerichten($routeParams.id).then(function (berichtendata) {
                vm.berichten = berichtendata;
            });
            var promise = $timeout(chatpolling, 500);

            $rootScope.$on('$destroy', function () {
                $timeout.cancel(promise);
            });
            $rootScope.$on('$locationChangeStart', function () {
                $timeout.cancel(promise);
            });
        };

        cirkelpolling();
        spelkaartpolling();
        chatpolling();

        vm.isActive = function (date) {
            return new Date() > new Date(date);
        };

        vm.isDeelnemer = function () {
            for (var i = 0; i < vm.gebruikers.length; i++) {
                if ($rootScope.id == vm.gebruikers[i].id) {
                    return true;
                }
            }

            return false;
        };

        vm.isAanDeBeurt = function () {

            for (var i = 0; i < vm.deelnames.length; i++) {
                if ($rootScope.id == vm.deelnames[i].gebruiker.id && vm.deelnames[i].aanDeBeurt == true) {
                    return true;
                }
            }

            return false;
        };


        vm.addBericht = function (id, bericht) {
            bericht.gebruiker = $rootScope.id;
            CirkelsessieService.addBericht(id, bericht).then(function () {
                $route.reload();
            });
        };

        vm.addDeelname = function (id) {
            CirkelsessieService.addDeelname(id).then(function () {
                alert('Beste ' + $rootScope.naam + ', Dank u voor uw deelname !');
            });
        };

        vm.createKaart = function (cirkelsessieId, kaart) {
            kaart.gebruiker = $rootScope.id;
            KaartService.createKaart(cirkelsessieId, kaart).then(function () {
                alert('De kaart  "' + kaart.tekst + '" is toegevoegd.');
            });
        };

        vm.verschuifKaart = function (spelkaartId) {
            KaartService.verschuifKaart(spelkaartId).then(function () {
                alert('U hebt de kaart 1 stap dichter verschoven');
            });
        };

        vm.getTimes = function (n) {

            var numbers = [];

            for (var i = n; i > 0; i--) {
                numbers.push(i);
            }

            return numbers;
        };

        vm.setCircleColor = function (number) {

            if (number % 2 == 0) {
                return "#4985b9"
            }

            return "#ffffff"
        };

        vm.showTooltip = function (mouseovertext) {
            var tooltip = document.getElementById('tooltip');
            tooltip.innerHTML = mouseovertext;
            tooltip.setAttribute("display", "block");
        };

        vm.hideTooltip = function () {
            document.getElementById('tooltip').setAttribute("display", "none");
        };

        vm.beeindigSpel = function (cirkelsessie) {
            cirkelsessie.gebruiker = vm.cirkelsessie.gebruiker.id;
            if (cirkelsessie.subthema != null) {
                cirkelsessie.subthema = vm.cirkelsessie.subthema.id;
            } else {
                cirkelsessie.subthema = 0;
            }

            CirkelsessieService.beeindigSpel(cirkelsessie).then(function () {
                alert('U hebt de spel beeindigd!');
            });
        };

        vm.openSpel = function (cirkelsessie) {
            cirkelsessie.gebruiker = vm.cirkelsessie.gebruiker.id;
            if (cirkelsessie.subthema != null) {
                cirkelsessie.subthema = vm.cirkelsessie.subthema.id;
            } else {
                cirkelsessie.subthema = 0;
            }

            CirkelsessieService.openSpel(cirkelsessie).then(function () {
                alert('U hebt de spel geopend!');
            });
        };

        vm.sluitSpel = function (cirkelsessie) {
            cirkelsessie.gebruiker = vm.cirkelsessie.gebruiker.id;
            if (cirkelsessie.subthema != null) {
                cirkelsessie.subthema = vm.cirkelsessie.subthema.id;
            } else {
                cirkelsessie.subthema = 0;
            }

            CirkelsessieService.sluitSpel(cirkelsessie).then(function () {
                alert('U hebt de spel gesloten!');
            });
        };

        vm.kanKaartenToevoegen = function () {
            for (var x = 0; x < vm.deelnames.length; x++) {
                if (vm.deelnames[x].gebruiker.id == $rootScope.id) {
                    if (vm.cirkelsessie.maxAantalKaarten == vm.deelnames[x].aangemaakteKaarten) {
                        return false;
                    }
                }
            }
            return true;
        };

        vm.deleteCirkelsessie = function (id) {
            if ($window.confirm("Bent u zeker dat u de cirkelsessie wilt verwijderen?")) {
                CirkelsessieService.delete(id).then(function () {
                    $location.path("/cirkelsessies");
                });
            }
        };

        vm.editCirkelsessieLink = function (id) {
            $location.path('/cirkelsessies/edit/' + id);
        };

        vm.maakAdmin = function (deelname) {
            deelname.medeorganisator = true;
            deelname.gebruiker = deelname.gebruiker.id;
            deelname.cirkelsessie = deelname.cirkelsessie.id;
            DeelnameService.update(deelname).then(function () {
                alert('De deelnemer "' + deelname.gebruiker.gebruikersnaam + '" is nu medeorganisator!');
            });

        };

        vm.kickDeelnemer = function (deelname) {
            deelname.gebruiker = deelname.gebruiker.id;
            deelname.cirkelsessie = deelname.cirkelsessie.id;
            if ($window.confirm('Bent u zeker dat u deelnemer "' + deelname.gebruiker.gebruikersnaam + '"  wilt kicken?')) {
                DeelnameService.delete(deelname.id).then(function () {
                    $route.reload();
                });
            }
        };


        vm.isAdmin = function () {
            for (var i = 0; i < vm.deelnames.length; i++) {
                if (vm.deelnames[i].gebruiker.id == $rootScope.id && vm.deelnames[i].medeorganisator == true) {
                    return true;
                }
            }
            return false;
        };

    }

    angular.module("kandoe").controller("CirkelsessieDetailsController", CirkelsessieDetailsController);

})(window.angular);
