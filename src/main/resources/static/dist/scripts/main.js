(function (angular) {

    "use strict";

    angular.module("kandoe", ["ngRoute", "ngStorage"])

        .config(["$httpProvider", "localStorageServiceProvider", function ($httpProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix("kandoe");
            $httpProvider.interceptors.push("AuthInterceptor");

        }])

        .run(["$location", "$rootScope", "$timeout", "GebruikerService", "JwtService", "localStorageService", function ($location, $rootScope, $timeout, GebruikerService, JwtService, localStorageService) {

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

                    var gebruikersdatapolling = function () {
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

                        $timeout(gebruikersdatapolling, 1000);
                    };

                    gebruikersdatapolling();


                });

            }

        }]);


})(window.angular);


(function (angular) {

    "use strict";

    routes.$inject = ["$routeProvider"];
    function routes($routeProvider) {

        $routeProvider

            .when("/", {
                templateUrl: "/dist/views/home.html",
                controller: "HomeController",
                controllerAs: "vm"
            })

            .when("/auth/login", {
                templateUrl: "/dist/views/auth/login.html",
                controller: "LoginController",
                controllerAs: "vm"
            })

            .when("/auth/register", {
                templateUrl: "/dist/views/auth/register.html",
                controller: "RegisterController",
                controllerAs: "vm"
            })

            .when("/auth/profile", {
                templateUrl: "/dist/views/auth/profile.html",
                controller: "ProfileController",
                controllerAs: "vm"
            })

            .when("/organisaties", {
                templateUrl: "/dist/views/organisaties/index.html",
                controller: "OrganisatieIndexController",
                controllerAs: "vm"
            })

            .when("/cirkelsessies", {
                templateUrl: "/dist/views/cirkelsessies/index.html",
                controller: "CirkelsessieIndexController",
                controllerAs: "vm"
            })

            .when ("/cirkelsessies/details/:id", {
                templateUrl: "/dist/views/cirkelsessies/details.html",
                controller: "CirkelsessieDetailsController",
                controllerAs: "vm"
            })

            .when ("/deelnames", {
                templateUrl: "/dist/views/deelnames/index.html",
                controller: "DeelnameIndexController",
                controllerAs: "vm"
            })

            .when ("/hoofdthemas", {
                templateUrl: "/dist/views/hoofdthemas/index.html",
                controller: "HoofdthemaIndexController",
                controllerAs: "vm"
            })

            .when ("/subthemas", {
                templateUrl: "/dist/views/subthemas/index.html",
                controller: "SubthemaIndexController",
                controllerAs: "vm"
            })

            .when ("/subthemas/details/:id", {
                templateUrl: "/dist/views/subthemas/details.html",
                controller: "SubthemaDetailsController",
                controllerAs: "vm"
            })

            .when ("/kaarten/details/:id", {
                templateUrl: "/dist/views/kaarten/details.html",
                controller: "KaartDetailsController",
                controllerAs: "vm"
            })

            .otherwise({
                redirectTo: "/"
            });

    }

    angular.module("kandoe").config(routes);

})(window.angular);

(function (angular) {

    "use strict";

    AuthInterceptor.$inject = ["localStorageService"];
    function AuthInterceptor(localStorageService) {

        var exports = {};

        exports.request = function (config) {

            config.headers = config.headers || {};

            if (!config.headers.Authorization) {

                var data = localStorageService.get("auth");

                if (data) {
                    config.headers.Authorization = "Bearer " + data.token;
                }
            }

            return config;

        };

        return exports;

    }

    angular.module("kandoe").factory("AuthInterceptor", AuthInterceptor);

})(window.angular);

(function (angular) {

    "use strict";

    AuthService.$inject = ["$http"];
    function AuthService($http) {

        var exports = {};

        exports.login = function (credentials) {

            return $http.post("/auth/login", credentials).then(function (response) {
                return response.data;
            });

        };

        exports.register = function (credentials) {

            return $http.post("/auth/register", credentials).then(function (response) {
                return response.data;
            });

        };

        return exports;


    }

    angular.module("kandoe").factory("AuthService", AuthService);

})(window.angular);

