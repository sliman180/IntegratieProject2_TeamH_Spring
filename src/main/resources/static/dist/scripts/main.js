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

                $location.path("/");

            };

            var data = localStorageService.get("auth");

            if (data) {

                GebruikerService.find(JwtService.decodeToken(data.token).sub).then(function (data) {

                    $rootScope.id = data.id;
                    $rootScope.naam = data.gebruikersnaam;
                    $rootScope.rollen = data.rollen;
                    $rootScope.loggedIn = true;

                });

            }

        }]);


})(window.angular);


(function (angular) {

    "use strict";

    angular.module("kandoe").constant("HEADERS", {"Content-Type": "application/json"});

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

            var data = localStorageService.get("auth");

            if (data) {
                config.headers.Authorization = "Bearer " + data.token;
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

    ChatService.$inject = ["$http"];
    function ChatService($http) {

        var exports = {};


        exports.createMessage = function (chatId, bericht) {

            return $http.post("/api/chats/" + chatId + "/messages", bericht).then(function (response) {
                return response.data;
            });

        };

        exports.getChat = function (cirkelsessieId) {

            return $http.get("/api/cirkelsessies/" + cirkelsessieId + "/chat").then(function (response) {
                return response.data;
            });

        };


        return exports;

    }

    angular.module("kandoe").factory("ChatService", ChatService);

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

        return exports;

    }

    angular.module("kandoe").factory("CirkelsessieService", CirkelsessieService);

})(window.angular);

