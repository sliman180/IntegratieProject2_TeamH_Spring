(function (angular) {

    "use strict";

    function DeelnameService($http) {

        var exports = {};


        exports.doeDeelname = function (cirkelsessieId) {

            return $http.post("/api/deelnames/" + cirkelsessieId).then(function (response) {
                return response.data;
            });

        };

        exports.getDeelnames = function (cirkelsessieId) {

            return $http.get("/api/cirkelsessies/" + cirkelsessieId + "/deelnames").then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("DeelnameService", DeelnameService);

})(window.angular);