(function (angular) {

    "use strict";

    BerichtService.$inject = ["$http"];
    function BerichtService($http) {

        var exports = {};

        exports.getGebruiker = function (id) {

            return $http.get("/api/berichten/" + id + "/gebruiker").then(function (response) {
                return response.data;
            });

        };


        return exports;

    }

    angular.module("kandoe").factory("BerichtService", BerichtService);

})(window.angular);

(function (angular) {

    "use strict";

    CirkelsessieService.$inject = ["$http"];
    function CirkelsessieService($http) {

        var exports = {};

        exports.all = function () {

            return $http.get("/api/cirkelsessies").then(function (response) {
                return response.data;
            });

        };

        exports.find = function (id) {

            return $http.get("/api/cirkelsessies/" + id).then(function (response) {
                return response.data;
            });

        };

        exports.create = function (cirkelsessie) {

            return $http.post("/api/cirkelsessies", cirkelsessie).then(function (response) {
                return response.data;
            });

        };

        exports.update = function (cirkelsessie) {

            return $http.put("/api/cirkelsessies/" + cirkelsessie.id, cirkelsessie).then(function (response) {
                return response.data;
            });

        };

        exports.delete = function (id) {

            return $http.delete("/api/cirkelsessies/" + id).then(function (response) {
                return response.data;
            });

        };

        exports.getSpelkaarten = function (id) {

            return $http.get("/api/cirkelsessies/" + id + "/spelkaarten").then(function (response) {
                return response.data;
            });

        };

        exports.addSpelkaart = function (id) {

            return $http.post("/api/cirkelsessies/" + id + "/spelkaarten").then(function (response) {
                return response.data;
            });

        };

        exports.getDeelnames = function (id) {

            return $http.get("/api/cirkelsessies/" + id + "/deelnames").then(function (response) {
                return response.data;
            });

        };

        exports.addDeelname = function (id) {

            return $http.post("/api/cirkelsessies/" + id + "/deelnames").then(function (response) {
                return response.data;
            });

        };

        exports.getBerichten = function (id) {

            return $http.get("/api/cirkelsessies/" + id + "/berichten").then(function (response) {
                return response.data;
            });

        };

        exports.getGebruiker = function (id) {

            return $http.get("/api/cirkelsessies/" + id + "/gebruiker").then(function (response) {
                return response.data;
            });

        };

        exports.addBericht = function (id, bericht) {

            return $http.post("/api/cirkelsessies/" + id + "/berichten", bericht).then(function (response) {
                return response.data;
            });

        };

        exports.beeindigSpel = function (cirkelsessie) {
            cirkelsessie.status = 'BEEINDIGD';
            cirkelsessie.gebruiker = cirkelsessie.gebruiker.id;
            return $http.put("/api/cirkelsessies/" + cirkelsessie.id, cirkelsessie).then(function (response) {
                return response.data;
            });
        };

        return exports;

    }

    angular.module("kandoe").factory("CirkelsessieService", CirkelsessieService);

})(window.angular);

(function (angular) {

    "use strict";

    DeelnameService.$inject = ["$http"];
    function DeelnameService($http) {

        var exports = {};

        exports.allOfGebruiker = function (id) {

            return $http.get("/api/gebruikers/" + id + "/deelnames").then(function (response) {
                return response.data;
            });

        };

        exports.update = function (deelname) {

            return $http.put("/api/deelnames/" + deelname.id, deelname).then(function (response) {
                return response.data;
            });

        };

        exports.delete = function (id) {

            return $http.delete("/api/deelnames/" + id).then(function (response) {
                return response.data;
            });

        };

        exports.getGebruiker = function (id) {

            return $http.get("/api/deelnames/" + id + "/gebruiker").then(function (response) {
                return response.data;
            });
        };

        exports.getCirkelsessie = function (id) {

            return $http.get("/api/deelnames/" + id + "/cirkelsessie").then(function (response) {
                return response.data;
            });
        };


        return exports;

    }

    angular.module("kandoe").factory("DeelnameService", DeelnameService);

})(window.angular);

