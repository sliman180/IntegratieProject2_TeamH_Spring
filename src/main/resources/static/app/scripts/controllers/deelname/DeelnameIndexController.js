(function (angular) {

    "use strict";

    function DeelnameIndexController($rootScope, DeelnameService) {

        var vm = this;

        vm.deelnames = [];
        vm.cirkelsessies = [];

        DeelnameService.allOfGebruiker($rootScope.id).then(function (data) {
            vm.deelnames = data;
            angular.forEach(vm.deelnames, function (value, key) {
                DeelnameService.getCirkelsessie(value.id).then(function (cirkelsessiedata) {
                    vm.cirkelsessies.push(cirkelsessiedata);
                });
            });
        });

    }

    angular.module("kandoe").controller("DeelnameIndexController", DeelnameIndexController);

})(window.angular);
