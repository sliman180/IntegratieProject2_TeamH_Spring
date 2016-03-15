(function (angular) {

    "use strict";

    function SubthemaService($http) {

        var exports = {};

        exports.mySubthemas = function () {

            return $http.get("/api/gebruikers/subthemas").then(function (response) {
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

        exports.createWithHoofdthema = function (subthema, hoofdthemaId) {

            return $http.post("/api/subthemas/hoofdthema=" + hoofdthemaId, subthema).then(function (response) {
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