(function (angular) {

    "use strict";

    GebruikerService.$inject = ["$http"];
    function GebruikerService($http) {

        var exports = {};

        exports.all = function () {

            return $http.get("/api/gebruikers").then(function (response) {
                return response.data;
            });

        };

        exports.create = function (gebruiker) {

            return $http.post("/api/gebruikers", gebruiker).then(function (response) {
                return response.data;
            });

        };

        exports.find = function (id) {

            return $http.get("/api/gebruikers/" + id).then(function (response) {
                return response.data;
            });

        };

        exports.update = function (id, gebruiker) {

            return $http.put("/api/gebruikers/" + id, gebruiker).then(function (response) {
                return response.data;
            });

        };

        exports.delete = function (id) {

            return $http.delete("/api/gebruikers/" + id).then(function (response) {
                return response.data;
            });

        };

        exports.deelnames = function (id) {

            return $http.get("/api/gebruikers/" + id + "/deelnames").then(function (response) {
                return response.data;
            });

        };
        exports.organisaties = function (id) {

            return $http.get("/api/gebruikers/" + id + "/organisaties").then(function (response) {
                return response.data;
            });

        };

        exports.hoofdthemas = function (id) {

            return $http.get("/api/gebruikers/" + id + "/hoofdthemas").then(function (response) {
                return response.data;
            });

        };

        exports.subthemas = function (id) {

            return $http.get("/api/gebruikers/" + id + "/subthemas").then(function (response) {
                return response.data;
            });

        };


        return exports;

    }

    angular.module("kandoe").factory("GebruikerService", GebruikerService);

})(window.angular);

