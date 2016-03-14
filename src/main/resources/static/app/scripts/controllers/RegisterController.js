(function (angular) {

    "use strict";

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
