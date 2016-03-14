(function (angular) {

    "use strict";

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

        exports.createWithSubthema = function (cirkelsessie, subthemaId) {

            return $http.post("/api/cirkelsessies/subthema=" + subthemaId, cirkelsessie).then(function (response) {
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
