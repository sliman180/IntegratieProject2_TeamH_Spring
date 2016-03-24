(function (angular) {

    "use strict";

    function DeelnameIndexController($rootScope, DeelnameService) {

        var vm = this;

        vm.deelnames = [];

        DeelnameService.allOfGebruiker($rootScope.gebruiker.id).then(function (data) {
            vm.deelnames = data;
        });

    }

    angular.module("kandoe").controller("DeelnameIndexController", DeelnameIndexController);

})(window.angular);
