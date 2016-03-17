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

        return exports;

    }

    angular.module("kandoe").factory("CirkelsessieService", CirkelsessieService);

})(window.angular);
