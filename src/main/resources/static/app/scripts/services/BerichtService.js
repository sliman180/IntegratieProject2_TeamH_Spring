(function (angular) {

    "use strict";

    function BerichtService($http) {

        var exports = {};

        exports.getGebruiker = function (id) {

            return $http.get("/api/berichten/" + id + "/gebruiker").then(function (response) {
                return response.data;
            });

        };


        return exports;

    }

    angular.module("kandoe").factory("BerichtService", BerichtService);

})(window.angular);
