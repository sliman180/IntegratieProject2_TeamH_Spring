(function (angular) {

    "use strict";

    function DeelnameIndexController(GebruikerService) {

        var vm = this;

        vm.deelnames = [];


            GebruikerService.getMijnDeelnames().then(function (data) {
                vm.deelnames=data;
            });


    }

    angular.module("kandoe").controller("DeelnameIndexController", DeelnameIndexController);

})(window.angular);
