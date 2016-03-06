(function (angular) {

    "use strict";

    function KaartService($http) {

        var exports = {};


        exports.createKaart = function (cirkelsessieId, kaart) {

            return $http.post("/api/cirkelsessies/" + cirkelsessieId + "/spelkaart", kaart).then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("KaartService", KaartService);

})(window.angular);