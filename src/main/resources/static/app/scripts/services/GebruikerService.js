(function (angular) {

    "use strict";

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
