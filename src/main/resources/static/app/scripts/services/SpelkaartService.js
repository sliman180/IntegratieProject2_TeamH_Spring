(function (angular) {

    "use strict";

    function SpelkaartService($http) {

        var exports = {};

        exports.getKaart = function (id) {

            return $http.get("/api/spelkaarten/" + id + "/kaart").then(function (response) {
                return response.data;
            });

        };

        return exports;

    }

    angular.module("kandoe").factory("SpelkaartService", SpelkaartService);

})(window.angular);
