(function (angular) {

    "use strict";

    function SpelkaartService($http) {

        var exports = {};


        exports.getSpelkaarten = function (cirkelsessieId) {

            return $http.get("/api/cirkelsessies/" + cirkelsessieId + "/spelkaarten").then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("SpelkaartService", SpelkaartService);

})(window.angular);