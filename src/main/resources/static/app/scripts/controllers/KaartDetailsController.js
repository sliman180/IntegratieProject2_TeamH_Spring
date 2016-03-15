(function (angular) {

    "use strict";


    function KaartDetailsController($route, $routeParams, KaartService) {

        var vm = this;

        vm.kaart = {};

        KaartService.find($routeParams.id).then(function (data) {
            vm.kaart = data;
        });


        //vm.createComment = function (kaartId, kaart) {
        //    KaartService.createKaartComment(kaartId, kaart).then(function () {
        //        $route.reload();
        //    });
        //};
    }


    angular.module("kandoe").controller("KaartDetailsController", KaartDetailsController);

})(window.angular);
