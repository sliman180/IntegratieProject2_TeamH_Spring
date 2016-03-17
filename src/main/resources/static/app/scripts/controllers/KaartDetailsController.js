(function (angular) {

    "use strict";


    function KaartDetailsController($route, $routeParams, KaartService, $rootScope) {

        var vm = this;

        vm.kaart = {};
        vm.comments = [];
        vm.gebruiker = {};

        KaartService.find($routeParams.id).then(function (data) {
            vm.kaart = data;

            KaartService.getComments($routeParams.id).then(function (commentsdata) {
                vm.comments = commentsdata;
            });

            KaartService.getGebruiker(vm.kaart.id).then(function (gebruikerdata) {
                vm.gebruiker = gebruikerdata;
            });
        });


        vm.createComment = function (kaartId, comment) {
            comment.gebruiker = $rootScope.id;
            KaartService.createComment(kaartId, comment).then(function () {
                $route.reload;
            });
        };


        //vm.createComment = function (kaartId, kaart) {
        //    KaartService.createKaartComment(kaartId, kaart).then(function () {
        //        $route.reload();
        //    });
        //};
    }


    angular.module("kandoe").controller("KaartDetailsController", KaartDetailsController);

})(window.angular);
