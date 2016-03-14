(function (angular) {

    "use strict";

    function ChatService($http) {

        var exports = {};


        exports.createMessage = function (chatId, bericht) {

            return $http.post("/api/chats/" + chatId + "/messages", bericht).then(function (response) {
                return response.data;
            });

        };


        return exports;

    }

    angular.module("kandoe").factory("ChatService", ChatService);

})(window.angular);