(function (angular) {

    "use strict";

    HoofdthemaService.$inject = ["$http"];
    function HoofdthemaService($http) {

        var exports = {};

        exports.getOrganisatie = function (id) {

            return $http.get("/api/hoofdthemas/" + id + "/organisatie").then(function (response) {
                return response.data;
            });

        };

        exports.getSubthemas = function (id) {

            return $http.get("/api/hoofdthemas/" + id + "/subthemas").then(function (response) {
                return response.data;
            });

        };

        exports.allOfGebruiker = function (id) {

            return $http.get("/api/gebruikers/" + id + "/hoofdthemas").then(function (response) {
                return response.data;
            });

        };

        exports.all = function () {

            return $http.get("/api/hoofdthemas").then(function (response) {
                return response.data;
            });

        };

        exports.find = function (id) {

            return $http.get("/api/hoofdthemas/" + id).then(function (response) {
                return response.data;
            });

        };

        exports.create = function (hoofdthema) {

            return $http.post("/api/hoofdthemas", hoofdthema).then(function (response) {
                return response.data;
            });
        };

        exports.update = function (hoofdthema) {

            return $http.put("/api/hoofdthemas/" + hoofdthema.id, hoofdthema).then(function (response) {
                return response.data;
            });

        };

        exports.delete = function (id) {

            return $http.delete("/api/hoofdthemas/" + id).then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("HoofdthemaService", HoofdthemaService);

})(window.angular);

(function (angular) {

    "use strict";

    function JwtService() {

        this.urlBase64Decode = function (str) {

            var output = str.replace(/-/g, '+').replace(/_/g, '/');

            switch (output.length % 4) {
                case 0:
                    break;
                case 2:
                    output += '==';
                    break;
                case 3:
                    output += '=';
                    break;
                default:
                    throw 'Illegal base64url string!';
            }

            return decodeURIComponent(escape(window.atob(output)));
        };

        this.decodeToken = function (token) {

            var parts = token.split('.');

            if (parts.length !== 3) {
                throw new Error('JWT must have 3 parts');
            }

            var decoded = this.urlBase64Decode(parts[1]);

            if (!decoded) {
                throw new Error('Cannot decode the token');
            }

            return JSON.parse(decoded);
        };

        this.getTokenExpirationDate = function (token) {

            var decoded = this.decodeToken(token);
            var d = new Date(0);

            if (typeof decoded.exp === "undefined") {
                return null;
            }

            d.setUTCSeconds(decoded.exp);

            return d;
        };

        this.isTokenExpired = function (token, offsetSeconds) {

            var d = this.getTokenExpirationDate(token);
            offsetSeconds = offsetSeconds || 0;

            if (d === null) {
                return false;
            }

            return !(d.valueOf() > (new Date().valueOf() + (offsetSeconds * 1000)));
        };

    }

    angular.module("kandoe").service("JwtService", JwtService);

}(window.angular));

(function (angular) {

    "use strict";

    KaartService.$inject = ["$http"];
    function KaartService($http) {

        var exports = {};

        exports.find = function (kaartId) {

            return $http.get("/api/kaarten/" + kaartId).then(function (response) {
                return response.data;
            });

        };

        exports.createKaart = function (cirkelsessieId, kaart) {

            return $http.post("/api/cirkelsessies/" + cirkelsessieId + "/spelkaarten", kaart).then(function (response) {
                return response.data;
            });

        };

        exports.createKaartForSubthema = function (subthemaId, kaart) {

            return $http.post("/api/subthemas/" + subthemaId + "/kaart", kaart).then(function (response) {
                return response.data;
            });

        };

        exports.verschuifKaart = function (spelkaartId) {

            return $http.post("/api/spelkaarten/" + spelkaartId + "/verschuif").then(function (response) {
                return response.data;
            });

        };

        exports.getKaarten = function (subthemaId) {

            return $http.get("/api/subthemas/" + subthemaId + "/kaarten").then(function (response) {
                return response.data;
            });

        };

        exports.getComments = function (kaartId) {

            return $http.get("/api/kaarten/" + kaartId + "/comments").then(function (response) {
                return response.data;
            });

        };

        exports.getGebruiker = function (id) {

            return $http.get("/api/kaarten/" + id + "/gebruiker").then(function (response) {
                return response.data;
            });

        };

        exports.createComment = function (kaartId, comment) {

            return $http.post("/api/kaarten/" + kaartId + "/comments", comment).then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("KaartService", KaartService);

})(window.angular);

(function (angular) {

    "use strict";

    OrganisatieService.$inject = ["$http"];
    function OrganisatieService($http) {

        var exports = {};

        exports.all = function () {

            return $http.get("/api/organisaties").then(function (response) {
                return response.data;
            });

        };


        exports.allOfGebruiker = function (id) {

            return $http.get("/api/gebruikers/" + id + "/organisaties").then(function (response) {
                return response.data;
            });

        };

        exports.find = function (id) {

            return $http.get("/api/organisaties/" + id).then(function (response) {
                return response.data;
            });

        };


        exports.getHoofdthemas = function (id) {

            return $http.get("/api/organisaties/" + id + "/hoofdthemas").then(function (response) {
                return response.data;
            });

        };


        exports.create = function (organisatie) {

            return $http.post("/api/organisaties", organisatie).then(function (response) {
                return response.data;
            });

        };

        exports.update = function (organisatie) {

            return $http.put("/api/organisaties/" + organisatie.id, organisatie).then(function (response) {
                return response.data;
            });

        };

        exports.delete = function (id) {

            return $http.delete("/api/organisaties/" + id).then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("OrganisatieService", OrganisatieService);

})(window.angular);

(function (angular) {

    "use strict";

    SpelkaartService.$inject = ["$http"];
    function SpelkaartService($http) {

        var exports = {};

        exports.getKaart = function (id) {

            return $http.get("/api/spelkaarten/" + id + "/kaart").then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("SpelkaartService", SpelkaartService);

})(window.angular);

(function (angular) {

    "use strict";

    SubthemaService.$inject = ["$http"];
    function SubthemaService($http) {

        var exports = {};


        exports.getKaarten = function (id) {

            return $http.get("/api/subthemas/" + id + "/kaarten").then(function (response) {
                return response.data;
            });

        };

        exports.getHoofdthema = function (id) {

            return $http.get("/api/subthemas/" + id + "/hoofdthema").then(function (response) {
                return response.data;
            });

        };

        exports.getCirkelsessies = function (id) {

            return $http.get("/api/subthemas/" + id + "/cirkelsessies").then(function (response) {
                return response.data;
            });

        };


        exports.getOrganisatie = function (id) {

            return $http.get("/api/subthemas/" + id + "/organisatie").then(function (response) {
                return response.data;
            });

        };

        exports.allOfGebruiker = function (id) {

            return $http.get("/api/gebruikers/" + id + "/subthemas").then(function (response) {
                return response.data;
            });

        };

        exports.all = function () {

            return $http.get("/api/subthemas").then(function (response) {
                return response.data;
            });

        };

        exports.find = function (id) {

            return $http.get("/api/subthemas/" + id).then(function (response) {
                return response.data;
            });

        };

        exports.create = function (subthema) {

            return $http.post("/api/subthemas", subthema).then(function (response) {
                return response.data;
            });
        };

        exports.update = function (subthema) {

            return $http.put("/api/subthemas/" + subthema.id, subthema).then(function (response) {
                return response.data;
            });

        };

        exports.delete = function (id) {

            return $http.delete("/api/hoofdthemas/" + id).then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("SubthemaService", SubthemaService);

})(window.angular);

(function (angular) {

    "use strict";

    LoginController.$inject = ["$location", "$rootScope", "AuthService", "GebruikerService", "JwtService", "localStorageService"];
    function LoginController($location, $rootScope, AuthService, GebruikerService, JwtService, localStorageService) {

        var vm = this;

        vm.login = function (credentials) {

            AuthService.login(credentials).then(function (data) {

                localStorageService.set("auth", {
                    token: data.token
                });

                GebruikerService.find(JwtService.decodeToken(data.token).sub).then(function (gebruiker) {

                    $rootScope.id = gebruiker.id;
                    $rootScope.naam = gebruiker.gebruikersnaam;
                    $rootScope.rollen = gebruiker.rollen;
                    $rootScope.loggedIn = true;

                    $location.path("/");

                });

            });

        };

    }

    angular.module("kandoe").controller("LoginController", LoginController);

})(window.angular);

(function (angular) {

    "use strict";

    ProfileController.$inject = ["$location", "$rootScope", "GebruikerService"];
    function ProfileController($location, $rootScope, GebruikerService) {

        var vm = this;

        vm.updateProfile = function (credentials) {

            if (credentials.wachtwoord == null) {
                credentials.wachtwoord = "";
            }

            GebruikerService.update($rootScope.id, credentials).then(function () {

                GebruikerService.find($rootScope.id).then(function (data) {

                    $rootScope.id = data.id;
                    $rootScope.naam = data.gebruikersnaam;
                    $rootScope.rollen = data.rollen;

                    $location.path("/");

                });

            });

        };


    }

    angular.module("kandoe").controller("ProfileController", ProfileController);

})(window.angular);

(function (angular) {

    "use strict";

    RegisterController.$inject = ["$location", "AuthService"];
    function RegisterController($location, AuthService) {

        var vm = this;

        vm.register = function (credentials) {

            AuthService.register(credentials).then(function () {

                $location.path("/auth/login");

            });

        };

    }

    angular.module("kandoe").controller("RegisterController", RegisterController);

})(window.angular);

(function (angular) {

    "use strict";

    CirkelsessieDetailsController.$inject = ["$route", "$timeout", "$rootScope", "$routeParams", "CirkelsessieService", "KaartService", "DeelnameService", "SpelkaartService", "BerichtService"];
    function CirkelsessieDetailsController($route, $timeout, $rootScope, $routeParams, CirkelsessieService, KaartService, DeelnameService, SpelkaartService, BerichtService) {

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
            CirkelsessieService.addBericht(id, bericht);
        };

        vm.addDeelname = function (id) {
            CirkelsessieService.addDeelname(id).then(function () {
                alert('Beste ' + $rootScope.gebruikersnaam + ', Dank u voor uw deelname !');
            });
        };

        vm.createKaart = function (cirkelsessieId, kaart) {
            kaart.gebruiker = $rootScope.id;
            KaartService.createKaart(cirkelsessieId, kaart).then(function () {
                alert('De kaart  ' + kaart.tekst + ' is toegevoegd.');
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

        vm.showTooltip = function (event, mouseovertext) {
            var tooltip = document.getElementById('tooltip');
            tooltip.setAttribute("x", event.clientX - 50);
            tooltip.setAttribute("y", event.clientY - 50);
            tooltip.firstChild.data = mouseovertext;
            tooltip.setAttribute("visibility", "visible");
        };

        vm.hideTooltip = function () {
            document.getElementById('tooltip').setAttribute("visibility", "hidden");
        };

        vm.beeindigSpel = function (cirkelsessie) {
            CirkelsessieService.beeindigSpel(cirkelsessie).then(function () {
                alert('U hebt de spel beeindigd!');
            });
        };

        vm.kanKaartenToevoegen = function () {
            for(var x=0;x<vm.deelnames.length;x++){
                if(vm.deelnames[x].gebruiker.id==$rootScope.id){
                    if(vm.cirkelsessie.maxAantalKaarten==vm.deelnames[x].aangemaakteKaarten){
                        return false;
                    }
                }
            }
            return true;
        };

    }

    angular.module("kandoe").controller("CirkelsessieDetailsController", CirkelsessieDetailsController);

})(window.angular);

(function (angular) {

    "use strict";

    CirkelsessieIndexController.$inject = ["$rootScope", "$route", "CirkelsessieService", "SubthemaService"];
    function CirkelsessieIndexController($rootScope, $route, CirkelsessieService, SubthemaService) {

        var vm = this;

        vm.nowDate = new Date();
        vm.cirkelsessies = [];
        vm.subthemas = [];
        vm.subthema = {};
        vm.deelnames = [];

        SubthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.subthemas = data;
        });

        CirkelsessieService.all().then(function (data) {
            vm.cirkelsessies = data;
            angular.forEach(vm.cirkelsessies, function (value, key) {
                CirkelsessieService.getDeelnames(value.id).then(function (deelnamesdata) {
                    vm.deelnames.push(deelnamesdata);
                });
            });
        });

        vm.isActive = function (date) {
            return new Date() > new Date(date);
        };

        vm.getSubthema = function (subthemaId) {

            SubthemaService.find(subthemaId).then(function (data) {
                vm.subthema = data;
            });

        };

        vm.addCirkelsessie = function (cirkelsessie) {
            cirkelsessie.gebruiker = $rootScope.id;
            CirkelsessieService.create(cirkelsessie).then(function () {
                $route.reload();
            });
        };

        vm.showCirkelsessieLink = function (id) {

            window.location.href = '/#/cirkelsessies/details/' + id;
        };

        vm.deleteCirkelsessieLink = function (id) {

            window.location.href = '/#/cirkelsessies/delete/' + id;
        };

    }

    angular.module("kandoe").controller("CirkelsessieIndexController", CirkelsessieIndexController);

})(window.angular);

(function (angular) {

    "use strict";

    DeelnameIndexController.$inject = ["$rootScope", "DeelnameService"];
    function DeelnameIndexController($rootScope, DeelnameService) {

        var vm = this;

        vm.deelnames = [];

        DeelnameService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.deelnames = data;
        });

    }

    angular.module("kandoe").controller("DeelnameIndexController", DeelnameIndexController);

})(window.angular);

(function (angular) {

    "use strict";

    OrganisatieIndexController.$inject = ["$route", "$rootScope", "OrganisatieService"];
    function OrganisatieIndexController($route, $rootScope, OrganisatieService) {

        var vm = this;

        vm.organisaties = [];
        vm.hoofdthemas = [];

        OrganisatieService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.organisaties = data;
            angular.forEach(vm.organisaties, function (value, key) {
                OrganisatieService.getHoofdthemas(value.id).then(function (hoofdthemadata) {
                    vm.hoofdthemas.push(hoofdthemadata);
                });
            });
        });

        vm.addOrganisatie = function (organisatie) {
            organisatie.gebruiker = $rootScope.id;
            OrganisatieService.create(organisatie).then(function () {
                $route.reload();
            });
        }

    }

    angular.module("kandoe").controller("OrganisatieIndexController", OrganisatieIndexController);

})(window.angular);

(function (angular) {

    "use strict";

    function HomeController() {

        var vm = this;

    }

    angular.module("kandoe").controller("HomeController", HomeController);

})(window.angular);

(function (angular) {

    "use strict";

    HoofdthemaIndexController.$inject = ["$rootScope", "$route", "HoofdthemaService", "OrganisatieService"];
    function HoofdthemaIndexController($rootScope, $route, HoofdthemaService, OrganisatieService) {

        var vm = this;

        vm.hoofdthemas = [];

        vm.organisaties = [];

        vm.subthemas = [];

        HoofdthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.hoofdthemas = data;
            angular.forEach(vm.hoofdthemas, function (value, key) {
                HoofdthemaService.getSubthemas(value.id).then(function (subthemadata) {
                    vm.subthemas.push(subthemadata);
                });
            });
        });


        OrganisatieService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.organisaties = data;
        });

        vm.addHoofdthema = function (hoofdthema) {
            hoofdthema.gebruiker = $rootScope.id;
            HoofdthemaService.create(hoofdthema).then(function () {
                $route.reload();
            });
        }

    }

    angular.module("kandoe").controller("HoofdthemaIndexController", HoofdthemaIndexController);

})(window.angular);

(function (angular) {

    "use strict";


    KaartDetailsController.$inject = ["$route", "$routeParams", "KaartService", "$rootScope"];
    function KaartDetailsController($route, $routeParams, KaartService, $rootScope) {

        var vm = this;

        vm.kaart = {};
        vm.comments = [];

        KaartService.find($routeParams.id).then(function (data) {
            vm.kaart = data;

            KaartService.getComments($routeParams.id).then(function (commentsdata) {
                vm.comments = commentsdata;
            });
        });


        vm.createComment = function (kaartId, comment) {
            comment.gebruiker = $rootScope.id;
            KaartService.createComment(kaartId, comment).then(function () {
                $route.reload;
            });
        };


        //vm.createComment = function (kaartId, kaart) {
        //    KaartService.createKaartComment(kaartId, kaart).then(function () {
        //        $route.reload();
        //    });
        //};
    }


    angular.module("kandoe").controller("KaartDetailsController", KaartDetailsController);

})(window.angular);

(function (angular) {

    "use strict";


    SubthemaDetailsController.$inject = ["$route", "$rootScope", "$routeParams", "SubthemaService", "HoofdthemaService", "OrganisatieService", "KaartService"];
    function SubthemaDetailsController($route, $rootScope, $routeParams, SubthemaService, HoofdthemaService, OrganisatieService, KaartService) {

        var vm = this;

        vm.subthema = {};
        vm.kaarten = [];
        vm.cirkelsessies = [];
        vm.commentaren = [];

        SubthemaService.find($routeParams.id).then(function (data) {
            vm.subthema = data;
            SubthemaService.getKaarten(vm.subthema.id).then(function (kaartendata) {
                vm.kaarten = kaartendata;
                angular.forEach(vm.kaarten, function (value, key) {
                    KaartService.getComments(value.id).then(function (commentaardata) {
                        vm.commentaren.push(commentaardata);
                    });
                });
            });

            SubthemaService.getCirkelsessies(vm.subthema.id).then(function (cirkelsessiedata) {
                vm.cirkelsessies = cirkelsessiedata;
            });

        });

        vm.createKaart = function (subthemaId, kaart) {
            kaart.gebruiker = $rootScope.id;
            KaartService.createKaartForSubthema(subthemaId, kaart).then(function () {
                $route.reload();
            });
        };

    }


    angular.module("kandoe").controller("SubthemaDetailsController", SubthemaDetailsController);

})(window.angular);

(function (angular) {

    "use strict";

    SubthemaIndexController.$inject = ["$route", "$rootScope", "HoofdthemaService", "SubthemaService", "OrganisatieService"];
    function SubthemaIndexController($route, $rootScope, HoofdthemaService, SubthemaService, OrganisatieService) {

        var vm = this;

        vm.subthemas = [];

        vm.kaarten = [];

        vm.hoofdthemas = [];

        SubthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.subthemas = data;
            angular.forEach(vm.subthemas, function (value, key) {
                SubthemaService.getKaarten(value.id).then(function (kaartendata) {
                    vm.kaarten.push(kaartendata);
                });
            });
        });

        HoofdthemaService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.hoofdthemas = data;
        });


        vm.addSubthema = function (subthema) {
            subthema.gebruiker = $rootScope.id;
            SubthemaService.create(subthema).then(function () {
                $route.reload();
            });
        }
    }

    angular.module("kandoe").controller("SubthemaIndexController", SubthemaIndexController);

})(window.angular);
