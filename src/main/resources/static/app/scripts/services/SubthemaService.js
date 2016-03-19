(function (angular) {

    "use strict";

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