(function (angular) {

    "use strict";

    DeelnameService.$inject = ["$http"];
    function DeelnameService($http) {

        var exports = {};


        exports.doeDeelname = function (cirkelsessieId) {

            return $http.post("/api/deelnames/" + cirkelsessieId).then(function (response) {
                return response.data;
            });

        };

        exports.getDeelnames = function (cirkelsessieId) {

            return $http.get("/api/cirkelsessies/" + cirkelsessieId + "/deelnames").then(function (response) {
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

        exports.getMijnDeelnames = function () {

            return $http.get("/api/gebruikers/deelnames").then(function (response) {
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

        exports.myHoofdthemas = function () {

            return $http.get("/api/gebruikers/hoofdthemas").then(function (response) {
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

        exports.createWithOrganisation = function (hoofdthema, organisatieId) {

            return $http.post("/api/hoofdthemas/organisatie=" + organisatieId, hoofdthema).then(function (response) {
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
                {
                    break;
                }
                case 2:
                {
                    output += '==';
                    break;
                }
                case 3:
                {
                    output += '=';
                    break;
                }
                default:
                {
                    throw 'Illegal base64url string!';
                }
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


        exports.createKaart = function (cirkelsessieId, kaart) {

            return $http.post("/api/cirkelsessies/" + cirkelsessieId + "/spelkaart", kaart).then(function (response) {
                return response.data;
            });

        };

        exports.verschuifKaart = function (spelkaartId) {

            return $http.post("/api/spelkaarten/" + spelkaartId + "/verschuif").then(function (response) {
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

        exports.myOrganisaties = function () {

            return $http.get("/api/organisaties/my").then(function (response) {
                return response.data;
            });

        };

        exports.find = function (id) {

            return $http.get("/api/organisaties/" + id).then(function (response) {
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


        exports.getSpelkaarten = function (cirkelsessieId) {

            return $http.get("/api/cirkelsessies/" + cirkelsessieId + "/spelkaarten").then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("SpelkaartService", SpelkaartService);

})(window.angular);
(function (angular) {

    "use strict";


    CirkelsessieDetailsController.$inject = ["$route", "$rootScope", "$routeParams", "CirkelsessieService", "ChatService", "KaartService", "DeelnameService", "SpelkaartService"];
    function CirkelsessieDetailsController($route, $rootScope, $routeParams, CirkelsessieService, ChatService, KaartService, DeelnameService, SpelkaartService) {

        var vm = this;

        vm.cirkelsessie = {};
        vm.chat = {};
        vm.chatId = {};
        vm.deelnames = [];
        vm.cirkelsessieId = {};
        vm.spelkaarten = [];


        CirkelsessieService.find($routeParams.id).then(function (data) {

            ChatService.getChat(data.id).then(function (chatdata) {
                vm.chat = chatdata;
                vm.chatId = chatdata.id;
            });

            SpelkaartService.getSpelkaarten(data.id).then(function (spelkaartdata) {
                vm.spelkaarten = spelkaartdata;
            });

            DeelnameService.getDeelnames(data.id).then(function (deelnamedata) {
                vm.deelnames = deelnamedata;
            });

            vm.cirkelsessie = data;
            vm.cirkelsessieId = data.id;


        });


        vm.getTimes = function (n) {

            var numbers = [];

            for (var i = n; i > 0; i--) {
                numbers.push(i);
            }

            return numbers;
        };

        vm.isDeelnemer = function (list) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].gebruiker.id == $rootScope.id) {
                    return true;
                }
            }
            return false;
        };

        vm.setCircleColor = function (number) {

            if (number % 2 == 0) {

                return "#4985B9"

            } else {

                return "white"
            }
        };

        vm.ShowTooltip = function (event, mouseovertext) {
            var tooltip = document.getElementById('tooltip');
            tooltip.setAttribute("x", event.clientX - 50);
            tooltip.setAttribute("y", event.clientY - 50);
            tooltip.firstChild.data = mouseovertext;
            tooltip.setAttribute("visibility", "visible");
        };

        vm.HideTooltip = function () {
            var tooltip = document.getElementById('tooltip');
            tooltip.setAttribute("visibility", "hidden");
        };


        vm.createMessage = function (chatId, bericht) {
            ChatService.createMessage(chatId, bericht).then(function () {
                $route.reload();
            });
        };

        vm.createKaart = function (cirkelsessieId, kaart) {
            KaartService.createKaart(cirkelsessieId, kaart).then(function () {
                $route.reload();
            });
        };

        vm.verschuifKaart = function (spelkaartId) {
            KaartService.verschuifKaart(spelkaartId).then(function () {
                $route.reload();
            });
        };

        vm.doeDeelname = function () {
            DeelnameService.doeDeelname(vm.cirkelsessieId).then(function () {
                $route.reload();
            });
        };


    }


    angular.module("kandoe").controller("CirkelsessieDetailsController", CirkelsessieDetailsController);

})(window.angular);

(function (angular) {

    "use strict";

    CirkelsessieIndexController.$inject = ["CirkelsessieService"];
    function CirkelsessieIndexController(CirkelsessieService) {

        var vm = this;

        vm.cirkelsessies = [];

        CirkelsessieService.all().then(function (data) {
            vm.cirkelsessies = data;
        });

        vm.addCirkelsessie = function (cirkelsessie) {
            CirkelsessieService.create(cirkelsessie).then(function () {
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

    DeelnameIndexController.$inject = ["GebruikerService"];
    function DeelnameIndexController(GebruikerService) {

        var vm = this;

        vm.deelnames = [];


        GebruikerService.getMijnDeelnames().then(function (data) {
            vm.deelnames = data;
        });


    }

    angular.module("kandoe").controller("DeelnameIndexController", DeelnameIndexController);

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

    HoofdthemaIndexController.$inject = ["$route", "HoofdthemaService", "OrganisatieService"];
    function HoofdthemaIndexController($route, HoofdthemaService, OrganisatieService) {

        var vm = this;

        vm.hoofdthemas = [];

        vm.organisaties = [];


        HoofdthemaService.myHoofdthemas().then(function (data) {
            vm.hoofdthemas = data;
        });

        OrganisatieService.myOrganisaties().then(function (data) {
            vm.organisaties = data;
        });


        vm.addHoofdthema = function (hoofdthema, organisatieId) {
            if (organisatieId > 0) {
                HoofdthemaService.createWithOrganisation(hoofdthema, organisatieId).then(function () {
                    $route.reload();
                });
            } else {
                HoofdthemaService.create(hoofdthema).then(function () {
                    $route.reload();
                });
            }
        }

    }

    angular.module("kandoe").controller("HoofdthemaIndexController", HoofdthemaIndexController);

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

                GebruikerService.find(JwtService.decodeToken(data.token).sub).then(function (data) {

                    $rootScope.id = data.id;
                    $rootScope.naam = data.gebruikersnaam;
                    $rootScope.rollen = data.rollen;
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

    OrganisatieIndexController.$inject = ["$route", "OrganisatieService"];
    function OrganisatieIndexController($route, OrganisatieService) {

        var vm = this;

        vm.organisaties = [];

        OrganisatieService.myOrganisaties().then(function (data) {
            vm.organisaties = data;
        });

        vm.addOrganisatie = function (organisatie) {
            OrganisatieService.create(organisatie).then(function () {
                $route.reload();
            });
        }

    }

    angular.module("kandoe").controller("OrganisatieIndexController", OrganisatieIndexController);

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

                    console.log(credentials);
                    console.log(data);

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
