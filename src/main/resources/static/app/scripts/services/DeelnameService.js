(function (angular) {

    "use strict";

    function DeelnameService($http) {

        var exports = {};

        exports.allOfGebruiker = function (id) {

            return $http.get("/api/gebruikers/" + id + "/deelnames").then(function (response) {
                return response.data;
            });

        };

        exports.update = function (deelname) {

            return $http.put("/api/deelnames/" + deelname.id, deelname).then(function (response) {
                return response.data;
            });

        };

        exports.delete = function (id) {

            return $http.delete("/api/deelnames/" + id).then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("DeelnameService", DeelnameService);

})(window.angular);
