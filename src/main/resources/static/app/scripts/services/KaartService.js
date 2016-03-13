(function (angular) {

    "use strict";

    function KaartService($http) {

        var exports = {};


        exports.createKaart = function (cirkelsessieId, kaart) {

            return $http.post("/api/cirkelsessies/" + cirkelsessieId + "/spelkaart", kaart).then(function (response) {
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

        return exports;

    }

    angular.module("kandoe").factory("KaartService", KaartService);

})(window.angular);