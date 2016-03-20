(function (angular) {

    "use strict";

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

            .when ("/kaarten/edit/:id", {
                templateUrl: "/dist/views/kaarten/edit.html",
                controller: "KaartEditController",
                controllerAs: "vm"
            })
            .when ("/cirkelsessies/edit/:id", {
                templateUrl: "/dist/views/cirkelsessies/edit.html",
                controller: "CirkelsessieEditController",
                controllerAs: "vm"
            })

            .when ("/subthemas/edit/:id", {
                templateUrl: "/dist/views/subthemas/edit.html",
                controller: "SubthemaEditController",
                controllerAs: "vm"
            })

            .when ("/organisaties/edit/:id", {
                templateUrl: "/dist/views/organisaties/edit.html",
                controller: "OrganisatieEditController",
                controllerAs: "vm"
            })

            .when ("/hoofdthemas/edit/:id", {
                templateUrl: "/dist/views/hoofdthemas/edit.html",
                controller: "HoofdthemaEditController",
                controllerAs: "vm"
            })


            .otherwise({
                redirectTo: "/"
            });

    }

    angular.module("kandoe").config(routes);

})(window.angular);
