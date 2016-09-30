(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('ResponsableDetailController', ResponsableDetailController);

    ResponsableDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Responsable', 'Entreprise'];

    function ResponsableDetailController($scope, $rootScope, $stateParams, previousState, entity, Responsable, Entreprise) {
        var vm = this;

        vm.responsable = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliProjectApp:responsableUpdate', function(event, result) {
            vm.responsable = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
