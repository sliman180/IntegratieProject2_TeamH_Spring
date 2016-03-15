(function (angular) {

    "use strict";


    function KaartDetailsController($route, $routeParams, KaartService) {

        var vm = this;

        vm.kaart = {};
        vm.comments = [];

        KaartService.find($routeParams.id).then(function (data) {
            vm.kaart = data;

            KaartService.getComments($routeParams.id).then(function (commentsdata) {

                vm.comments = commentsdata;
            })
        });


        vm.createComment = function (kaartId, comment) {
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
