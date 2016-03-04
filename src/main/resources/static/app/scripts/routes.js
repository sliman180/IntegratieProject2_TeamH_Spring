(function (angular) {

    "use strict";

    function routes($routeProvider) {

        $routeProvider

            .when("/", {
                templateUrl: "/dist/views/home.html",
                controller: "HomeController",
                controllerAs: "vm"
            })

            .when("/auth", {
                templateUrl: "/dist/views/auth.html",
                controller: "AuthController",
                controllerAs: "vm"
            })

            .when("/organisaties", {
                templateUrl: "/dist/views/organisaties/index.html",
                controller: "OrganisatieIndexController",
                controllerAs: "vm"
            })

            .otherwise({
                redirectTo: "/"
            });

    }

    angular.module("kandoe").config(routes);

})(window.angular);
