(function (angular) {

    "use strict";

    function AuthInterceptor(localStorageService) {

        var exports = {};

        exports.request = function (config) {

            config.headers = config.headers || {};

            var data = localStorageService.get("auth");

            if (data) {
                config.headers.Authorization = "Bearer " + data.token;
            }

            return config;

        };

        return exports;

    }

    angular.module("kandoe").factory("AuthInterceptor", AuthInterceptor);

})(window.angular);
